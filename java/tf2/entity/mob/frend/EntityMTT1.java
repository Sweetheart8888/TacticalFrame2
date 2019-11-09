package tf2.entity.mob.frend;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import tf2.TFItems;
import tf2.TFSoundEvents;
import tf2.entity.mob.ai.EntityAIFollowFriendMecha;
import tf2.entity.mob.ai.EntityAINearestAttackbleTargetFriend;
import tf2.entity.mob.ai.EntityAITurretAttackRanged;
import tf2.entity.projectile.player.EntityFriendBullet;

public class EntityMTT1 extends EntityFriendMecha implements IRangedAttackMob
{
	private static final double defaultDamage = 3;
	private static final double upAttack = 0.07;
	private static final double upArmor = 0.124;
	private static final double upArmorToughness = 0.022;
	private static final double upMaxHealth = 1.23;

	private static final double defaultArmor = 8.0D;
	private static final double defaultArmorToughness = 0.0D;
	private static final double defaultMaxHealth = 40.0D;

	private static final int uniqueSkillLevel = 39;

	public EntityMTT1(World worldIn)
	{
		super(worldIn, (byte) 1, (byte) 50, defaultDamage, upAttack, defaultArmor, upArmor, defaultArmorToughness, upArmorToughness, defaultMaxHealth, upMaxHealth, false);
		this.setSize(1.4F, 2.0F);
		this.stepHeight = 1.6F;
		if (this.getHomePosition() == null)
		{
			this.setHomePosAndDistance(this.getPosition(), 4);
		}
	}

	protected void initEntityAI()
	{
		super.initEntityAI();
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIFollowFriendMecha(this, 1.0D, 5.0F, 2.0F));
		//this.tasks.addTask(2, new EntityAIWanderFriendMecha(this, 1.0D));
		this.tasks.addTask(2, new EntityAITurretAttackRanged(this, 1.0D, 1, 30.0F));
		this.tasks.addTask(3, new EntityFriendMecha.EntityAILookAtAccessPlayer(this));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.targetTasks.addTask(2, new EntityAINearestAttackbleTargetFriend(this, 10, true));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0D);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return TFSoundEvents.TM_SAY;
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
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
	{
		double var3 = target.posX - this.posX;
		double var8 = target.posY - this.posY - 0.5F;
		double var5 = target.posZ - this.posZ;

		if (this.attackTime <= 28 && this.attackTime % 4 == 0)
		{
			EntityFriendBullet var7 = new EntityFriendBullet(this.world, this);
			var7.setDamage(var7.getDamage() + this.getMechaATK());
			this.playSound(TFSoundEvents.M16, 2.3F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
			var7.shoot(var3, var8, var5, 2.0F, 3.0F);
			this.world.spawnEntity(var7);
		}

		if (this.attackTime <= 0)
		{
			if (this.getMechaLevel() >= 39)
			{
				this.attackTime = 70;
			}
			else
			{
				this.attackTime = 100;
			}
		}
	}

	@Override
	public void setSwingingArms(boolean swingingArms)
	{}

	@Override
	public ItemStack getSkillUnique()
	{
		if (this.getMechaLevel() >= uniqueSkillLevel)
		{
			return new ItemStack(TFItems.SKILL_QUICKRELOAD);
		}
		return null;
	}

	@Override
	public void isUpLevel()
	{
		super.isUpLevel();
	}

}
