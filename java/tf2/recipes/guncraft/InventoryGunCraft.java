package tf2.recipes.guncraft;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class InventoryGunCraft implements IInventory {

	private final NonNullList<ItemStack> stackResult;

	public InventoryGunCraft(int i) {

		stackResult = NonNullList.<ItemStack>withSize(i, ItemStack.EMPTY);
	}

	@Override
	public String getName() {

		return "multicraft:Result";
	}

	@Override
	public boolean hasCustomName() {

		return false;
	}

	@Override
	public ITextComponent getDisplayName() {

		return (ITextComponent)(this.hasCustomName() ? new TextComponentString(this.getName()) : new TextComponentTranslation(this.getName(), new Object[0]));
	}

	@Override
	public int getSizeInventory() {

		return this.stackResult.size();
	}

	@Override
	public boolean isEmpty() {

		for(ItemStack itemstack : this.stackResult) {

			if(!itemstack.isEmpty()) {

				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {

		return this.stackResult.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {

		return ItemStackHelper.getAndRemove(this.stackResult, index);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {

		return ItemStackHelper.getAndRemove(this.stackResult, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {

		this.stackResult.set(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public void markDirty() {}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {

		return true;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {

		return true;
	}

	@Override
	public int getField(int id) {

		return 0;
	}

	@Override
	public void setField(int id, int value) {}

	@Override
	public int getFieldCount() {

		return 0;
	}

	@Override
	public void clear() {

		this.stackResult.clear();
	}
}