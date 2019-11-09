package tf2.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import tf2.TFItems;

public class EntityItemSpawnFriendMecha extends EntityItem
{
	public EntityItemSpawnFriendMecha(World worldIn)
	{
		super(worldIn);
	}

	public EntityItemSpawnFriendMecha(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public EntityItemSpawnFriendMecha(World worldIn, double x, double y, double z, ItemStack stack)
	{
		super(worldIn, x, y, z, stack);
	}

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.world.isRemote || this.isDead) return false; //Forge: Fixes MC-53850

        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else if (!this.getItem().isEmpty() && this.getItem().getItem() == TFItems.SPAWNFM && source.isExplosion())
        {
            return false;
        }

        return false;
    }
}
