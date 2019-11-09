package tf2.entity.mob.enemy;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import tf2.TF2Core;
import tf2.TFDamageSource;
import tf2.TFSoundEvents;
import tf2.entity.mob.ai.EntityAIAttackRangedOld;

public class EntityTM05 extends EntityMobTF implements IRangedAttackMob
{
	public EntityTM05(World worldIn)
	{
		super(worldIn);
		this.setSize(0.9F, 1.3F);
	}

	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackRangedOld(this, 1.0D, 3, 2.0F));
		this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(4, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityGolem.class, true));
		this.targetTasks.addTask(4, new EntityAINearestAttackableTarget(this, EntityVillager.class, true));
		this.experienceValue = 5;
	}

	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.3D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.32D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D);
	}

	@Override
	protected void entityInit()
	{
		super.entityInit();
	}

	@Nullable
	protected ResourceLocation getLootTable()
	{
		return TF2Core.ENTITIES_TM05;
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase var1, float var2)
	{
		this.setDeadExplode();
	}

	@Override
	public void onDeath(DamageSource cause)
	{
		super.onDeath(cause);
		this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);

		if (cause.getTrueSource() instanceof Entity)
		{
			this.setDeadExplode();
		}
	}

	public void setDeadExplode()
	{
		this.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this, this), this.getMaxHealth());
		this.setDead();

		this.world.createExplosion((Entity) null, this.posX, this.posY, this.posZ, 0.0F, false);

		List var7 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(4D));
		int var31;
		for (var31 = 0; var31 < var7.size(); ++var31)
		{
			EntityLivingBase var8 = (EntityLivingBase) var7.get(var31);
			if(!(var8 instanceof EntityMobTF))
			{
				var8.attackEntityFrom(TFDamageSource.causeGrenadeDamage(this), 15.0F);
				var8.hurtResistantTime = 0;
			}
		}
		if (!this.world.isRemote)
		{
			this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, this.experienceValue));
		}
	}
	@Override
	public void onDeathUpdate()
	{
		super.onDeathUpdate();
		this.setDead();
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
	{}

	@Override
	public boolean doesEntityNotTriggerPressurePlate()
	{
		return true;
	}

	@Override
	public void setSwingingArms(boolean swingingArms)
	{}

	@Override
	public boolean getCanSpawnHere()
	{
		return TF2Core.CONFIG.spawnMobTMtier1 && this.isValidLightLevel() && this.world.getDifficulty() != EnumDifficulty.PEACEFUL;
	}
}