package tf2.entity.projectile.player;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import tf2.entity.projectile.EntityTFProjectile;

public class EntityBulletBig extends EntityTFProjectile
{
	public EntityBulletBig(World worldIn)
	{
		super(worldIn);
		this.setSize(0.25F, 0.25F);
	}

	public EntityBulletBig(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public EntityBulletBig(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
		super.setTickAir(50);
	}

	@Override
	public float inWaterSpeed()
	{
		return 0.9F;
	}
}
