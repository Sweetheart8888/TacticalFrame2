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
import tf2.tile.tileentity.TileEntityCupola;

public class ContainerCupola extends Container
{
	public final TileEntityCupola tile;
	public final InventoryPlayer playerInv;

	private int lastCookTime;
	private int lastBurnTime;
	private int lastBurnStack1;
	private int lastBurnStack2;
	private int lastItemBurnTime;

	public ContainerCupola(EntityPlayer player, TileEntityCupola tileEntity)
	{
		this.tile = tileEntity;
		this.playerInv = player.inventory;
		tileEntity.openInventory(player);

		this.addSlotToContainer(new Slot(tileEntity, 0, 58, 56));
		this.addSlotToContainer(new SlotFurnaceOutput(player, tileEntity, 1, 96, 106));

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
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

			if (this.lastBurnTime != this.tile.getField(0))
			{
				icontainerlistener.sendWindowProperty(this, 0, this.tile.getField(0));
			}
			if (this.lastItemBurnTime != this.tile.getField(1))
			{
				icontainerlistener.sendWindowProperty(this, 1, this.tile.getField(1));
			}
			if (this.lastCookTime != this.tile.getField(2))
			{
				icontainerlistener.sendWindowProperty(this, 2, this.tile.getField(2));
			}
			if (this.lastBurnStack1 != this.tile.getField(3))
			{
				icontainerlistener.sendWindowProperty(this, 3, this.tile.getField(3));
			}
			if (this.lastBurnStack2 != this.tile.getField(4))
			{
				icontainerlistener.sendWindowProperty(this, 4, this.tile.getField(4));
			}
        }
        this.lastBurnTime = this.tile.getField(0);
        this.lastItemBurnTime = this.tile.getField(1);
        this.lastCookTime = this.tile.getField(2);
        this.lastBurnStack1 = this.tile.getField(3);
        this.lastBurnStack2 = this.tile.getField(4);
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
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			// スロット番号が2の時
			if (index == 1) {
				// アイテムの移動(スロット3～39へ)
				if (!this.mergeItemStack(itemstack1, 2, 38, true))
				{
					return ItemStack.EMPTY;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}
			// スロット番号が0、1でない時
			else if (index != 0)
			{
				if (TileEntityCupola.isItemFuel(itemstack1))
				{
					// アイテムの移動(スロット1～2へ)
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 2 && index < 29)
				{
					// アイテムの移動(スロット32～41へ)
					if (!this.mergeItemStack(itemstack1, 29, 38, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (index >= 29 && index < 38 && !this.mergeItemStack(itemstack1, 2, 29, false))
				{
					return ItemStack.EMPTY;
				}
			}
			// アイテムの移動(スロット3～39へ)
			else if (!this.mergeItemStack(itemstack1, 2, 38, false))
			{
				return ItemStack.EMPTY;
			}

			if (itemstack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}else
			{
				slot.onSlotChanged();
			}

			if (itemstack1.getCount() == itemstack.getCount())
			{
				return ItemStack.EMPTY;
			}
			slot.onTake(playerIn, itemstack1);
		}
		return itemstack;
	}
}