package tf2.entity.mob.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

public class EntityAIAttackRangedGun extends EntityAIBase
{
	/** The entity the AI instance has been applied to */
	private final EntityLiving entityHost;
	/** The entity (as a RangedAttackMob) the AI instance has been applied to. */
	private final IRangedAttackMob rangedAttackEntityHost;
	//private EntityLivingBase attackTarget;
	/**
	 * A decrementing tick that spawns a ranged attack once this value reaches 0. It is then set back to the
	 * maxattackTime.
	 */
	private int attackTime;
	private final double entityMoveSpeed;
	private int seeTime;
	private final int attackIntervalMin = 1;
	/** The maximum time the AI has to wait before peforming another ranged attack. */
	private final float attackRadius;
	private final float maxAttackDistance;

	private boolean strafingClockwise;
	private boolean strafingBackwards;
	private int strafingTime = -1;

	public EntityAIAttackRangedGun(IRangedAttackMob attacker, double movespeed, float maxAttackDistanceIn)
	{
		this.attackTime = -1;

		this.rangedAttackEntityHost = attacker;
		this.entityHost = (EntityLiving) attacker;
		this.entityMoveSpeed = movespeed;
		this.attackRadius = maxAttackDistanceIn;
		this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
		this.setMutexBits(3);

	}

	@Override
	public boolean shouldExecute()
	{
		return this.entityHost.getAttackTarget() == null ? false : true;
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return (this.shouldExecute() || !this.entityHost.getNavigator().noPath());
	}

	@Override
	public void startExecuting()
	{
		super.startExecuting();
	}

	@Override
	public void resetTask()
	{
		super.resetTask();
		this.seeTime = 0;
		this.attackTime = -1;
	}

	/**
	 * Updates the task
	 */
	public void updateTask()
	{
		EntityLivingBase attackTarget = this.entityHost.getAttackTarget();

		if (attackTarget != null)
		{
			double d0 = this.entityHost.getDistanceSq(attackTarget.posX, attackTarget.getEntityBoundingBox().minY, attackTarget.posZ);
			boolean flag = this.entityHost.getEntitySenses().canSee(attackTarget);
			boolean flag1 = this.seeTime > 0;
			if (flag != flag1)
			{
				this.seeTime = 0;
			}
			if (flag)
			{
				++this.seeTime;
			}
			else
			{
				--this.seeTime;
			}



			if (d0 <= (double) this.maxAttackDistance && this.seeTime >= 20)
			{
				this.entityHost.getNavigator().clearPath();
				++this.strafingTime;


			}
			else
			{
				this.entityHost.getNavigator().tryMoveToEntityLiving(attackTarget, this.entityMoveSpeed);
				this.strafingTime = -1;


			}
			if (this.strafingTime >= 20)
			{
				if ((double) this.entityHost.getRNG().nextFloat() < 0.3D)
				{
					this.strafingClockwise = !this.strafingClockwise;
				}

				if ((double) this.entityHost.getRNG().nextFloat() < 0.3D)
				{
					this.strafingBackwards = !this.strafingBackwards;
				}

				this.strafingTime = 0;
			}

			if (this.strafingTime > -1)
			{
				if (d0 > (double) (this.maxAttackDistance * 0.75F))
				{
					this.strafingBackwards = false;
				}
				else if (d0 < (double) (this.maxAttackDistance * 0.25F))
				{
					this.strafingBackwards = true;
				}

				this.entityHost.getMoveHelper().strafe(this.strafingBackwards ? -0.5F : 0.5F, this.strafingClockwise ? 0.5F : -0.5F);
				this.entityHost.faceEntity(attackTarget, 30.0F, 30.0F);
			}
			else
			{
				this.entityHost.getLookHelper().setLookPositionWithEntity(attackTarget, 30.0F, 30.0F);
			}

			if (--this.attackTime == 0)
			{
				if (d0 > (double) this.maxAttackDistance || !flag)
				{
					return;
				}

				float f = MathHelper.sqrt(d0) / this.attackRadius;
				float lvt_5_1_ = MathHelper.clamp(f, 0.1F, 1.0F);
				this.rangedAttackEntityHost.attackEntityWithRangedAttack(attackTarget, lvt_5_1_);
				this.attackTime = MathHelper.floor(f + (float) this.attackIntervalMin);
			}
			else if (this.attackTime < 0)
			{
				float f2 = MathHelper.sqrt(d0) / this.attackRadius;
				this.attackTime = MathHelper.floor(f2 + (float) this.attackIntervalMin);
			}
		}

	}
}