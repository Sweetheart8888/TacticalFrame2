package tf2.entity.mob.enemy;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import tf2.TF2Core;
import tf2.TFSoundEvents;
import tf2.entity.projectile.enemy.EntityEnemyMortar;

public class EntityTM12 extends EntityMobTF implements IRangedAttackMob
{

	public EntityTM12(World worldIn)
	{
		super(worldIn);
		this.setSize(1.3F, 1.9F);
	}

	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackRanged(this, 1.0D, 1, 40.0F));
		this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityGolem.class, true));
		this.experienceValue = 25;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(45.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.9D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.22D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
	}

	protected void entityInit()
	{
		super.entityInit();
	}

	@Nullable
	protected ResourceLocation getLootTable()
	{
		return TF2Core.ENTITIES_TM12;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase var1, float var2)
	{
		double var3 = var1.posX - this.posX;
		double var5 = var1.posZ - this.posZ;

		double f2 = MathHelper.sqrt(var3 * var3 + var5 * var5);

		double var8 = var1.posY - this.posY;

		if (this.attackTime <= 0)
		{
			this.attackTime = 130;

		}

		if (this.attackTime == 10 || this.attackTime == 20)
		{

			EntityEnemyMortar bullet = new EntityEnemyMortar(this.world, this);
			bullet.setDamage(bullet.getDamage() + 12.0D);
			bullet.setSpread(5D);
			bullet.posY = this.posY + this.height * 2;
			bullet.setRange(f2);
			bullet.shoot(var3, 50F, var5, 1.75F, 4.0F);

			this.world.spawnEntity(bullet);

			this.playSound(TFSoundEvents.BAZOOKA, 2.3F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
		}
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
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(TFSoundEvents.TF_STEP, 0.5F, 1.0F);
	}

	@Override
	public void setSwingingArms(boolean swingingArms)
	{}

	@Override
	public int getMaxSpawnedInChunk()
	{
		return 1;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		return TF2Core.CONFIG.spawnMobTMtier2 && this.isValidLightLevel() && this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
	}
}