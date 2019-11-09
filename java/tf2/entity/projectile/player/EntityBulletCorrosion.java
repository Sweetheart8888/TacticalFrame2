package tf2.entity.projectile.player;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import tf2.entity.projectile.EntityTFProjectile;
import tf2.potion.TFPotionPlus;

public class EntityBulletCorrosion extends EntityTFProjectile
{
	public EntityBulletCorrosion(World worldIn)
	{
		super(worldIn);
		this.setSize(0.2F, 0.2F);
	}

	public EntityBulletCorrosion(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public EntityBulletCorrosion(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
	}

	@Override
	public void bulletHit(EntityLivingBase living)
	{
		super.bulletHit(living);
		if (!this.world.isRemote)
		{
			living.addPotionEffect(new PotionEffect(TFPotionPlus.VULNERABILITY, 100, 1));
		}
	}
}
