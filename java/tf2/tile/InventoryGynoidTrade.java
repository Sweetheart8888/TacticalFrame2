package tf2.tile;

import net.minecraft.item.ItemStack;
import tf2.entity.mob.frend.EntityFriendMecha;

public class InventoryGynoidTrade extends InventoryFriendMecha
{

	public InventoryGynoidTrade(EntityFriendMecha entityMecha)
	{
		super(entityMecha, 2);
	}

	public ItemStack getSellItem()
	{
		return this.getStackInSlot(0);
	}

	public ItemStack getBuyItem()
	{
		return this.getStackInSlot(1);
	}
}
