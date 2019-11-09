package tf2.entity.mob.frend;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import tf2.TFItems;
import tf2.TFSoundEvents;
import tf2.entity.mob.ai.EntityAIFollowFriendMecha;
import tf2.entity.mob.ai.EntityAINearestAttackbleTargetFriend;
import tf2.entity.mob.ai.EntityAITurretAttackRanged;
import tf2.entity.projectile.player.EntityFriendMortar;

public class EntityMTT3 extends EntityFriendMecha implements IRangedAttackMob
{
	private static final double defaultDamage = 15;
	private static final double upAttack = 0.123;
	private static final double upArmor = 0.124;
	private static final double upArmorToughness = 0.022;
	private static final double upMaxHealth = 1.23;

	private static final double defaultArmor = 6.0D;
	private static final double defaultArmorToughness = 0.0D;
	private static final double defaultMaxHealth = 40.0D;

	public EntityMTT3(World worldIn)
	{
		super(worldIn, (byte) 1, (byte) 50, defaultDamage, upAttack, defaultArmor, upArmor, defaultArmorToughness, upArmorToughness, defaultMaxHealth, upMaxHealth, false);
		this.setSize(1.3F, 1.9F);
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
		//		this.tasks.addTask(2, new EntityAIWanderFriendMecha(this, 1.0D));
		this.tasks.addTask(2, new EntityAITurretAttackRanged(this, 1.0D, 1, 50.0F));
		this.tasks.addTask(3, new EntityFriendMecha.EntityAILookAtAccessPlayer(this));
		//		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.targetTasks.addTask(2, new EntityAINearestAttackbleTargetFriend(this, 10, true));
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.28D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(6.0D);
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

		double f2 = MathHelper.sqrt(var3 * var3 + var5 * var5);
		if (this.attackTime <= 0)
		{
			this.attackTime = 100;
		}

		if (this.attackTime == 1)
		{

			EntityFriendMortar bullet = new EntityFriendMortar(this.world, this);
			bullet.setDamage(bullet.getDamage() + this.getMechaATK());

			if (this.getMechaLevel() >= 19)
			{
				bullet.setSpread(8D);
			}
			else
			{
				bullet.setSpread(4D);
			}
			bullet.posY = this.posY + this.height * 2;
			bullet.setRange(f2);
			bullet.shoot(var3, 50F, var5, 1.75F, 4.0F);

			this.world.spawnEntity(bullet);

			this.playSound(TFSoundEvents.BAZOOKA, 2.3F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		}
	}

	@Override
	public void setSwingingArms(boolean swingingArms)
	{}

	@Override
	public ItemStack getSkillUnique()
	{
		if (this.getMechaLevel() >= 19)
		{
			return new ItemStack(TFItems.SKILL_SPREADCANNON);
		}
		return null;
	}

	@Override
	public void isUpLevel()
	{
		super.isUpLevel();
		this.getUniqueSkill(19);

		if (this.getMechaLevel() == 39)
		{
			this.getInventoryMechaEquipment().setHasSkill(new ItemStack(TFItems.SKILL_ARTILLERYCOMMAND_TURRET));
		}
	}
}
