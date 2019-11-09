package tf2.entity.mob.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

public class EntityAIJumpAndCharge extends EntityAIBase
{
	/** The entity that is leaping. */
	EntityLiving leaper;
	/** The entity that the leaper is leaping towards. */
	EntityLivingBase leapTarget;
	/** The entity's motionY after leaping. */
	float leapMotionY;
	double startDistance;

	private int jumpTime;

	public EntityAIJumpAndCharge(EntityLiving leapingEntity, float leapMotionYIn, double startDistanceIn)
	{
		this.leaper = leapingEntity;
		this.leapMotionY = leapMotionYIn;
		this.startDistance = startDistanceIn;
		this.setMutexBits(5);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		this.leapTarget = this.leaper.getAttackTarget();

		if (this.leapTarget == null)
		{
			return false;
		}
		else
		{
			double d0 = this.leaper.getDistance(this.leapTarget);

			if (d0 <= this.startDistance && d0 > 3.0F)
			{
				if (!this.leaper.onGround)
				{
					return false;
				}
				else
				{
					return this.leaper.getRNG().nextInt(25) == 0;
				}
			}
			else
			{
				return false;
			}
		}
	}

	@Override
	public void resetTask()
	{
		super.resetTask();
		this.jumpTime = 0;
	}

	/**
	 * Returns whether an in-progress EntityAIBase should continue executing
	 */
	public boolean shouldContinueExecuting()
	{
		return !this.leaper.onGround;
	}

	@Override
	public void updateTask()
	{
		boolean flag = this.leaper.getEntitySenses().canSee(leapTarget);
		boolean flag1 = this.jumpTime > 0;
		if (flag != flag1)
		{
			this.jumpTime = 0;
		}
		if (flag)
		{
			++this.jumpTime;
		}
		else
		{
			--this.jumpTime;
		}

		double dx = this.leapTarget.posX - this.leaper.posX;
		double dz = this.leapTarget.posZ - this.leaper.posZ;
		double dy = this.leapTarget.posY - this.leaper.posY;

		if (this.jumpTime == 20)
		{

			double f2 = MathHelper.sqrt(dx * dx + dz * dz);

			double y1 = Math.min(dy, 0D);
			double y2 = MathHelper.sqrt(y1 * y1);
			double y3 = Math.min(y2, 15D);

			this.leaper.motionX = (dx / f2) * MathHelper.sqrt(f2) * 1.42F;
			this.leaper.motionZ = (dz / f2) * MathHelper.sqrt(f2) * 1.42F;
			this.leaper.motionY = -(1.0F * y3);
		}
	}

	@Override
	public void startExecuting()
	{
		this.leaper.motionY = (double) this.leapMotionY;
	}
}