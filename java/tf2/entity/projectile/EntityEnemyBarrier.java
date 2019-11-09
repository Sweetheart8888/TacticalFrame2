package tf2.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class EntityEnemyBarrier extends EntityBarrier
{
    public EntityEnemyBarrier(World worldIn)
    {
        super(worldIn);
    }

    public EntityEnemyBarrier(World worldIn, EntityLivingBase shooter, int life, int ticks, int max, int amount, double distance)
    {
        super(worldIn, shooter, life, ticks, max, amount, distance);
    }

//    @Override
//    public void onImpact(Entity targetIn)
//    {
//        EntityLivingBase owner = this.getShooter();
//
//        if(targetIn instanceof IProjectile && !(targetIn instanceof EntityEnemyProjectile))
//        {
//    		this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ITEM_SHIELD_BREAK, this.getSoundCategory(), 1.0F, 3.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
//
//    		this.setDamageTaken(this.getDamageTaken() + 1);
//    		targetIn.setDead();
//        }
//    }

//    public static void registerEntity(Class<EntityEnemyBarrier> clazz, ResourceLocation registryName, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
//	{
//		EntityRegistry.registerModEntity(registryName, clazz, registryName.getResourceDomain() + "." + registryName.getResourcePath(), RegistryHandler.entityId++, TF2Core.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates);
//	}
}
