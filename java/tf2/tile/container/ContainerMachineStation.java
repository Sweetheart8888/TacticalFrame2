package tf2.tile.container;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketSetSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tf2.TFBlocks;
import tf2.recipes.guncraft.CraftingManagerMachineStation;
import tf2.recipes.guncraft.InventoryGunCraft;
import tf2.recipes.guncraft.SlotMachineStation;

public class ContainerMachineStation extends Container
{
	public InventoryCrafting craftMatrix;
	public IInventory craftResult;
	private final World world;
	private final BlockPos pos;
	private final EntityPlayer player;

    public ContainerMachineStation(InventoryPlayer playerInventory, World worldIn, BlockPos posIn)
    {
    	this.craftMatrix = new InventoryCrafting(this, 3, 3);
		this.craftResult = new InventoryGunCraft(9);
		this.world = worldIn;
		this.pos = posIn;
		this.player = playerInventory.player;

		 for (int i = 0; i < 3; i++)
			{
				for (int j = 0; j < 3; j++)
				{
					 this.addSlotToContainer(new SlotMachineStation(this.player, craftMatrix, craftResult, j + i * 3, 89 + j * 23, 20 + i * 37));
				}
			}

        for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				this.addSlotToContainer(new Slot(craftMatrix, j + i * 3, 16 + j * 18, 39 + i * 18));
			}
		}

		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 9; j++)
			{
				this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 128 + i * 18));
			}
		}
		for (int i = 0; i < 9; i++)
		{
			this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 186));
		}
    }

    @Override
	public void onCraftMatrixChanged(IInventory inventoryIn) { //変更点

		if(!this.world.isRemote) {

			EntityPlayerMP entityPlayerMP = (EntityPlayerMP)this.player;
			List<ItemStack> recipes = CraftingManagerMachineStation.findMatchingResult(this.craftMatrix, this.world);
			if(recipes == null) return;
			int index = 0;
			Iterator iterator = recipes.iterator();
			while(index < craftResult.getSizeInventory()) {

				ItemStack result = iterator.hasNext() ? (ItemStack)iterator.next() : ItemStack.EMPTY;
				this.craftResult.setInventorySlotContents(index, result);
				entityPlayerMP.connection.sendPacket(new SPacketSetSlot(this.windowId, index, result));
				index++;
			}
		}
	}
    /**
     * Called when the container is closed.
     */
    public void onContainerClosed(EntityPlayer playerIn)
    {
        super.onContainerClosed(playerIn);
        if (!this.world.isRemote)
        {
            this.clearContainer(playerIn, this.world, this.craftMatrix);
        }
    }

    /**
     * Determines whether supplied player can use this container
     */
    public boolean canInteractWith(EntityPlayer playerIn)
    {
        if (this.world.getBlockState(this.pos).getBlock() != TFBlocks.MACHINESTATION)
        {
            return false;
        }
        else
        {
            return playerIn.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
        }
    }

    /**
     * Handle when the stack in slot {@code index} is shift-clicked. Normally this moves the stack between the player
     * inventory and the other inventory(s).
     */
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);

        if (slot != null && slot.getHasStack())
        {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (index >= 0 && index < 9)
            {
                itemstack1.getItem().onCreated(itemstack1, this.world, playerIn);

                if (!this.mergeItemStack(itemstack1, 18, 54, true))
                {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (index >= 18 && index < 45)
            {
                if (!this.mergeItemStack(itemstack1, 45, 54, false))
                {
                    return ItemStack.EMPTY;
                }
            }
            else if (index >= 45 && index < 54)
            {
                if (!this.mergeItemStack(itemstack1, 18, 45, false))
                {
                    return ItemStack.EMPTY;
                }
            }
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

            ItemStack itemstack2 = slot.onTake(playerIn, itemstack1);

            if (index == 0)
            {
                playerIn.dropItem(itemstack2, false);
            }
        }

        return itemstack;
    }
}