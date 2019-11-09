package tf2.entity.projectile.player;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import tf2.entity.projectile.EntityTFProjectile;

public class EntityHomingMissile extends EntityTFProjectile
{
	protected EntityLivingBase owner;
    protected EntityLivingBase target;
    protected double spread;

	public EntityHomingMissile(World worldIn)
	{
		super(worldIn);
		this.setSize(0.2F, 0.2F);
	}

	public EntityHomingMissile(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public EntityHomingMissile(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
		this.owner = throwerIn;
		this.setTickAir(200);
	}

    @Override
	public void isGravity()
	{}

    @Override
    public void setEntityDead()
    {
        super.setDead();

    	this.world.createExplosion((Entity) null, this.posX, this.posY,this.posZ, 0.0F, false);
 		List var7 = this.world.getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(this.spread));
 		int var3;
 		for (var3 = 0; var3 < var7.size(); ++var3)
 		{
 			EntityLivingBase var8 = (EntityLivingBase)var7.get(var3);

			DamageSource var201 = this.damageSource();
 			var8.attackEntityFrom(var201, (float)this.damage);
 			var8.hurtResistantTime = 0;

 		}
    }

	public void setSpread(double damageIn)
	{
		this.spread = damageIn;
	}
	public double getSpread()
	{
		return this.spread;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setDouble("spread", this.spread);
	}

	/**
	 * (abstract) Protected helper method to read subclass entity data from NBT.
	 */
	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		this.spread = compound.getDouble("spread");
	}
}
