package tf2.entity.mob.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

public class EntityAICharge extends EntityAIBase
{
	/** The entity that is leaping. */
	EntityLiving leaper;
	/** The entity that the leaper is leaping towards. */
	EntityLivingBase leapTarget;

	double startDistance;

	private int chargeTime;

	public EntityAICharge(EntityLiving leapingEntity, double startDistanceIn)
	{
		this.leaper = leapingEntity;
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

			if (d0 <= this.startDistance)
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
		this.chargeTime = 0;
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return this.chargeTime < 40;
	}
	@Override
	public void startExecuting()
	{
		super.startExecuting();
	}
	@Override
	public void updateTask()
	{
		EntityLivingBase attackTarget = this.leaper.getAttackTarget();

		if (attackTarget != null)
		{
			boolean flag = this.leaper.getEntitySenses().canSee(leapTarget);
			boolean flag1 = this.chargeTime > 0;
			if (flag != flag1)
			{
				this.chargeTime = 0;
			}
			if (flag)
			{
				++this.chargeTime;
			}
			else
			{
				--this.chargeTime;
			}

			double dx = this.leapTarget.posX - this.leaper.posX;
			double dz = this.leapTarget.posZ - this.leaper.posZ;
			double dy = this.leapTarget.posY - this.leaper.posY;

			if (this.chargeTime < 40)
			{
				//this.leaper.getNavigator().clearPath();
				this.leaper.getNavigator().tryMoveToEntityLiving(attackTarget, 0F);
			}

			if (this.chargeTime == 40)
			{
				double f2 = MathHelper.sqrt(dx * dx + dz * dz);
				double y1 = Math.max(dy, 0D);
				double y2 = MathHelper.sqrt(y1 * y1);
				double y3 = Math.min(y2, 10D);



				this.leaper.motionX = (dx / f2) * MathHelper.sqrt(f2) * 1.5F;
				this.leaper.motionZ = (dz / f2) * MathHelper.sqrt(f2) * 1.5F;
				this.leaper.motionY = (0.14F * y3) + 0.3F;
			}
		}

	}


}