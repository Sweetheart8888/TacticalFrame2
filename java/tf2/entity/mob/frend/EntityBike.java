package tf2.entity.mob.frend;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tf2.TF2Core;
import tf2.TFSoundEvents;

public class EntityBike extends EntityVehicle
{
	public float speed = 0.0F;

	private static final DataParameter<Float> ANGLE = EntityDataManager.<Float> createKey(EntityBike.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> SPEED = EntityDataManager.<Float> createKey(EntityBike.class, DataSerializers.FLOAT);

	public EntityBike(World worldIn)
	{
		super(worldIn, 0.0D, 10.0D, 0.0D, 50.0D);
		this.setSize(1.2F, 0.8F);
		this.stepHeight = 1.5F;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
		this.dataManager.register(ANGLE, this.rotationYaw);
		this.dataManager.register(SPEED, this.speed);
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
	}

	public boolean isAIEnabled()
	{
		return true;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return TFSoundEvents.TM_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_IRONGOLEM_HURT;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(TFSoundEvents.BIKE, 0.5F, this.speed * 1.5F);
	}

	@Override
	public void updatePassenger(Entity passenger)
	{
		if (this.isPassenger(passenger))
		{
			float f = 0.0F;
			float f1 = (float) ((this.isDead ? 0.009999999776482582D : this.getMountedYOffset()) + passenger.getYOffset());

			if (this.getPassengers().size() > 1)
			{
				int i = this.getPassengers().indexOf(passenger);

				if (i == 0)
				{
					f = 0.2F;
				}
				else
				{
					f = -0.6F;
				}

				if (passenger instanceof EntityAnimal)
				{
					f = (float) ((double) f + 0.2D);
				}
			}

			Vec3d vec3d = (new Vec3d((double) f, 0.0D, 0.0D)).rotateYaw(-this.rotationYaw * 0.017453292F - ((float) Math.PI / 2F));
			passenger.setPosition(this.posX + vec3d.x, this.posY + (double) f1, this.posZ + vec3d.z);
			this.applyYawToEntity(passenger);

			if (passenger instanceof EntityAnimal && this.getPassengers().size() > 1)
			{
				int j = passenger.getEntityId() % 2 == 0 ? 90 : 270;
				passenger.setRenderYawOffset(((EntityAnimal) passenger).renderYawOffset + (float) j);
				passenger.setRotationYawHead(passenger.getRotationYawHead() + (float) j);
			}
		}
	}

	protected void applyYawToEntity(Entity entityToUpdate)
	{
		entityToUpdate.setRenderYawOffset(this.rotationYaw);
		float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
		float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
		entityToUpdate.prevRotationYaw += f1 - f;
		entityToUpdate.rotationYaw += f1 - f;
		entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
	}

	@Override
	public boolean processInteract(EntityPlayer player, EnumHand hand)
	{
		if (!this.world.isRemote)
		{

			if (this.canBeingRidden && player.isSneaking() && player.getCachedUniqueIdString().equals(this.getOwnerUUID().toString()))
			{
				if (!this.world.isRemote && this.getPassengers().size() < 2)
				{
					player.startRiding(this);
					return true;
				}
				else
				{
					this.setAccessPlayer(player);
					if(this.getOwner() != null || player.isCreative())
					{
						player.openGui(TF2Core.INSTANCE, TF2Core.guiTurret, this.getEntityWorld(), this.getEntityId(), 0, 0);
					}
					return false;
				}

			}
			else
			{
				this.setAccessPlayer(player);
				if(this.getOwner() != null || player.isCreative())
				{
					player.openGui(TF2Core.INSTANCE, TF2Core.guiTurret, this.getEntityWorld(), this.getEntityId(), 0, 0);
				}
				return false;
			}
		}
		return false;
	}

	@Override
	public void onLivingUpdate()
	{
		super.onLivingUpdate();
		this.motionX = (double)(MathHelper.sin(this.dataManager.get(ANGLE) * -0.017453292F) * (this.dataManager.get(SPEED)));
		this.motionZ = (double)(MathHelper.cos(this.dataManager.get(ANGLE) * -0.017453292F) * (this.dataManager.get(SPEED)));
		if (!this.isBeingRidden() || !this.canBeSteered())
		{
			if(this.speed > 0.05F)
			{
				this.speed -= 0.025F;
			}
			else if(this.speed < -0.025F)
			{
				this.speed += 0.05F;
			}
			else
			{
				this.speed = 0.0F;
			}
			this.dataManager.set(SPEED, this.speed);
		}
		if(this.speed > 0.15F && this.isInWater())
		{
			this.speed = 0.15F;
		}
	}

	@Override
	protected boolean canFitPassenger(Entity passenger)
	{
		return this.getPassengers().size() < 2;
	}

	@Override
	public void travel(float strafe, float vertical, float forward)
	{
		if (this.isBeingRidden() && this.canBeSteered())
		{
			EntityLivingBase entitylivingbase = (EntityLivingBase) this.getControllingPassenger();
			this.rotationYaw = this.dataManager.get(ANGLE);
			this.prevRotationYaw = this.dataManager.get(ANGLE);
//			this.rotationPitch = entitylivingbase.rotationPitch * 0.5F;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.renderYawOffset = this.rotationYaw;
			this.rotationYawHead = this.renderYawOffset;
//
//			if (forward <= 0.0F)
//			{
//				forward *= 0.25F;
//
//			}
			this.isAirBorne = false;

			this.jumpMovementFactor = this.getAIMoveSpeed() * 0.1F;

			if (this.canPassengerSteer())
			{
//				this.setAIMoveSpeed((float) this.dataManager.get(SPEED) / 2.5F);
				super.travel(0F, vertical, 0F);
//				this.motionX = (double)(MathHelper.sin(this.rotationYaw * 0.017453292F) * (this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() / 2));
//				this.motionZ = (double)(MathHelper.cos(this.rotationYaw * 0.017453292F) * (this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() / 2));
			}
		}
		else
		{
			this.jumpMovementFactor = 0.02F;
			super.travel(strafe, vertical, forward);
		}
	}

	@Override
	public void onFrontMove(World world, EntityPlayer player)
	{
		if(this.speed < 0.7F)
		{
			this.speed += 0.01875F;
		}
		else
		{
			this.speed = 0.7F;
		}
		this.dataManager.set(SPEED, speed);
	}

	@Override
	public void onBackMove(World world, EntityPlayer player)
	{
		if(this.speed > -0.15F)
		{
			this.speed -= 0.01875F;
		}
		else
		{
			this.speed = -0.15F;
		}
		this.dataManager.set(SPEED, this.speed);
	}

	@Override
	public void onLeftMove(World world, EntityPlayer player)
	{
		this.dataManager.set(ANGLE, this.rotationYaw -= 2.8F);
	}

	@Override
	public void onRightMove(World world, EntityPlayer player)
	{
		this.dataManager.set(ANGLE, this.rotationYaw += 2.8F);
	}

	@Override
	public void onJumped(World world, EntityPlayer player)
	{
		if(this.speed > 0.05F)
		{
			this.speed -= 0.025F;
		}
		else if(this.speed < -0.025F)
		{
			this.speed += 0.05F;
		}
		else
		{
			this.speed = 0.0F;
		}
		this.dataManager.set(SPEED, this.speed);
	}

	@Override
	public ItemStack getSkillUnique()
	{
		return null;
	}

}
