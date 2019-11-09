package tf2.entity.mob.enemy;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
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
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import tf2.TF2Core;
import tf2.TFSoundEvents;
import tf2.entity.mob.ai.EntityAIStepFront;
import tf2.entity.mob.ai.tm26d.EntityAITM26ChargeCombo;
import tf2.entity.mob.ai.tm26d.EntityAITM26QuakeCombo;
import tf2.entity.mob.ai.tm26d.EntityAITM26SlashCombo;

public class EntityTM26D extends EntityMobTFBoss
{
	public EntityTM26D(World worldIn)
	{
		super(worldIn, 2);
		this.setSize(1.4F, 2.4F);
	}

	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIStepFront(this, 1.5F, 5, 40D, 50D));
		this.tasks.addTask(2, new EntityAITM26SlashCombo(this, 20D));
		this.tasks.addTask(2, new EntityAITM26QuakeCombo(this, 20D));
		this.tasks.addTask(2, new EntityAITM26ChargeCombo(this, 40D));
		this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, false));
		this.tasks.addTask(4, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(5, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(6, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityGolem.class, true));
		this.experienceValue = 100;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(50.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.34D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(12.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(2.0D);
	}

	@Override
	protected SoundEvent getAmbientSound()
	{
		return TFSoundEvents.TM_SAY;
	}

	@Override
	protected SoundEvent getDeathSound()
	{
		if(this.healthCount > 0)
		{
			return SoundEvents.ENTITY_IRONGOLEM_HURT;
		}
		else
		{
			return TFSoundEvents.TF_DEATH;
		}
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn)
	{
		return SoundEvents.ENTITY_IRONGOLEM_HURT;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn)
	{
		this.playSound(TFSoundEvents.TF_STEP, 0.8F, 1.0F);
	}

//	@Override
//	public void onDeath(DamageSource cause)
//	{
//		super.onDeath(cause);
//
//		if (!this.world.isRemote)
//		{
//			List k = this.world.getEntitiesWithinAABB(EntityPlayer.class, this.getEntityBoundingBox().grow(50.0D));
//			for (int u = 0; u < k.size(); ++u)
//			{
//				EntityPlayer playerall = (EntityPlayer) k.get(u);
//
//				EntityItem entityitem = new EntityItem(this.world, playerall.posX, playerall.posY + 1.0F, playerall.posZ, new ItemStack(TFItems.GEM, 1, 2));
//				this.world.spawnEntity(entityitem);
//			}
//		}
//	}

	@Override
	@Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
	{
		livingdata = super.onInitialSpawn(difficulty, livingdata);

		if (TF2Core.CONFIG.multiMission)
		{
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(800D);
			this.setHealth(800F);
		}

		return livingdata;
	}
}