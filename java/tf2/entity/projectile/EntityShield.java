package tf2.entity.projectile;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityShield extends Entity
{
	public EntityShield(World worldIn)
	{
		super(worldIn);
		this.noClip = this.hasNoGravity();
		this.setSize(1.8F, 1.5F);
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound)
	{

	}

}
