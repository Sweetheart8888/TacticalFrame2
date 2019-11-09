package tf2.entity.mob.frend;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tf2.TFItems;
import tf2.TFSoundEvents;
import tf2.entity.projectile.player.EntityBulletBig;
import tf2.entity.projectile.player.EntityGrenade;
import tf2.potion.TFPotionPlus;

public class EntityCFR12 extends EntityMobCF
{
	private int smoke;

	private static final byte slotSize = 2;
	private static final byte maxLevel = 99;

	private static final double defaultDamage = 10D;
	private static final double defaultArmor = 8.0D;
	private static final double defaultArmorToughness = 2.0D;
	private static final double defaultMaxHealth = 100.0D;

	private static final double upAttack = 0.257;
	private static final double upArmor = 0.123;
	private static final double upArmorToughness = 0.062;
	private static final double upMaxHealth = 6.64;

	private static final int maxBoostStack = 40;
	private static final int limitLeftclick = 12;
	private static final int limitRightclick = 20;
	private static final int limitShift = 300;

	private static final int leftAmmo = 30;
	private static final int rightAmmo = 3;
	private static final int leftReloadTime = 100;
	private static final int rightReloadTime = 200;

	public EntityCFR12(World worldIn)
	{
		super(worldIn, slotSize, maxLevel, defaultDamage, upAttack, defaultArmor, upArmor, defaultArmorToughness, upArmorToughness, defaultMaxHealth, upMaxHealth,
				maxBoostStack, limitLeftclick, limitRightclick, limitShift, leftAmmo, rightAmmo, leftReloadTime, rightReloadTime);
		this.setSize(2.1F, 2.1F);
		this.stepHeight = 2.0F;
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(20D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.22D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
		this.boostStack = this.maxBoostStack;
	}

	@Override
	public void isUpLevel()
	{
		super.isUpLevel();
		this.getUniqueSkill(19);

		if (this.getMechaLevel() == 39)
		{
			this.getInventoryMechaEquipment().setHasSkill(new ItemStack(TFItems.SKILL_ADDITIONALARMOR_1));
		}
		if (this.getMechaLevel() == 59)
		{
			this.getInventoryMechaEquipment().setHasSkill(new ItemStack(TFItems.SKILL_FIREFILLING));
		}
	}

	@Override
	public ItemStack getSkillUnique()
	{
		if (this.getMechaLevel() >= 19)
		{
			return new ItemStack(TFItems.SKILL_WIDESPREAD);
		}
		return null;
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return null;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		return TFSoundEvents.TF_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_IRONGOLEM_HURT;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		//this.playSound(TFSoundEvents.TF_TANK, 0.8F, 0.8F);
	}

	//搭乗者の高さ
	@Override
	public double getMountedYOffset()
	{
		return 2.0D;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();
		if (smoke > 0)
		{
			for (int var24 = 0; var24 < 50; ++var24)
			{
				this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX - 1.5F + (this.rand.nextFloat() * 3F), this.posY + this.rand.nextFloat() * 3F, this.posZ - 1.5F + (this.rand.nextFloat() * 3F), 0.0D, 0.0D, 0.0D, new int[15]);
			}
			--smoke;
		}
	}

	@Override
	public void onLeftClick(World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			EntityBulletBig bullet = new EntityBulletBig(world, player);
			bullet.setDamage(bullet.getDamage() + this.getMechaATK());
			bullet.setHeadingFromThrower(player, player.rotationPitch - 1.5F, player.rotationYaw, 0.0F, 2.0F, 0.0F);
			bullet.setPosition(this.calPosX(player), this.calPosY(player) + 1.5D, this.calPosZ(player));
			bullet.shoot(bullet.motionX, bullet.motionY, bullet.motionZ, 3.0F, 0F);
			world.spawnEntity(bullet);
		}
		world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, TFSoundEvents.SAIGA, SoundCategory.AMBIENT, 2.5F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
	}

	@Override
	public void onRightClick(World world, EntityPlayer player)
	{
		if (!world.isRemote)
		{
			float[] wideAngle = { 0, -15F, 15F };
			for (int i = 0; i < 3; i++)
			{
				EntityGrenade bullet = new EntityGrenade(world, player);
				bullet.setDamage(bullet.getDamage() + this.getMechaATK() + 10D);
				bullet.setSpread(bullet.getSpread() + 3.5D);
				bullet.setHeadingFromThrower(player, player.rotationPitch - 1.5F, player.rotationYaw + wideAngle[i], 0.0F, 2.0F, 0.0F);
				bullet.setPosition(this.calPosX(player), this.calPosY(player) + 1.5D, this.calPosZ(player));
				bullet.shoot(bullet.motionX, bullet.motionY, bullet.motionZ, 3.0F, 0F);
				world.spawnEntity(bullet);
			}
		}
		world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, TFSoundEvents.MGL, SoundCategory.AMBIENT, 2.5F, 1.0F / (this.rand.nextFloat() * 0.4F + 0.8F));
	}

	@Override
	public void onJumped(World world, EntityPlayer player)
	{
		this.motionY = 0.5D;
	}

	@Override
	public void onShift(World world, EntityPlayer player)
	{
		this.smoke = 80;

		double k;
		if (this.getMechaLevel() >= 19)
		{
			k = 10D;
		}
		else
		{
			k = 1.5D;
		}
		List var7 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(k));
		for (int var3 = 0; var3 < var7.size(); ++var3)
		{
			EntityLivingBase living = (EntityLivingBase) var7.get(var3);

			if ((living instanceof EntityFriendMecha || living instanceof EntityPlayer) && !living.world.isRemote)
			{
				if (!living.isPotionActive(TFPotionPlus.DISABLE_CHANCE))
				{
					living.addPotionEffect(new PotionEffect(TFPotionPlus.DISABLE_CHANCE, 200, 2));
				}
			}
		}

	}

	@Override
	public void travel(float strafe, float vertical, float forward)
	{
		if (this.isBeingRidden() && this.canBeSteered())
		{
			EntityLivingBase entitylivingbase = (EntityLivingBase) this.getControllingPassenger();

			this.rotationYaw = entitylivingbase.rotationYaw;
			this.prevRotationYaw = this.rotationYaw;
			this.rotationPitch = entitylivingbase.rotationPitch * 1.0F;
			this.setRotation(this.rotationYaw, this.rotationPitch);
			this.renderYawOffset = this.rotationYaw;
			this.rotationYawHead = this.renderYawOffset;
			strafe = entitylivingbase.moveStrafing * 1.00F;
			forward = entitylivingbase.moveForward;

			if (forward <= 0.0F)
			{
				forward *= 0.25F;

			}
			this.isAirBorne = false;
			this.fallDistance = 0F;

			this.jumpMovementFactor = this.getAIMoveSpeed() * 0.45F;

			if (this.canPassengerSteer())
			{
				this.setAIMoveSpeed((float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
				super.travel(strafe, vertical, forward);
			}
		}
		else
		{
			this.jumpMovementFactor = 0.02F;
			super.travel(strafe, vertical, forward);
		}
	}
}
