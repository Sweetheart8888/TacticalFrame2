package tf2.entity.projectile.enemy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.common.TFExplosion;
import tf2.entity.projectile.EntityTFProjectile;
import tf2.entity.projectile.IEnemyProjectile;

public class EntityEnemyGrenade extends EntityTFProjectile implements IEnemyProjectile
{
    public EntityEnemyGrenade(World worldIn)
	{
		super(worldIn);
		this.setSize(0.2F, 0.2F);
	}

	public EntityEnemyGrenade(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public EntityEnemyGrenade(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
		this.setTickAir(50);
	}

    @Override
    public void setEntityDead()
    {
 		TFExplosion.doExplosion(this.world, this.thrower, this.posX, this.posY, this.posZ, 3.0D, this.getDamage());
		this.world.createExplosion((Entity) null, this.posX, this.posY, this.posZ, 0.0F, false);
		super.setDead();
    }
    @Override
   	protected float directHitDamage()
   	{
   		return 0F;
   	}
    @Override
    @SideOnly(Side.CLIENT)
    public void generateRandomParticles()
    {
        double var211 = this.prevPosX - this.posX;
        double var231 = this.prevPosY - this.posY;
        double var23 = this.prevPosZ - this.posZ;

        if (this.world.isRemote)
        {
        	for (int var24 = 0; var24 < 5; ++var24)
            {
        		float var16 = 0.2F * (float)var24;

                this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL,  this.posX + var211 * (double)var16, this.posY + 0.1D + var231 * (double)var16, this.posZ + var23 * (double)var16, 0.0D, 0.0D, 0.0D, new int[15]);
            }
        }
    }
}
