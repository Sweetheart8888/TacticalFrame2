package tf2.entity.projectile.enemy;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import tf2.entity.projectile.IEnemyProjectile;
import tf2.entity.projectile.player.EntityImpact;

public class EntityEnemyImpact extends EntityImpact implements IEnemyProjectile
{
	public EntityEnemyImpact(World worldIn)
	{
		super(worldIn);
		this.setSize(0.5F, 0.5F);
	}

	public EntityEnemyImpact(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public EntityEnemyImpact(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
		this.setTickAir(50);
	}
}
