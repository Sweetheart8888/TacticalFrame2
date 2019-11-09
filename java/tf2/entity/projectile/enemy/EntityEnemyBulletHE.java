package tf2.entity.projectile.enemy;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import tf2.common.TFExplosion;
import tf2.entity.projectile.EntityTFProjectile;
import tf2.entity.projectile.IEnemyProjectile;
import tf2.potion.TFPotionPlus;

public class EntityEnemyBulletHE extends EntityTFProjectile implements IEnemyProjectile
{
	protected int amplifier;

	public EntityEnemyBulletHE(World worldIn)
	{
		super(worldIn);
		this.setSize(0.25F, 0.25F);
	}

	public EntityEnemyBulletHE(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public EntityEnemyBulletHE(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
		this.setTickAir(50);
	}

	@Override
	public float inWaterSpeed()
	{
		return 0.95F;
	}

	@Override
	public void bulletHit(EntityLivingBase living)
	{
		if (living instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) living;
			if (!player.isActiveItemStackBlocking())
			{
				if (!this.world.isRemote)
				{
					living.addPotionEffect(new PotionEffect(TFPotionPlus.HEAT, 200, this.amplifier));
				}
			}
		}
		else
		{
			if (!this.world.isRemote)
			{
				living.addPotionEffect(new PotionEffect(TFPotionPlus.HEAT, 200, this.amplifier));
			}
		}
	}

	@Override
	protected float directHitDamage()
	{
		return 0F;
	}

	@Override
	public void setEntityDead()
	{
		TFExplosion.doExplosion(this.world, this.thrower, this.posX, this.posY, this.posZ, 3.0D, this.getDamage());
		this.world.createExplosion((Entity) null, this.posX, this.posY, this.posZ, 0.0F, false);
		super.setDead();
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound compound)
	{
		super.writeEntityToNBT(compound);
		compound.setInteger("Amplifier", this.amplifier);
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound compound)
	{
		super.readEntityFromNBT(compound);
		if (compound.hasKey("Amplifier", 99))
		{
			this.amplifier = compound.getInteger("Amplifier");
		}

	}

	public void setAmplifier(int amplifierIn)
	{
		this.amplifier = amplifierIn;
	}

	public int getAmplifier()
	{
		return this.amplifier;
	}
}
