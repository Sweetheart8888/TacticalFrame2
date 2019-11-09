package tf2.tile;

import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import tf2.entity.mob.frend.EntityFriendMecha;
import tf2.util.FriendMechaHelper;


public abstract class InventoryFriendMecha extends InventoryBasic
{

	private EntityFriendMecha entityMecha;

	public InventoryFriendMecha(EntityFriendMecha entityMecha, int slotCount)
	{
		super(entityMecha.getName(), true, slotCount);

		this.entityMecha = entityMecha;
	}

	public EntityFriendMecha getContainerEntityFriendMecha()
	{
		return this.entityMecha;
	}

	public void readInventoryFromNBT(NBTTagList nbtList)
	{
		this.clear();

		for (int i = 0; i < nbtList.tagCount(); ++i)
		{
			NBTTagCompound nbt = nbtList.getCompoundTagAt(i);
			int slot = nbt.getByte("Slot") & 255;

			if ((0 <= slot) && (slot < this.getSizeInventory()))
			{
				this.setInventorySlotContents(slot, new ItemStack(nbt));
			}
		}
	}

	public NBTTagList writeInventoryToNBT()
	{
		NBTTagList nbtList = new NBTTagList();

		for (int slot = 0; slot < this.getSizeInventory(); ++slot)
		{
			ItemStack stackSlot = this.getStackInSlot(slot);

			if (FriendMechaHelper.isNotEmptyItemStack(stackSlot))
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setByte("Slot", (byte) slot);
				stackSlot.writeToNBT(nbt);
				nbtList.appendTag(nbt);
			}
		}

		return nbtList;
	}

}
