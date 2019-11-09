package tf2.tile.container;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TFItems;
import tf2.tile.tileentity.TileEntityMechaDock;

public class ContainerMechaDock  extends Container
{
	public final TileEntityMechaDock tile;
	public final InventoryPlayer playerInv;

	private int lastCookTime;

	public ContainerMechaDock(EntityPlayer player, TileEntityMechaDock tileEntity)
	{
		this.tile = tileEntity;
		this.playerInv = player.inventory;
		tileEntity.openInventory(player);

		this.addSlotToContainer(new Slot(tileEntity, 0, 132, 63)
		{
			@Override
			public boolean isItemValid(ItemStack stack)
			{
				return stack.getItem() == TFItems.SPAWNFM;
			}

			@Override
		    public boolean canTakeStack(EntityPlayer playerIn)
		    {
				if(!tile.getStackInSlot(0).isEmpty())
				{
					ItemStack stack = tile.getStackInSlot(0);

                	NBTTagCompound nbt = ((ItemStack) stack).getTagCompound();
                	if(nbt != null)
                	{
                		return nbt.getInteger("tf.mechaHealth") >= nbt.getInteger("tf.mechaMaxHealth");
                	}
				}
				return true;
		    }
		});


		this.addSlotToContainer(new Slot(tileEntity, 1, 152, 63));

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
		}
		this.lastCookTime = this.tile.getField(0);
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
			if (par2 == 1)
			{
				// アイテムの移動(スロット3～39へ)
				if (!this.mergeItemStack(itemstack1, 2, 38, true))
				{
					return ItemStack.EMPTY;
				}

				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (par2 != 0)
			{

				if ((itemstack1.getItem() == TFItems.SPAWNFM) && this.inventorySlots.get(0).getStack().isEmpty())
				{
					// アイテムの移動(スロット0～1へ)
					if (!this.mergeItemStack(itemstack1, 0, 1, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (this.tile.burnItemTime(itemstack1) > 0 && this.inventorySlots.get(1).getStack().isEmpty())
				{
					// アイテムの移動(スロット0～1へ)
					if (!this.mergeItemStack(itemstack1, 1, 2, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (par2 >= 2 && par2 < 29)
				{
					// アイテムの移動(スロット32～41へ)
					if (!this.mergeItemStack(itemstack1, 29, 38, false))
					{
						return ItemStack.EMPTY;
					}
				}
				else if (par2 >= 29 && par2 < 38 && !this.mergeItemStack(itemstack1, 2, 29, false))
				{
					return ItemStack.EMPTY;
				}
			}

			else if (!this.mergeItemStack(itemstack1, 2, 38, false))
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
