package tf2.entity.mob.enemy.boss;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import tf2.TF2Core;
import tf2.TFSoundEvents;
import tf2.entity.mob.ai.EntityAIAttackRangedGun;
import tf2.entity.mob.enemy.EntityMobTFBoss;
import tf2.entity.projectile.EntityTFProjectile;

public class EntityTM17 extends EntityMobTFBoss implements IRangedAttackMob, IEntityMultiPart
{
	private final Entity partArray[];

	private boolean criticalDamage = false;

	private final EntityTM17Head head = new EntityTM17Head(this, "head", 2F, 3F);

	public EntityTM17(World worldIn)
	{
		super(worldIn, 1);
		this.setSize(3.2F, 1.7F);
		this.stepHeight = 3.0F;

		List<Entity> parts = new ArrayList<>();
		parts.add(head);

		partArray = parts.toArray(new Entity[0]);
	}

	protected void initEntityAI()
	{
		this.tasks.addTask(0, new EntityAISwimming(this));
		this.tasks.addTask(1, new EntityAIAttackRangedGun(this, 1.0D, 18.0F));
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
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(200.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.31D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D);
	}

	protected void entityInit()
	{
		super.entityInit();
	}

	@Nullable
	protected ResourceLocation getLootTable()
	{
		return TF2Core.ENTITIES_TM06;
	}

	@Override
	public void onLivingUpdate()
	{

		if(criticalDamage)
		{
			criticalDamage = false;
		}

		super.onLivingUpdate();

		multiPartEntityPosition(head, 240, 1.2F, 1.75F);
//		multiPartEntityPosition(rightFrontLeg, -240, 1.2F, 1.75F);
//		multiPartEntityPosition(leftBackLeg, 60, 1.2F, 1.75F);
//		multiPartEntityPosition(rightBackLeg, -60, 1.2F, 1.75F);
	}

	protected void multiPartEntityPosition(MultiPartEntityPart part, float angleIn, float widthIn, float heightIn)
	{
		part.width = widthIn;
		part.height = heightIn;

		float angle;
		double dx, dy, dz;

		angle = (((renderYawOffset + 180 + angleIn) * 3.141593F) / 180F);

		dx = posX - MathHelper.sin(angle) * 0;
		dy = posY + 1.25;
		dz = posZ + MathHelper.cos(angle) * 0;
		part.setPosition(dx, dy, dz);

		part.onUpdate();
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase var1, float var2)
	{
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
	}

	/**
	 * We need to do this for the bounding boxes on the parts to become active
	 */
	@Override
	public Entity[] getParts() {
		return partArray;
	}

	@Override
	public World getWorld() {
		return this.getEntityWorld();
	}

	@Override
	public boolean attackEntityFromPart(MultiPartEntityPart part, DamageSource source, float damage) {
		if(source.getImmediateSource() instanceof EntityTFProjectile)
		{
			this.hurtResistantTime = 0;
		}
		if(this.healthCount > 0)
		{
			this.criticalDamage = true;
		}
		return (this.healthCount > 0) ? super.attackEntityFrom(source, damage) : false;
	}

	@Override
	public boolean attackEntityFrom(DamageSource var1, float var2)
	{
		if(this.healthCount > 0)
		{
			return criticalDamage ? super.attackEntityFrom(var1, var2) : false;
		}

		return this.healthCount == 0 ? super.attackEntityFrom(var1, var2) : false;
	}

	@Override
	public void setSwingingArms(boolean swingingArms) {

	}
}

