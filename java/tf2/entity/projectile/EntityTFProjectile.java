package tf2.entity.projectile;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TF2Core;
import tf2.TFDamageSource;
import tf2.potion.TFPotionPlus;
import tf2.util.RegistryHandler;

public class EntityTFProjectile extends Entity implements IProjectile
{
	protected int xTile;
	protected int yTile;
	protected int zTile;
	protected Block inTile;
	protected int inData;
	protected boolean inGround;

	protected EntityLivingBase thrower;

	private int ticksInGround;
	protected int ticksInAir;
	protected int ticksLifeBullet;

	protected double damage;

	public EntityTFProjectile(World worldIn)
	{
		super(worldIn);
		this.xTile = -1;
		this.yTile = -1;
		this.zTile = -1;
		this.setSize(0.2F, 0.2F);
	}

	public EntityTFProjectile(World worldIn, double x, double y, double z)
	{
		this(worldIn);
		this.setPosition(x, y, z);
	}

	public EntityTFProjectile(World worldIn, EntityLivingBase throwerIn)
	{
		this(worldIn, throwerIn.posX, throwerIn.posY + (double) throwerIn.getEyeHeight() - 0.10000000149011612D, throwerIn.posZ);
		this.thrower = throwerIn;
	}

	protected void entityInit()
	{}

	/**
	 * Checks if the entity is in range to render.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance)
	{
		double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;

		if (Double.isNaN(d0))
		{
			d0 = 4.0D;
		}

		d0 = d0 * 128.0D;
		return distance < d0 * d0;
	}

	/**
	 * Sets throwable heading based on an entity that's throwing it
	 */

	public void setHeadingFromThrower(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy)
	{
		float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
		float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
		float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
		this.shoot((double) f, (double) f1, (double) f2, velocity, inaccuracy);
		this.motionX += entityThrower.motionX;
		this.motionZ += entityThrower.motionZ;

		if (!entityThrower.onGround)
		{
			this.motionY += entityThrower.motionY;
		}
	}

	/**
	 * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
	 */
	@Override
	public void shoot(double x, double y, double z, float velocity, float inaccuracy)
	{
		float f = MathHelper.sqrt(x * x + y * y + z * z);
		x = x / (double) f;
		y = y / (double) f;
		z = z / (double) f;
		x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
		x = x * (double) velocity;
		y = y * (double) velocity;
		z = z * (double) velocity;
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
		float f1 = MathHelper.sqrt(x * x + z * z);
		this.rotationYaw = (float) (MathHelper.atan2(x, z) * (180D / Math.PI));
		this.rotationPitch = (float) (MathHelper.atan2(y, (double) f1) * (180D / Math.PI));
		this.prevRotationYaw = this.rotationYaw;
		this.prevRotationPitch = this.rotationPitch;
		this.ticksInGround = 0;
	}

