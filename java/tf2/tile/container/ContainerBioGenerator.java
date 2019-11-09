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
import tf2.recipes.BioGeneratorRecipes;
import tf2.tile.tileentity.TileEntityBioGenerator;

public class ContainerBioGenerator extends Container
{
	public final TileEntityBioGenerator tile;
	public final InventoryPlayer playerInv;

	private int lastCookTime;
	private int lastBurnTime;
	private int lastItemBurnTime;

	private int lastBurnTime2;
	private int lastItemBurnTime2;

	private int lastBurnTime3;
	private int lastItemBurnTime3;

	public ContainerBioGenerator(EntityPlayer player, TileEntityBioGenerator tileEntity)
	{
		this.tile = tileEntity;
		this.playerInv = player.inventory;
		tileEntity.openInventory(player);

		this.addSlotToContainer(new Slot(tileEntity, 0, 129, 15));
		 for (int k = 0; k < 3; ++k)
		 {
			 this.addSlotToContainer(new Slot(tileEntity, k + 1, 26 + k * 18, 55));
		 }

		 this.addSlotToContainer(new SlotFurnaceOutput(player, tileEntity, 4, 129, 51));


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
            IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);

            if (this.lastCookTime != this.tile.getField(0))
            {
                icontainerlistener.sendWindowProperty(this, 0, this.tile.getField(0));
            }
            if (this.lastBurnTime != this.tile.getField(1))
            {
                icontainerlistener.sendWindowProperty(this, 1, this.tile.getField(1));
            }

            if (this.lastBurnTime2 != this.tile.getField(2))
            {
                icontainerlistener.sendWindowProperty(this, 2, this.tile.getField(2));
            }

            if (this.lastBurnTime3 != this.tile.getField(3))
            {
                icontainerlistener.sendWindowProperty(this, 3, this.tile.getField(3));
            }

            if (this.lastItemBurnTime != this.tile.getField(4))
            {
                icontainerlistener.sendWindowProperty(this, 4, this.tile.getField(4));
            }
            if (this.lastItemBurnTime2 != this.tile.getField(5))
            {
                icontainerlistener.sendWindowProperty(this, 5, this.tile.getField(5));
            }
            if (this.lastItemBurnTime3 != this.tile.getField(6))
            {
                icontainerlistener.sendWindowProperty(this, 6, this.tile.getField(6));
            }
        }

        this.lastCookTime = this.tile.getField(0);
        this.lastBurnTime = this.tile.getField(1);
        this.lastBurnTime2 = this.tile.getField(2);
        this.lastBurnTime3 = this.tile.getField(3);
        this.lastItemBurnTime = this.tile.getField(4);
        this.lastItemBurnTime2 = this.tile.getField(5);
        this.lastItemBurnTime3 = this.tile.getField(6);
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
		public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int index)
		{
		ItemStack itemstack = ItemStack.EMPTY;
			Slot slot = (Slot)this.inventorySlots.get(index);

			if (slot != null && slot.getHasStack())
			{
				ItemStack itemstack1 = slot.getStack();
				itemstack = itemstack1.copy();


				if (index == 4)
				{

					if (!this.mergeItemStack(itemstack1, 5, 41, true))
					{
						return ItemStack.EMPTY;
					}

					slot.onSlotChange(itemstack1, itemstack);
				}
				else if (index != 3 && index != 2 && index != 1 && index != 0)
				{
					if (!BioGeneratorRecipes.instance().getSmeltingResult(itemstack1).isEmpty())
					{

						if (!this.mergeItemStack(itemstack1, 0, 1, false))
						{
							return ItemStack.EMPTY;
						}
					}
					else if (TileEntityBioGenerator.isItemFuel(itemstack1))
					{
						//アイテムの移動(スロット1～2へ)
						if (!this.mergeItemStack(itemstack1, 1, 4, false))
						{
							return ItemStack.EMPTY;
						}
					}
					else if (index >= 5 && index < 32)
					{
						//アイテムの移動(スロット32～41へ)
						if (!this.mergeItemStack(itemstack1, 32, 41, false))
						{
							return ItemStack.EMPTY;
						}
					}
					else if (index >= 32 && index < 41 && !this.mergeItemStack(itemstack1, 5, 32, false))
					{
						return ItemStack.EMPTY;
					}
				}

				else if (!this.mergeItemStack(itemstack1, 5, 41, false))
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
