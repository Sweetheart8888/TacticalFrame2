package tf2.recipes.guncraft;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class SlotMachineStation extends Slot {

	private final InventoryCrafting craftMatrix;
	private final EntityPlayer player;
	private int amountCrafted;

	public SlotMachineStation(EntityPlayer player, InventoryCrafting craftingInventory, IInventory inventoryIn, int index, int xPosition, int yPosition) {

		super(inventoryIn, index, xPosition, yPosition);
		this.player = player;
		this.craftMatrix = craftingInventory;
	}

	public boolean isItemValid(@Nullable ItemStack stack) {

		return false;
	}

	public ItemStack decrStackSize(int amount) {

		if(this.getHasStack()) {

			this.amountCrafted += Math.min(amount, this.getStack().getCount());
		}
		return super.decrStackSize(amount);
	}

	protected void onCrafting(ItemStack stack, int amount) {

		this.amountCrafted += amount;
		this.onCrafting(stack);
	}

	protected void onCrafting(ItemStack stack) {

		if(this.amountCrafted > 0) {

			stack.onCrafting(this.player.world, this.player, this.amountCrafted);

			FMLCommonHandler.instance().firePlayerCraftingEvent(this.player, stack, this.craftMatrix);
		}

		// レシピを使用済みにする処理(いるかは不明)
	}

	public ItemStack onTake(EntityPlayer thePlayer, ItemStack stack) {

		this.onCrafting(stack);
		ForgeHooks.setCraftingPlayer(thePlayer);
		NonNullList<ItemStack> nnList = CraftingManagerMachineStation.getRemainingItems(craftMatrix, thePlayer.world);
		ForgeHooks.setCraftingPlayer(null);
		for(int i = 0; i < nnList.size(); i++) {

			ItemStack itemstack = this.craftMatrix.getStackInSlot(i);
			ItemStack itemstack1 = nnList.get(i);
			if(!itemstack.isEmpty()) {

				this.craftMatrix.decrStackSize(i, 1);
				itemstack = this.craftMatrix.getStackInSlot(i);
			}

			if(!itemstack1.isEmpty()) {
				if(itemstack.isEmpty()) {

					this.craftMatrix.setInventorySlotContents(i, itemstack1);
				}
				else if(ItemStack.areItemsEqual(itemstack, itemstack1) && ItemStack.areItemStackTagsEqual(itemstack, itemstack1)) {

					itemstack1.grow(itemstack.getCount());
					this.craftMatrix.setInventorySlotContents(i, itemstack1);
				}
				else if(!this.player.inventory.addItemStackToInventory(itemstack1)) {

					this.player.dropItem(itemstack1, false);
				}
			}
		}
		return stack;
	}
}
