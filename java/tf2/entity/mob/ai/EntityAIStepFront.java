package tf2.entity.mob.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.MathHelper;

public class EntityAIStepFront extends EntityAIBase
{
	/** The entity that is leaping. */
	EntityLiving leaper;
	/** The entity that the leaper is leaping towards. */
	EntityLivingBase leapTarget;
	/** The entity's motionY after leaping. */
	float motionFront;
	double minDistance;
	double maxDistance;

	int frequency;

	public EntityAIStepFront(EntityLiving leapingEntity, float motionFrontIn, int frequencyIn, double minDistanceIn, double maxDistanceIn)
	{
		this.leaper = leapingEntity;
		this.motionFront = motionFrontIn;
		this.frequency = frequencyIn;
		this.minDistance = minDistanceIn;
		this.maxDistance = maxDistanceIn;
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

			if (d0 >= this.minDistance && d0 <= this.maxDistance)
			{
				if (!this.leaper.onGround)
				{
					return false;
				}
				else
				{
					return this.leaper.getRNG().nextInt(frequency) == 0;
				}
			}
			else
			{
				return false;
			}
		}
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return !this.leaper.onGround;
	}

	@Override
	public void startExecuting()
	{
		double dx = this.leapTarget.posX - this.leaper.posX;
		double dz = this.leapTarget.posZ - this.leaper.posZ;
		float f = MathHelper.sqrt(dx * dx + dz * dz);

		this.leaper.motionX = (dx / f) * this.motionFront;
		this.leaper.motionZ = (dz / f) * this.motionFront;

		this.leaper.motionY = 0.4D;
	}
}