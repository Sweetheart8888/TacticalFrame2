package tf2.tile.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import tf2.entity.mob.frend.EntityFriendMecha;
import tf2.items.skill.ItemSkillBase;
import tf2.items.skill.friendskill.ItemMechaSkillBase;

public class ContainerFriendMechaInventory  extends Container
{

	private EntityFriendMecha entityMecha;
	private EntityPlayer entityPlayer;
	private int slotSize;

	public ContainerFriendMechaInventory(EntityFriendMecha entityMecha, EntityPlayer entityPlayer)
	{
		int column;
		int row;
		int index;
		this.slotSize = entityMecha.slotSize;

		if(slotSize > 3)
			slotSize = 3;


		entityMecha.getInventoryMechaEquipment().openInventory(entityPlayer);

		for (index = 0; index < slotSize; ++index)
		{
			switch (index)
			{
				case 0 :

					this.addSlotToContainer(new Slot(entityMecha.getInventoryMechaEquipment(), index, 83, 86)
					{
						@Override
						public boolean isItemValid(ItemStack stack)
						{
							if(stack.getItem() == entityMecha.getInventoryMechaEquipment().getSkillBItem().getItem() || stack.getItem() == entityMecha.getInventoryMechaEquipment().getSkillBItem().getItem())
							{
								return false;
							}

							if (stack.getItem() instanceof ItemMechaSkillBase)
							{
								return entityPlayer == entityMecha.getOwner() ? true : false;
							}
							return false;
						}

						@Override
					    public boolean canTakeStack(EntityPlayer playerIn)
					    {
							return entityPlayer == entityMecha.getOwner() ? true : false;
					    }

						@Override
					    public int getSlotStackLimit()
					    {
					        return 1;
					    }
					});
					break;

				case 1 :

					this.addSlotToContainer(new Slot(entityMecha.getInventoryMechaEquipment(), index, 101, 86)
					{
						@Override
						public boolean isItemValid(ItemStack stack)
						{
							if(stack.getItem() == entityMecha.getInventoryMechaEquipment().getSkillAItem().getItem() || stack.getItem() == entityMecha.getInventoryMechaEquipment().getSkillCItem().getItem())
							{
								return false;
							}

							if (stack.getItem() instanceof ItemMechaSkillBase)
							{
								return entityPlayer == entityMecha.getOwner() ? true : false;
							}
							return false;
						}

						@Override
					    public boolean canTakeStack(EntityPlayer playerIn)
					    {
							return entityPlayer == entityMecha.getOwner() ? true : false;
					    }

						@Override
					    public int getSlotStackLimit()
					    {
					        return 1;
					    }
					});
					break;

				case 2 :

					this.addSlotToContainer(new Slot(entityMecha.getInventoryMechaEquipment(), index, 119, 86)
					{
						@Override
						public boolean isItemValid(ItemStack stack)
						{
							if(stack.getItem() == entityMecha.getInventoryMechaEquipment().getSkillAItem().getItem() || stack.getItem() == entityMecha.getInventoryMechaEquipment().getSkillBItem().getItem())
							{
								return false;
							}

							if (stack.getItem() instanceof ItemMechaSkillBase)
							{
								return entityPlayer == entityMecha.getOwner() ? true : false;
							}
							return false;
						}

						@Override
					    public boolean canTakeStack(EntityPlayer playerIn)
					    {
							return entityPlayer == entityMecha.getOwner() ? true : false;
					    }

						@Override
					    public int getSlotStackLimit()
					    {
					        return 1;
					    }
					});
					break;

			}
		}

		entityPlayer.inventory.openInventory(entityPlayer);

		for (column = 0; column < 3; ++column)
		{
			for (row = 0; row < 9; ++row)
			{
				index = (row + column * 9 + 9);

				this.addSlotToContainer(new Slot(entityPlayer.inventory, index, (row * 18) + 8, (column * 18) + 118));
			}
		}

		for (row = 0; row < 9; ++row)
		{
			index = row;

			this.addSlotToContainer(new Slot(entityPlayer.inventory, index, (row * 18) + 8, 176));
		}

		this.entityMecha = entityMecha;
		this.entityPlayer = entityPlayer;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return this.entityMecha.getInventoryMechaEquipment().isUsableByPlayer(playerIn) && this.entityMecha.isEntityAlive() && this.entityMecha.getDistance(playerIn) < 8.0F;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int index)
	{
		ItemStack stackEmpty = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot == null || slot.getHasStack() == false)
		{
			return stackEmpty;
		}

		ItemStack srcItemStack = slot.getStack();
		ItemStack dstItemStack = srcItemStack.copy();

		if(index < slotSize )
		{
			if (!this.mergeItemStack(dstItemStack, 2, this.inventorySlots.size(), false))
			{
				return stackEmpty;
			}
		}
		else if (index < 27 + slotSize)
		{
			if(srcItemStack.getItem() instanceof ItemSkillBase)
			{

				if (!this.mergeItemStack(dstItemStack, 0, slotSize, false))
				{
					if (!this.mergeItemStack(dstItemStack, 27 + slotSize, this.inventorySlots.size(), true))
					{
						return stackEmpty;
					}
				}

			}
			else
			{
				if (!this.mergeItemStack(dstItemStack, 27 + slotSize, this.inventorySlots.size(), true))
				{
					return stackEmpty;
				}
			}
		}
		else
		{
			if (!this.mergeItemStack(dstItemStack, 0, 27 + slotSize, false))
			{
				return stackEmpty;
			}
		}

		if (dstItemStack.isEmpty())
		{
			slot.putStack(stackEmpty);
		}
		else
		{
			slot.onSlotChanged();
		}

		if (dstItemStack.getCount() == srcItemStack.getCount())
		{
			return stackEmpty;
		}

		slot.onTake(player, dstItemStack);



		return srcItemStack;
	}

	@Override
	public void onContainerClosed(EntityPlayer playerIn)
	{
		this.entityMecha.getInventoryMechaEquipment().closeInventory(playerIn);
		this.entityPlayer.inventory.openInventory(playerIn);

		super.onContainerClosed(playerIn);
	}

}