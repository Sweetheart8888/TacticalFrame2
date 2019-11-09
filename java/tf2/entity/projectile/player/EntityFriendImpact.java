package tf2.entity.projectile.player;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import tf2.entity.projectile.IFriendProjectile;

public class EntityFriendImpact extends EntityImpact implements IFriendProjectile
{
	public EntityFriendImpact(World worldIn)
	{
		super(worldIn);
		this.setSize(0.5F, 0.5F);
	}

	public EntityFriendImpact(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public EntityFriendImpact(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
	}
}
