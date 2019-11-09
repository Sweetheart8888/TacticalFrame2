package tf2.entity.mob.ai;

import java.util.List;

import com.google.common.base.Predicates;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.IMob;
import net.minecraft.util.EntitySelectors;
import tf2.entity.mob.frend.EntityFriendMecha;

public class EntityAINearestAttackbleTargetFriend<T extends EntityLivingBase> extends EntityAIBase
{
    protected final EntityFriendMecha taskOwner;
//    protected final Class<T> target;
    protected final int targetChance;
    protected final boolean checkSight;
    protected EntityLivingBase targetEntity;

    public EntityAINearestAttackbleTargetFriend(EntityFriendMecha ownerIn, int chanceIn, boolean checkSight)
    {
        this.taskOwner = ownerIn;
//        this.target = targetIn;
        this.targetChance = chanceIn;
        this.checkSight = checkSight;

        this.setMutexBits(1);
    }

	@Override
	public boolean shouldExecute()
	{
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0)
        {
            return false;
        }
//        if(this.taskOwner instanceof EntityGynoid && this.taskOwner.getMechaLevel() < 14)
//        {
//        	return false;
//        }

    	if(this.taskOwner.getAttackTarget() == null && this.taskOwner.getMechaMode() != 0)
    	{
    		List<EntityLivingBase> list = this.taskOwner.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.taskOwner.getEntityBoundingBox().grow(this.taskOwner.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue()), Predicates.<EntityLivingBase>and(EntitySelectors.IS_ALIVE));

    		for(int lists = 0; lists < list.size(); lists++)
    		{
    			if(list.get(lists).isEntityAlive() && (list.get(lists) instanceof EntityMob || list.get(lists) instanceof IMob))
    			{
    				if(this.checkSight)
    				{
    					if(this.taskOwner.canEntityBeSeen(list.get(lists)))
    					{
    	    				this.targetEntity = list.get(lists);
    	    				return true;
    					}
    				}
    				else
    				{
        				this.targetEntity = list.get(lists);
        				return true;
    				}
    			}
    		}
    	}
    	return false;
	}

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }
}
