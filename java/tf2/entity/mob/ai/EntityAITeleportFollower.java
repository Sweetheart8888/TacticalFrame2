package tf2.entity.mob.ai;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class EntityAITeleportFollower extends EntityAIBase
{
	protected EntityLiving entity;
	/** The closest entity which is being watched by this one. */
	protected Entity closestEntity;
	/** This is the Maximum distance that the AI will look for the Entity */
	protected float maxDistanceForPlayer;
	private int lookTime;
	private final float chance;
	protected Class<? extends Entity> watchedClass;

	public EntityAITeleportFollower(EntityLiving entityIn, Class<? extends Entity> watchTargetClass, float maxDistance)
	{
		this.entity = entityIn;
		this.watchedClass = watchTargetClass;
		this.maxDistanceForPlayer = maxDistance;
		this.chance = 0.02F;
		this.setMutexBits(2);
	}

	public EntityAITeleportFollower(EntityLiving entityIn, Class<? extends Entity> watchTargetClass, float maxDistance, float chanceIn)
	{
		this.entity = entityIn;
		this.watchedClass = watchTargetClass;
		this.maxDistanceForPlayer = maxDistance;
		this.chance = chanceIn;
		this.setMutexBits(3);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	public boolean shouldExecute()
	{
		if (this.entity.getRNG().nextFloat() >= this.chance)
		{
			return false;
		} else
		{
			this.closestEntity = this.entity.world.findNearestEntityWithinAABB(this.watchedClass, this.entity.getEntityBoundingBox().grow(40D, 10.0D, 40D), this.entity);
			return this.closestEntity != null;
		}
	}

	/**
	 * Execute a one shot task or start executing a continuous task
	 */
	public void startExecuting()
	{
		super.startExecuting();
	}

	/**
	 * Reset the task's internal state. Called when this task is interrupted by another one
	 */
	public void resetTask()
	{
		this.closestEntity = null;
	}

	/**
	 * Keep ticking a continuous task that has already been started
	 */
	public void updateTask()
	{
		double range = MathHelper.sqrt(this.entity.getDistanceSq(this.closestEntity));
		if (range >= 20.0D)
		{
			int i = MathHelper.floor(this.closestEntity.posX) - 2;
			int j = MathHelper.floor(this.closestEntity.posZ) - 2;
			int k = MathHelper.floor(this.closestEntity.getEntityBoundingBox().minY);

			for (int l = 0; l <= 4; ++l)
			{
				for (int i1 = 0; i1 <= 4; ++i1)
				{
					if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.isTeleportFriendlyBlock(i, j, k, l, i1))
					{
						this.entity.setLocationAndAngles((double) ((float) (i + l) + 0.5F), (double) k, (double) ((float) (j + i1) + 0.5F), this.entity.rotationYaw, this.entity.rotationPitch);
						return;
					}
				}
			}
		}
	}

	protected boolean isTeleportFriendlyBlock(int x, int p_192381_2_, int y, int p_192381_4_, int p_192381_5_)
	{
		BlockPos blockpos = new BlockPos(x + p_192381_4_, y - 1, p_192381_2_ + p_192381_5_);
		IBlockState iblockstate = this.entity.world.getBlockState(blockpos);
		return iblockstate.getBlockFaceShape(this.entity.world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID && iblockstate.canEntitySpawn(this.entity) && this.entity.world.isAirBlock(blockpos.up()) && this.entity.world.isAirBlock(blockpos.up(2));
	}
}