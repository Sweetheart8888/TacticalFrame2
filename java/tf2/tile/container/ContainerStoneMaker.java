package tf2.tile.container;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnaceOutput;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.recipes.StoneMakerRecipes;
import tf2.tile.tileentity.TileEntityStoneMaker;

public class ContainerStoneMaker extends Container
{
	public final TileEntityStoneMaker tile;
	public final InventoryPlayer playerInv;

	private int lastCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;

	public ContainerStoneMaker(EntityPlayer player, TileEntityStoneMaker tileEntity)
	{
		this.tile = tileEntity;
		this.playerInv = player.inventory;
		tileEntity.openInventory(player);

		this.addSlotToContainer(new Slot(tileEntity, 0, 78, 13));

		for (int k = 0; k < 3; ++k)
		{
	    	for (int j = 0; j < 9; ++j)
			{
	    		this.addSlotToContainer(new SlotFurnaceOutput(player, tileEntity, 1 + j + k * 9, 8 + j * 18, 72 + k * 18));
			}
		}

		this.addSlotToContainer(new Slot(tileEntity, 28, 32, 46));

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 140 + i * 18));
			}
		}

		for (int l = 0; l < 9; ++l)
		{
			this.addSlotToContainer(new Slot(playerInv, l, 8 + l * 18, 198));
		}
	}

	@Override
	public void addListener(IContainerListener listener)
	{
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.tile);
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int i = 0; i < this.listeners.size(); ++i)
		{
			IContainerListener icontainerlistener = (IContainerListener) this.listeners.get(i);

			if (this.lastCookTime != this.tile.getField(0))
			{
				icontainerlistener.sendWindowProperty(this, 0, this.tile.getField(0));
			}
			if (this.lastBurnTime != this.tile.getField(1))
			{
				icontainerlistener.sendWindowProperty(this, 1, this.tile.getField(1));
			}
			if (this.lastItemBurnTime != this.tile.getField(2))
			{
				icontainerlistener.sendWindowProperty(this, 2, this.tile.getField(2));
			}

		}
		this.lastCookTime = this.tile.getField(0);
		this.lastBurnTime = this.tile.getField(1);
		this.lastItemBurnTime = this.tile.getField(2);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		this.tile.setField(id, data);
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn)
	{
		return this.tile.isUsableByPlayer(playerIn);
	}

	@Override
	@Nullable
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	{
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 >= 1 && par2 < 29)
			{
				if (!this.mergeItemStack(itemstack1, 29, 65, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (par2 != 0)
			{
				if (!StoneMakerRecipes.instance().getSmeltingResult(itemstack1).isEmpty())
				{
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (TileEntityStoneMaker.isItemFuel(itemstack1))
				{
					if (!this.mergeItemStack(itemstack1, 28, 29, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (par2 >= 29 && par2 < 56)
				{
					// アイテムの移動(スロット32～41へ)
					if (!this.mergeItemStack(itemstack1, 56, 65, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (par2 >= 56 && par2 < 65 && !this.mergeItemStack(itemstack1, 29, 56, false))
				{
					return ItemStack.EMPTY;
				}
			}

			else if (!this.mergeItemStack(itemstack1, 29, 65, false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}
			slot.onTake(par1EntityPlayer, itemstack1);
		}
		return itemstack;
	}
}
