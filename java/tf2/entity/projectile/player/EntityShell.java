package tf2.entity.projectile.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.common.TFExplosion;
import tf2.entity.projectile.EntityTFProjectile;

public class EntityShell extends EntityTFProjectile
{
	public EntityShell(World worldIn)
	{
		super(worldIn);
		this.setSize(0.25F, 0.25F);
		this.setTickAir(200);
	}

	public EntityShell(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public EntityShell(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
		this.setTickAir(200);
	}

	@Override
	public float inWaterSpeed()
	{
		return 0.8F;
	}

	@Override
	public void setEntityDead()
    {
 		TFExplosion.doExplosion(this.world, this.thrower, this.posX, this.posY, this.posZ, 10D, this.getDamage());
		this.world.createExplosion((Entity) null, this.posX, this.posY, this.posZ, 0.0F, false);
		super.setDead();
    }
	@Override
    public void setDead()
	{
    	for (int var3 = 0; var3 < 10; ++var3)
		{
			double r = this.rand.nextDouble() * 8.0D;
			double i = this.rand.nextDouble() * Math.PI * 2.0D;
			double parX = this.posX + r * Math.sin(i);
			double parZ = this.posZ + r * Math.cos(i);
			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, parX, this.posY + this.rand.nextDouble(), parZ, 0.0D, 0.0D, 0.0D, new int[0]);
		}
    	for (int i = 0; i < 180; ++i)
		{
			double r = 1.0D + this.rand.nextDouble() * 1.5D;
			double t = this.rand.nextDouble() * 2 * Math.PI;

			double e0 = r * Math.sin(t);
			double e2 = r * Math.cos(t);

			double r1 = 3.0D;

			double d4 = this.posX + 0.5D + r1 * Math.sin(t);
			double d6 = this.posZ + 0.5D + r1 * Math.cos(t);
			this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, d4, this.posY, d6, e0, 0F, e2, new int[0]);
		}
		super.setDead();
	}

	@Override
	public void isGravity()
	{
		if (!this.hasNoGravity())
        {
            this.motionY -= 0.05D;
        }
		else if (this.isInWater())
		{
			this.motionY -= 0.05D;
		}
	}

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

                this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL,  this.posX + var211 * (double)var16, this.posY + 0.1D + var231 * (double)var16, this.posZ + var23 * (double)var16, 0.0D, 0.0D, 0.0D, new int[15]);
            }
        }
    }

    @Override
	protected float directHitDamage()
	{
		return 0F;
	}
}
