package tf2.entity.mob.ai;

import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityAIFloating  extends EntityAIBase
{
	private final EntityLiving parentEntity;
	private final double height;
	private final double speed;
	private final double leaving;
	private Boolean landing = false;

	public EntityAIFloating(EntityLiving entity, double heightIn, double speedIn, double leavingIn)
	{
		this.parentEntity = entity;
		this.height = heightIn;
		this.speed = speedIn;
		this.leaving = leavingIn;
		this.setMutexBits(4);
	}

	public EntityAIFloating(EntityLiving entity, double heightIn, double speedIn, double leavingIn, boolean landingIn)
	{
		this(entity, heightIn, speedIn, leavingIn);
		this.landing = landingIn;
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		return true;
	}

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {
    	if(this.landing)
    	{
    		this.parentEntity.motionY = -this.speed;
    	}
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {

    	this.parentEntity.motionY = this.speed;

    	World world = this.parentEntity.world;
    	double height = this.height;
    	double count = this.height;
		int j = 0;
		BlockPos blockpos = new BlockPos(this.parentEntity.getPosition().down());

		while (count > 0)
		{
			blockpos = new BlockPos(this.parentEntity.posX, this.parentEntity.posY - (height - count), this.parentEntity.posZ);
			if (world.getBlockState(blockpos).getMaterial() == Material.AIR)
			{
				j++;
			}
			count--;
		}
		if (j <= height - 1)
		{
			this.parentEntity.motionY += (0.15000001192092896D - this.parentEntity.motionY) * 0.10000001192092896D;

		}
		else
		{
			blockpos = new BlockPos(this.parentEntity.posX, this.parentEntity.posY - (height - (count + 0.5)), this.parentEntity.posZ);
			if (world.getBlockState(blockpos).getMaterial() == Material.AIR)
			{
				this.parentEntity.motionY = -this.speed;
			}
			else
			{
				this.parentEntity.motionY = 0.0D;
			}
		}

		/** 動いている方向に視線を向ける処理 */
		if(this.parentEntity.getAttackTarget() != null)
		{
			if (this.parentEntity.getAttackTarget().getDistanceSq(this.parentEntity) < 4096.0D)
			{
				double d1 = this.parentEntity.getAttackTarget().posX - this.parentEntity.posX;
				double d2 = this.parentEntity.getAttackTarget().posZ - this.parentEntity.posZ;
				this.parentEntity.rotationYaw = -((float) MathHelper.atan2(d1, d2)) * (180F / (float) Math.PI);
				this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
			}
		}
		else
		{
			this.parentEntity.rotationYaw = -((float) MathHelper.atan2(this.parentEntity.motionX, this.parentEntity.motionZ)) * (180F / (float) Math.PI);
			this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
		}

		/** ターゲットに対して一定距離取る処理*/
		if (this.parentEntity.getAttackTarget() != null)
		{
			if (!this.parentEntity.canEntityBeSeen(this.parentEntity.getAttackTarget()))
			{
				this.parentEntity.setAttackTarget(null);
			}
		}
		if (this.parentEntity.getAttackTarget() != null)
		{
			double d0 = this.parentEntity.getAttackTarget().posX - this.parentEntity.posX;
			double d1 = this.parentEntity.getAttackTarget().posZ - this.parentEntity.posZ;
			float f0 = MathHelper.sqrt(d0 * d0 + d1 * d1);

			if (!this.parentEntity.onGround)
			{
				float f = this.parentEntity.getDistance(this.parentEntity.getAttackTarget()) / 0.9F;

				float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1);

				if (f < this.leaving)
				{
					this.parentEntity.motionX = -(d0 / (double) f2 * 0.01D * (30.0D - f) + this.parentEntity.motionX * 0.3D);
					this.parentEntity.motionZ = -(d1 / (double) f2 * 0.01D * (30.0D - f) + this.parentEntity.motionZ * 0.3D);
				}
				else if(f >= this.leaving && f <  this.leaving + 0.05D)
				{
					this.parentEntity.motionX *= 0.01D;
					this.parentEntity.motionZ *= 0.01D;
				}
				else if(f >=  this.leaving + 0.05D)
				{
					this.parentEntity.motionX = d0 / (double) f0 * 0.25D * 0.5D + this.parentEntity.motionX * 0.1D;
					this.parentEntity.motionZ = d1 / (double) f0 * 0.25D * 0.5D + this.parentEntity.motionZ * 0.1D;
				}
			}

			this.parentEntity.rotationYaw = -((float) MathHelper.atan2(d0, d1)) * (180F / (float) Math.PI);
			this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;


		}
    }
}