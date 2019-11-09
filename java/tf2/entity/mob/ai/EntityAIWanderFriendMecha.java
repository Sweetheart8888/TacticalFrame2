package tf2.entity.mob.ai;

import javax.annotation.Nullable;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import tf2.entity.mob.frend.EntityFriendMecha;

public class EntityAIWanderFriendMecha extends EntityAIBase
{
    protected final EntityFriendMecha entity;
    protected double x;
    protected double y;
    protected double z;
    protected final double speed;
    protected int executionChance;
    protected boolean mustUpdate;

    public EntityAIWanderFriendMecha(EntityFriendMecha creatureIn, double speedIn)
    {
        this(creatureIn, speedIn, 120);
    }

    public EntityAIWanderFriendMecha(EntityFriendMecha creatureIn, double speedIn, int chance)
    {
        this.entity = creatureIn;
        this.speed = speedIn;
        this.executionChance = chance;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
    	if(this.entity.getMechaMode() != 2 || this.entity.getHomePosition() == null)
    	{
    		return false;
    	}

    	if(this.entity.getDistanceSq(this.entity.getHomePosition()) > 128.0D && this.entity.motionX == 0 && this.entity.motionZ == 0)
    	{
            this.x = this.entity.getHomePosition().getX();
            this.y = this.entity.getHomePosition().getY();
            this.z = this.entity.getHomePosition().getZ();
            this.mustUpdate = false;
            return true;
    	}

        if (!this.mustUpdate)
        {
            if (this.entity.getIdleTime() >= 100)
            {
                return false;
            }

            if (this.entity.getRNG().nextInt(this.executionChance) != 0)
            {
                return false;
            }
        }

        Vec3d vec3d = this.getPosition();

        if (vec3d == null)
        {
            return false;
        }
        else
        {
        	BlockPos blockpos = new BlockPos(vec3d.x, vec3d.y, vec3d.z);

        	if(blockpos.distanceSq(this.entity.getHomePosition().getX(), this.entity.getHomePosition().getY(), this.entity.getHomePosition().getZ()) > 128.0D)
        	{
        		return false;
        	}

            this.x = vec3d.x;
            this.y = vec3d.y;
            this.z = vec3d.z;
            this.mustUpdate = false;
            return true;
        }
    }

    @Nullable
    protected Vec3d getPosition()
    {
        return RandomPositionGenerator.findRandomTarget(this.entity, 10, 7);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return !this.entity.getNavigator().noPath();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.entity.getNavigator().tryMoveToXYZ(this.x, this.y, this.z, this.speed);
    }

    /**
     * Makes task to bypass chance
     */
    public void makeUpdate()
    {
        this.mustUpdate = true;
    }

    /**
     * Changes task random possibility for execution
     */
    public void setExecutionChance(int newchance)
    {
        this.executionChance = newchance;
    }
}