	/**
	 * Updates the velocity of the entity to a new value.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void setVelocity(double x, double y, double z)
	{
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt(x * x + z * z);
			this.rotationPitch = (float) (MathHelper.atan2(y, (double) f) * (180D / Math.PI));
			this.rotationYaw = (float) (MathHelper.atan2(x, z) * (180D / Math.PI));
			this.prevRotationPitch = this.rotationPitch;
			this.prevRotationYaw = this.rotationYaw;
		}
	}

	public void onUpdate()
	{
		super.onUpdate();

		if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
		{
			float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));
			this.rotationPitch = (float) (MathHelper.atan2(this.motionY, (double) f) * (180D / Math.PI));
			this.prevRotationYaw = this.rotationYaw;
			this.prevRotationPitch = this.rotationPitch;
		}

		BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
		IBlockState iblockstate = this.world.getBlockState(blockpos);
		Block block = iblockstate.getBlock();

		if (iblockstate.getMaterial() != Material.AIR)
		{
			AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(this.world, blockpos);

			if (axisalignedbb != Block.NULL_AABB && axisalignedbb.offset(blockpos).contains(new Vec3d(this.posX, this.posY, this.posZ)))
			{
				this.inGround = true;
			}
		}
		if (this.inGround)
		{
			this.inGround();
		}
		else
		{
			++this.ticksInAir;
			Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
			Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
			RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
			vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
			vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

			if (raytraceresult != null)
			{
				vec3d = new Vec3d(raytraceresult.hitVec.x, raytraceresult.hitVec.y, raytraceresult.hitVec.z);
			}

			Entity entity = this.findEntityOnPath(vec3d1, vec3d);

			if (entity != null)
			{
				raytraceresult = new RayTraceResult(entity);
			}

			if (raytraceresult != null && raytraceresult.entityHit != null && raytraceresult.entityHit instanceof EntityPlayer)
			{
				EntityPlayer entityplayer = (EntityPlayer) raytraceresult.entityHit;

				if (this.thrower instanceof EntityPlayer && !((EntityPlayer) this.thrower).canAttackPlayer(entityplayer))
				{
					raytraceresult = null;
				}
			}

			if (raytraceresult != null && raytraceresult.entityHit != null && raytraceresult.entityHit.isBeingRidden() && raytraceresult.entityHit.isRidingOrBeingRiddenBy(this.thrower))
			{
				raytraceresult = null;
			}

			if (raytraceresult != null && !net.minecraftforge.event.ForgeEventFactory.onProjectileImpact(this, raytraceresult))
			{
				this.onHit(raytraceresult);
			}

			this.posX += this.motionX;
			this.posY += this.motionY;
			this.posZ += this.motionZ;
			float f4 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
			this.rotationYaw = (float) (MathHelper.atan2(this.motionX, this.motionZ) * (180D / Math.PI));

			for (this.rotationPitch = (float) (MathHelper.atan2(this.motionY, (double) f4) * (180D / Math.PI)); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
			{
				;
			}

			while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
			{
				this.prevRotationPitch += 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw < -180.0F)
			{
				this.prevRotationYaw -= 360.0F;
			}

			while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
			{
				this.prevRotationYaw += 360.0F;
			}

			this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
			this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
			float f1 = 0.99F;

			if (this.isInWater())
			{
				for (int i = 0; i < 4; ++i)
				{
					this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ, new int[0]);
				}
				f1 = this.inWaterSpeed();
			}

			if (this.world.isRemote)
			{
				this.generateRandomParticles();
			}

			if (this.isWet())
			{
				this.extinguish();
			}

			this.motionX *= (double) f1;
			this.motionY *= (double) f1;
			this.motionZ *= (double) f1;

			this.isGravity();

			this.setPosition(this.posX, this.posY, this.posZ);
			this.doBlockCollisions();

		}

		if(!this.world.isRemote)
		{
			if (this.ticksInAir >= this.getTickAir() || this.inGround)
			{
				this.setEntityDead();
			}
		}
	}

	protected DamageSource damageSource()
	{
		if (this.thrower == null)
		{
			return TFDamageSource.causeBulletDamage(this, this);
		}
		else
		{
			return TFDamageSource.causeBulletDamage(this, this.thrower);
		}
	}

	protected void onHit(RayTraceResult raytraceResultIn)
	{
		Entity entity = raytraceResultIn.entityHit;
		if (entity != null)
		{
			DamageSource damagesource = this.damageSource();

			if (this.isBurning())
			{
				entity.setFire(5);
			}

			if (entity.attackEntityFrom(damagesource, this.directHitDamage()))
			{
				if (entity instanceof EntityLivingBase)
				{
					EntityLivingBase entitylivingbase = (EntityLivingBase) entity;

					if (this.thrower instanceof EntityLivingBase)
					{
						EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.thrower);
						EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) this.thrower, entitylivingbase);
					}

					this.bulletHit(entitylivingbase);
					entitylivingbase.hurtResistantTime = 0;

					if (this.thrower != null && entitylivingbase != this.thrower && entitylivingbase instanceof EntityPlayer && this.thrower instanceof EntityPlayerMP)
					{
						((EntityPlayerMP) this.thrower).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
					}
				}

				if (!(entity instanceof EntityEnderman))
				{
					this.setEntityDead();
				}
			}
			else
			{
				this.setEntityDead();
			}

		}
		else
		{
			this.onHitBlock(raytraceResultIn);
		}
	}

	protected void onHitBlock(RayTraceResult raytraceResultIn)
	{
		BlockPos blockpos = raytraceResultIn.getBlockPos();
		this.xTile = blockpos.getX();
		this.yTile = blockpos.getY();
		this.zTile = blockpos.getZ();
		IBlockState iblockstate = this.world.getBlockState(blockpos);
		this.inTile = iblockstate.getBlock();
		this.inData = this.inTile.getMetaFromState(iblockstate);
		this.motionX = (double) ((float) (raytraceResultIn.hitVec.x - this.posX));
		this.motionY = (double) ((float) (raytraceResultIn.hitVec.y - this.posY));
		this.motionZ = (double) ((float) (raytraceResultIn.hitVec.z - this.posZ));
		float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
		this.posX -= this.motionX / (double) f2 * 0.05000000074505806D;
		this.posY -= this.motionY / (double) f2 * 0.05000000074505806D;
		this.posZ -= this.motionZ / (double) f2 * 0.05000000074505806D;
		this.inGround = true;

		if (iblockstate.getMaterial() != Material.AIR)
		{
			this.inTile.onEntityCollidedWithBlock(this.world, blockpos, iblockstate, this);
		}
	}

	protected void inGround()
	{
		this.setEntityDead();
	}

	protected void setEntityDead()
	{
		this.setDead();
	}

	/**
	 * 重力加速度及び空中時のonUpdateの追加
	 */
	protected void isGravity()
	{}

	/**
	 * 水中での速度減衰
	 */
	protected float inWaterSpeed()
	{
		return 0.6F;
	}

	/**
	 * 自然消滅までの時間
	 */
	protected int getTickAir()
	{
		return ticksLifeBullet == 0 ? 30 : ticksLifeBullet;
	}

