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
import tf2.recipes.CokeOvenRecipes;
import tf2.tile.tileentity.TileEntityCokeOven;

public class ContainerCokeOven extends Container
{
	public final TileEntityCokeOven tile;
	public final InventoryPlayer playerInv;

	private int lastCookTime1;
	private int lastCookTime2;
	private int lastCookTime3;
	private int lastCookTime4;
	private int lastCookTime5;
	private int lastCookTime6;
	private int lastCookTime7;
	private int lastCookTime8;
	private int lastBurnStack;
	private int lastCoalStack;

	public ContainerCokeOven(EntityPlayer player, TileEntityCokeOven tileEntity)
	{
		this.tile = tileEntity;
		this.playerInv = player.inventory;
		tileEntity.openInventory(player);

		for (int i = 0; i < 2; ++i)
		{
			for (int j = 0; j < 4; ++j)
			{
				this.addSlotToContainer(new Slot(tileEntity, j + i * 4, 17 + j * 18, 21 + i * 22));
			}
		}
		for (int i = 0; i < 4; ++i)
		{
			for (int j = 0; j < 2; ++j)
			{
				this.addSlotToContainer(new SlotFurnaceOutput(player, tileEntity, j + i * 2 + 8, 125 + j * 18, 25 + i * 18));
			}
		}

		this.addSlotToContainer(new SlotFurnaceOutput(player, tileEntity, 16, 71, 79));
		this.addSlotToContainer(new SlotFurnaceOutput(player, tileEntity, 17, 89, 79));

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 113 + i * 18));
			}
		}

		for (int l = 0; l < 9; ++l)
		{
			this.addSlotToContainer(new Slot(playerInv, l, 8 + l * 18, 171));
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

			if (this.lastBurnStack != this.tile.getField(0))
			{
				icontainerlistener.sendWindowProperty(this, 0, this.tile.getField(0));
			}
			if (this.lastCookTime1 != this.tile.getField(1))
			{
				icontainerlistener.sendWindowProperty(this, 1, this.tile.getField(1));
			}

			if (this.lastCookTime2 != this.tile.getField(2))
			{
				icontainerlistener.sendWindowProperty(this, 2, this.tile.getField(2));
			}

			if (this.lastCookTime3 != this.tile.getField(3))
			{
				icontainerlistener.sendWindowProperty(this, 3, this.tile.getField(3));
			}

			if (this.lastCookTime4 != this.tile.getField(4))
			{
				icontainerlistener.sendWindowProperty(this, 4, this.tile.getField(4));
			}
			if (this.lastCookTime5 != this.tile.getField(5))
			{
				icontainerlistener.sendWindowProperty(this, 5, this.tile.getField(5));
			}
			if (this.lastCookTime6 != this.tile.getField(6))
			{
				icontainerlistener.sendWindowProperty(this, 6, this.tile.getField(6));
			}
			if (this.lastCookTime7 != this.tile.getField(7))
			{
				icontainerlistener.sendWindowProperty(this, 7, this.tile.getField(7));
			}
			if (this.lastCookTime8 != this.tile.getField(8))
			{
				icontainerlistener.sendWindowProperty(this, 8, this.tile.getField(8));
			}
			if (this.lastCoalStack != this.tile.getField(9))
			{
				icontainerlistener.sendWindowProperty(this, 9, this.tile.getField(9));
			}
		}

		this.lastBurnStack = this.tile.getField(0);
		this.lastCookTime1 = this.tile.getField(1);
		this.lastCookTime2 = this.tile.getField(2);
		this.lastCookTime3 = this.tile.getField(3);
		this.lastCookTime4 = this.tile.getField(4);
		this.lastCookTime5 = this.tile.getField(5);
		this.lastCookTime6 = this.tile.getField(6);
		this.lastCookTime7 = this.tile.getField(7);
		this.lastCookTime8 = this.tile.getField(8);
		this.lastCoalStack = this.tile.getField(9);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data)
	{
		this.tile.setField(id, data);
	}

	// InventorySample内のisUseableByPlayerメソッドを参照
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

			//スロット番号が2の時
			if (par2 >= 8 && par2 < 18)
			{
				//アイテムの移動(スロット3～39へ)
				if (!this.mergeItemStack(itemstack1, 18, 54, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}

			else if (par2 >= 18)
			{
				if (!CokeOvenRecipes.instance().getSmeltingResult(itemstack1).isEmpty())
				{
					//アイテムの移動(スロット0～1へ)
					if (!this.mergeItemStack(itemstack1, 0, 8, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (par2 >= 18 && par2 < 45)
				{
					//アイテムの移動(スロット30～39へ)
					if (!this.mergeItemStack(itemstack1, 45, 54, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (par2 >= 45 && par2 < 54 && !this.mergeItemStack(itemstack1, 18, 45, false))
				{
					return ItemStack.EMPTY;
				}
			}
			//アイテムの移動(スロット3～39へ)
			else if (!this.mergeItemStack(itemstack1, 18, 54, false))
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
