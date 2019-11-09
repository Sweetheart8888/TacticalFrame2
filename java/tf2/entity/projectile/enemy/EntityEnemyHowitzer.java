package tf2.entity.projectile.enemy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import tf2.common.TFExplosion;
import tf2.entity.projectile.EntityTFProjectile;
import tf2.entity.projectile.IEnemyProjectile;

public class EntityEnemyHowitzer extends EntityTFProjectile implements IEnemyProjectile
{
	protected double spread;
	public EntityEnemyHowitzer(World worldIn)
	{
		super(worldIn);
		this.setSize(0.2F, 0.2F);
	}

	public EntityEnemyHowitzer(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public EntityEnemyHowitzer(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
		this.setTickAir(100);
	}
	@Override
	public float inWaterSpeed()
	{
		return 0.8F;
	}
	@Override
	protected float directHitDamage()
	{
		return 0F;
	}


	@Override
	public void setEntityDead()
	{
		TFExplosion.doExplosion(this.world, this.thrower, this.posX, this.posY, this.posZ, this.spread, this.getDamage());
		this.world.createExplosion((Entity) null, this.posX, this.posY, this.posZ, 0.0F, false);
		super.setDead();
	}

	@Override
	public void setDead()
	{
		this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
		super.setDead();
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