	protected void setTickAir(int ticks)
	{
		ticksLifeBullet = ticks;
	}

	public static void registerFixesThrowable(DataFixer fixer, String name)
	{}

	@Override
	public void move(MoverType type, double x, double y, double z)
	{
		super.move(type, x, y, z);

		if (this.inGround)
		{
			this.xTile = MathHelper.floor(this.posX);
			this.yTile = MathHelper.floor(this.posY);
			this.zTile = MathHelper.floor(this.posZ);
		}
	}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public boolean canBeAttackedWithItem()
	{
		return false;
	}

	protected void bulletHit(EntityLivingBase living)
	{
		if(living instanceof EntityEnderman)
		{
			living.attackEntityFrom(TFDamageSource.causeGrenadeDamage(this.thrower), (float) this.damage);
		}
	}

	protected float directHitDamage()
	{
		return (float)this.getDamage();
	}

	@Nullable
	protected Entity findEntityOnPath(Vec3d start, Vec3d end)
	{
		Entity entity = null;
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().expand(this.motionX, this.motionY, this.motionZ).grow(1.0D));
		double d0 = 0.0D;
		Entity entity1;
		double d1;

		if (!this.world.isRemote)
		{
			for (int k = 0; k < list.size(); ++k)
			{
				entity1 = (Entity) list.get(k);

				if (entity1.canBeCollidedWith() && (entity1 != this.thrower || this.ticksInAir >= 5))
				{
					AxisAlignedBB aabb = entity1.getEntityBoundingBox().grow(0.30000001192092896D);
					RayTraceResult result = aabb.calculateIntercept(start, end);

					if (result != null)
					{
						d1 = start.distanceTo(result.hitVec);

						if (d1 < d0 || d0 == 0.0D)
						{
							entity = entity1;
							d0 = d1;
						}
					}
				}
			}
		}
		return entity;
	}

	/**
	 * (abstract) Protected helper method to write subclass entity data to NBT.
	 */
	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		compound.setInteger("xTile", this.xTile);
		compound.setInteger("yTile", this.yTile);
		compound.setInteger("zTile", this.zTile);
		compound.setShort("life", (short) this.ticksInGround);
		ResourceLocation resourcelocation = Block.REGISTRY.getNameForObject(this.inTile);
		compound.setString("inTile", resourcelocation == null ? "" : resourcelocation.toString());
		compound.setByte("inData", (byte) this.inData);
		compound.setByte("inGround", (byte) (this.inGround ? 1 : 0));
		compound.setDouble("damage", this.damage);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		this.xTile = compound.getInteger("xTile");
		this.yTile = compound.getInteger("yTile");
		this.zTile = compound.getInteger("zTile");
		this.ticksInGround = compound.getShort("life");
		if (compound.hasKey("inTile", 8))
		{
			this.inTile = Block.getBlockFromName(compound.getString("inTile"));
		}
		else
		{
			this.inTile = Block.getBlockById(compound.getByte("inTile") & 255);
		}
		this.inData = compound.getByte("inData") & 255;
		this.inGround = compound.getByte("inGround") == 1;
		if (compound.hasKey("damage", 99))
		{
			this.damage = compound.getDouble("damage");
		}

	}

	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn)
	{}

	public void setDamage(double damageIn)
	{
		this.damage = damageIn;
	}

	public double getDamage()
	{
		double k = 0;
		if (this.thrower != null)
		{
			if (this.thrower.isPotionActive(MobEffects.STRENGTH))
			{
				PotionEffect potion = this.thrower.getActivePotionEffect(MobEffects.STRENGTH);
				int i = potion.getAmplifier();

				k = k + 2 + (i * 2);
			}
			if (this.thrower.isPotionActive(MobEffects.WEAKNESS))
			{
				PotionEffect potion = this.thrower.getActivePotionEffect(MobEffects.WEAKNESS);
				int i = potion.getAmplifier();

				k = k - (2 + (i * 2));
			}
			if (this.thrower.isPotionActive(TFPotionPlus.SHOOTING))
			{
				PotionEffect potion = this.thrower.getActivePotionEffect(TFPotionPlus.SHOOTING);
				int i = potion.getAmplifier();

				this.damage = this.damage * (1.1D + (i * 0.1D));
			}
			if (this.thrower.isPotionActive(TFPotionPlus.SHOOTING_SUPPORT))
			{
				PotionEffect potion = this.thrower.getActivePotionEffect(TFPotionPlus.SHOOTING_SUPPORT);
				int i = potion.getAmplifier();

				this.damage = this.damage * (1.1D + (i * 0.1D));
			}
		}
		return this.damage + k;
	}

	@SideOnly(Side.CLIENT)
	protected void generateRandomParticles()
	{}

	public static void registerEntity(Class<? extends Entity> clazz, ResourceLocation registryName, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerModEntity(registryName, clazz, registryName.getResourceDomain() + "." + registryName.getResourcePath(), RegistryHandler.entityId++, TF2Core.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
}
