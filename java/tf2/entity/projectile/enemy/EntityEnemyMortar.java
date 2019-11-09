package tf2.entity.projectile.enemy;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import tf2.entity.projectile.IEnemyProjectile;
import tf2.entity.projectile.player.EntityMortar;

public class EntityEnemyMortar extends EntityMortar implements IEnemyProjectile
{
	public EntityEnemyMortar(World worldIn)
	{
		super(worldIn);
		this.setSize(0.25F, 0.25F);
		this.setTickAir(200);
	}

	public EntityEnemyMortar(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public EntityEnemyMortar(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
	}
}
