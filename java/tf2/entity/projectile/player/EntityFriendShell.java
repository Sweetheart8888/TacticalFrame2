package tf2.entity.projectile.player;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import tf2.entity.projectile.IFriendProjectile;

public class EntityFriendShell extends EntityFriendBullet implements IFriendProjectile
{
	public EntityFriendShell(World worldIn)
	{
		super(worldIn);
		this.setSize(0.25F, 0.25F);
	}

	public EntityFriendShell(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public EntityFriendShell(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
	}
}