package tf2.entity.mob.ai;

import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import tf2.entity.mob.frend.EntityFriendMecha;

public class EntityAIAttackRangedFriendMecha extends EntityAIBase
{
    /** The entity the AI instance has been applied to */
    private final EntityLiving entityHost;
    /** The entity (as a RangedAttackMob) the AI instance has been applied to. */
    private final IRangedAttackMob rangedAttackEntityHost;
    private EntityLivingBase attackTarget;
    /**
     * A decrementing tick that spawns a ranged attack once this value reaches 0. It is then set back to the
     * maxRangedAttackTime.
     */
    private int rangedAttackTime;
    private final double entityMoveSpeed;
    private int seeTime;
    private final int attackIntervalMin;
    /** The maximum time the AI has to wait before peforming another ranged attack. */
    private final int maxRangedAttackTime;
    private final float attackRadius;
    private final float maxAttackDistance;

    public EntityAIAttackRangedFriendMecha(IRangedAttackMob attacker, double movespeed, int maxAttackTime, float maxAttackDistanceIn)
    {
        this(attacker, movespeed, maxAttackTime, maxAttackTime, maxAttackDistanceIn);
    }

    public EntityAIAttackRangedFriendMecha(IRangedAttackMob attacker, double movespeed, int p_i1650_4_, int maxAttackTime, float maxAttackDistanceIn)
    {
        this.rangedAttackTime = -1;

        if (!(attacker instanceof EntityLivingBase))
        {
            throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
        }
        else
        {
            this.rangedAttackEntityHost = attacker;
            this.entityHost = (EntityLiving)attacker;
            this.entityMoveSpeed = movespeed;
            this.attackIntervalMin = p_i1650_4_;
            this.maxRangedAttackTime = maxAttackTime;
            this.attackRadius = maxAttackDistanceIn;
            this.maxAttackDistance = maxAttackDistanceIn * maxAttackDistanceIn;
            this.setMutexBits(3);
        }
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        EntityLivingBase entitylivingbase = this.entityHost.getAttackTarget();

        if (entitylivingbase == null)
        {
            return false;
        }
        else
        {
            this.attackTarget = entitylivingbase;
    		if(this.entityHost instanceof EntityFriendMecha)
    		{
    			EntityFriendMecha mecha = (EntityFriendMecha) this.entityHost;
    			if(mecha.getOwner() != null && mecha.getMechaMode() == 1)
    			{
    				return mecha.getDistanceSq(mecha.getOwner()) < 420.0D;
    			}
    			if(mecha.getHomePosition() != null && mecha.getMechaMode() == 2)
    			{
    				return mecha.getDistanceSq(mecha.getHomePosition()) < 420.0D;
    			}
    		}
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return this.shouldExecute() || !this.entityHost.getNavigator().noPath();
    }

    /**
     * Reset the task's internal state. Called when this task is interrupted by another one
     */
    public void resetTask()
    {

        this.attackTarget = null;
        this.seeTime = 0;
        this.rangedAttackTime = -1;

		if(this.entityHost instanceof EntityFriendMecha)
		{
			EntityFriendMecha mecha = (EntityFriendMecha) this.entityHost;
			if(mecha.getOwner() != null && mecha.getMechaMode() == 1)
			{
                int i = MathHelper.floor(mecha.getOwner().posX) - 2;
                int j = MathHelper.floor(mecha.getOwner().posZ) - 2;
                int k = MathHelper.floor(mecha.getOwner().getEntityBoundingBox().minY);

                for (int l = 0; l <= 4; ++l)
                {
                    for (int i1 = 0; i1 <= 4; ++i1)
                    {
                        if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.isTeleportFriendlyBlock(i, j, k, l, i1))
                        {
                            mecha.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), mecha.rotationYaw, mecha.rotationPitch);
                            mecha.getNavigator().clearPath();
                            return;
                        }
                    }
                }
			}
			if(mecha.getHomePosition() != null && mecha.getMechaMode() == 2)
			{
                int i = MathHelper.floor(mecha.getHomePosition().getX()) - 2;
                int j = MathHelper.floor(mecha.getHomePosition().getZ()) - 2;
                int k = MathHelper.floor(mecha.getHomePosition().getY());

                for (int l = 0; l <= 4; ++l)
                {
                    for (int i1 = 0; i1 <= 4; ++i1)
                    {
                        if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.isTeleportFriendlyBlock(i, j, k, l, i1))
                        {
                            mecha.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), mecha.rotationYaw, mecha.rotationPitch);
                            mecha.getNavigator().clearPath();
                            return;
                        }
                    }
                }
			}
		}
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        double d0 = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.getEntityBoundingBox().minY, this.attackTarget.posZ);
        boolean flag = this.entityHost.getEntitySenses().canSee(this.attackTarget);

        if (flag)
        {
            ++this.seeTime;
        }
        else
        {
            this.seeTime = 0;
        }

        if (d0 <= (double)this.maxAttackDistance && this.seeTime >= 20)
        {
            this.entityHost.getNavigator().clearPath();
        }
        else
        {
            this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
        }

        this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0F, 30.0F);

        if (--this.rangedAttackTime == 0)
        {
            if (!flag)
            {
                return;
            }

            float f = MathHelper.sqrt(d0) / this.attackRadius;
            float lvt_5_1_ = MathHelper.clamp(f, 0.1F, 1.0F);
            this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, lvt_5_1_);
            this.rangedAttackTime = MathHelper.floor(f * (float)(this.maxRangedAttackTime - this.attackIntervalMin) + (float)this.attackIntervalMin);
        }
        else if (this.rangedAttackTime < 0)
        {
            float f2 = MathHelper.sqrt(d0) / this.attackRadius;
            this.rangedAttackTime = MathHelper.floor(f2 * (float)(this.maxRangedAttackTime - this.attackIntervalMin) + (float)this.attackIntervalMin);
        }
    }

    protected boolean isTeleportFriendlyBlock(int x, int p_192381_2_, int y, int p_192381_4_, int p_192381_5_)
    {
        BlockPos blockpos = new BlockPos(x + p_192381_4_, y - 1, p_192381_2_ + p_192381_5_);
        IBlockState iblockstate = entityHost.world.getBlockState(blockpos);
        return iblockstate.getBlockFaceShape(entityHost.world, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID && iblockstate.canEntitySpawn(this.entityHost) && entityHost.world.isAirBlock(blockpos.up()) && entityHost.world.isAirBlock(blockpos.up(2));
    }
}
