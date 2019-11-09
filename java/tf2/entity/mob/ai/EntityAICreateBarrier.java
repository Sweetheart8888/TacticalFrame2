package tf2.entity.mob.ai;

import java.util.List;

import com.google.common.base.Predicates;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundCategory;
import tf2.entity.projectile.EntityBarrier;
import tf2.entity.projectile.EntityEnemyBarrier;
/**
 * @author Arata
 *
 */
public class EntityAICreateBarrier extends EntityAIBase
{
    protected final EntityLiving entity;

    protected final int amount;
    protected final int life;
    protected final int ticks;
    protected final int spawnTicks;
    protected final int respawnTicks;
    protected final double distance;

    private int maxAmount;
    private int seeTime;


    /**
     * Entityの周囲を回転する{@link EntityEnemyBarrier}を生成するEntityAI
     *
     * @param entityIn			回転するバリアの中心になるEntity
     * @param amountIn			バリアの枚数
     * @param lifeAmount		バリアの体力. -1の場合はダメージで消滅しない
     * @param spawnTick 		バリアが生成されるまでの時間
     * @param respawnTick		バリアが再生成されるまでの時間. 必ずspawnTickよりも低い値にすること
     * @param limitedLifeTick	バリアが消滅するまでの時間. -1の場合は時間で消滅しない
     * @param distance     	回転するバリアの中心からの半径
     * @author 新(あらた)
     */
    public EntityAICreateBarrier(EntityLiving entityLivingIn, int amountIn, int lifeAmount, int spawnTick, int respawnTick, int limitedLifeTick, double distance)
    {
        this.entity = entityLivingIn;
        this.amount = amountIn;
        this.life = lifeAmount;
        this.spawnTicks = spawnTick;
        this.respawnTicks = respawnTick;
        this.ticks = limitedLifeTick;
        this.distance = distance;
        this.setMutexBits(4);
        this.seeTime = spawnTick;
    }

    @Override
	public boolean shouldExecute()
	{
		return this.entity.getAttackTarget() == null ? false : true;
	}


    @Override
	public boolean shouldContinueExecuting()
	{
		return (this.shouldExecute() || !this.entity.getNavigator().noPath());
	}

    @Override
	public void resetTask()
	{
		super.resetTask();
		this.seeTime = 0;
	}

    public void updateTask()
	{
    	/**デバッグ用*/
//    	if(this.seeTime % 20 == 0)
//    		System.out.println(this.seeTime);

		EntityLivingBase attackTarget = this.entity.getAttackTarget();

		if (attackTarget != null)
		{
			boolean flag = this.entity.getEntitySenses().canSee(attackTarget);
			boolean flag1 = this.seeTime > 0;
			boolean flag2 = false;

			List<EntityBarrier> list = this.entity.world.<EntityBarrier>getEntitiesWithinAABB(EntityBarrier.class, this.entity.getEntityBoundingBox().grow(10.0D), Predicates.<EntityBarrier>and(EntitySelectors.NOT_SPECTATING));

            for (int i = 0; i < list.size() && !list.isEmpty() && !flag2; ++i)
            {
            	EntityBarrier entityBarrier = list.get(i);
            	if(entityBarrier.getShooterUuid() == this.entity.getUniqueID())
            	{
            		flag2 = true;
            	}
            	else
            	{
            		flag2 = false;
            	}
			}

            if(list.isEmpty())
            {
            	flag2 = false;
            }

			++this.seeTime;

			if (this.seeTime >= respawnTicks)
			{
				if(!flag2)
				{
					if(this.seeTime >= spawnTicks)
					{
						this.createBarrier(this.amount, this.life, this.ticks);
						this.seeTime = 0;
					}
				}
				else
				{
					this.seeTime = respawnTicks;
				}
			}
		}
	}

    public void createBarrier(int amount, int life, int tick)
    {
    	if (!this.entity.world.isRemote)
	    {
			for(int i = amount;i >= 1;i--)
			{
				if(maxAmount < i)
				{
					maxAmount = i;
				}

				EntityEnemyBarrier barrier = new EntityEnemyBarrier(entity.world, entity, life, tick, maxAmount, i, distance);
	        	barrier.posY = entity.posY + entity.height / 4;
	        	barrier.posX = entity.posX - Math.sin((barrier.ticksExisted + (45 / maxAmount) * i) * Math.PI / 22.5D) * distance;
	    		barrier.posZ = entity.posZ - Math.cos((barrier.ticksExisted + (45 / maxAmount) * i) * Math.PI / 22.5D) * distance;
	    		entity.world.spawnEntity(barrier);
			}
			entity.world.playSound((EntityPlayer) null, entity.posX, entity.posY, entity.posZ,
					SoundEvents.ENTITY_ENDERMEN_TELEPORT, SoundCategory.PLAYERS, 0.75F, 1.0F);
        }
    }
}