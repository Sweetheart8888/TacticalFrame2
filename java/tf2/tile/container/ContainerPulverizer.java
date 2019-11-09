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
import tf2.recipes.PulverizerRecipes;
import tf2.tile.tileentity.TileEntityPulverizer;

public class ContainerPulverizer extends Container
{
	public final TileEntityPulverizer tile;
	public final InventoryPlayer playerInv;

	private int lastCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;

	public ContainerPulverizer(EntityPlayer player, TileEntityPulverizer tileEntity)
	{
		this.tile = tileEntity;
		this.playerInv = player.inventory;
		tileEntity.openInventory(player);

		this.addSlotToContainer(new Slot(tileEntity, 0, 47, 17));
		this.addSlotToContainer(new Slot(tileEntity, 1, 47, 53));

		this.addSlotToContainer(new SlotFurnaceOutput(player, tileEntity, 2, 116, 35));

		for (int i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
			}
		}

		for (int l = 0; l < 9; ++l)
		{
			this.addSlotToContainer(new Slot(playerInv, l, 8 + l * 18, 142));
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

			// スロット番号が2の時
			if (par2 == 2)
			{
				// アイテムの移動(スロット3～39へ)
				if (!this.mergeItemStack(itemstack1, 3, 39, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (par2 != 1 && par2 != 0)
			{
				if (!PulverizerRecipes.instance().getSmeltingResult(itemstack1).isEmpty())
				{
					// アイテムの移動(スロット0～1へ)
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (TileEntityPulverizer.isItemFuel(itemstack1))
				{
					// アイテムの移動(スロット1～2へ)
					if (!this.mergeItemStack(itemstack1, 1, 2, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (par2 >= 3 && par2 < 30)
				{
					// アイテムの移動(スロット32～41へ)
					if (!this.mergeItemStack(itemstack1, 30, 39, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (par2 >= 30 && par2 < 39 && !this.mergeItemStack(itemstack1, 3, 30, false))
				{
					return ItemStack.EMPTY;
				}
			}

			else if (!this.mergeItemStack(itemstack1, 3, 39, false))
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
