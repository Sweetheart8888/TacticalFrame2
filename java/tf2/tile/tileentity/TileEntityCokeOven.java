package tf2.tile.tileentity;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TFItems;
import tf2.recipes.CokeOvenRecipes;

public class TileEntityCokeOven extends TileEntity implements ITickable, ISidedInventory {

	public int[] cookTime = new int[8];
	public int tarStack;
	public int oilStack;

	public static final int[] SLOTS = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17};
	public static final int[] SLOT_INPUT = new int[] {0, 1, 2, 3, 4, 5, 6, 7};
	public static final int[] SLOT_OUTPUT = new int[] {8, 9, 10, 11, 12, 13};
	public static final int SLOT_TAR = 16;
	public static final int SLOT_OIL = 17;

	private NonNullList<ItemStack> slotItem = NonNullList.<ItemStack>withSize(SLOTS.length, ItemStack.EMPTY);

	@Override
	public int getSizeInventory() {

		return this.slotItem.size();
	}

	@Override
	public boolean isEmpty() {
		for(ItemStack itemStack : this.slotItem) {
			if(!itemStack.isEmpty()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return this.slotItem.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(this.slotItem, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(this.slotItem, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemStack = this.slotItem.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemStack) && ItemStack.areItemsEqual(stack, itemStack);
		this.slotItem.set(index, stack);
		if(stack.getCount() > this.getInventoryStackLimit()) {
			stack.setCount(this.getInventoryStackLimit());
		}
		this.markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if(index >= 8) {
			return false;
		}
		else {
			return !CokeOvenRecipes.instance().getSmeltingResult(stack).isEmpty();
		}
	}

	@Override
	public int getField(int id) {
		switch(id) {
		case 0:
			return this.cookTime[0];
		case 1:
			return this.cookTime[1];
		case 2:
			return this.cookTime[2];
		case 3:
			return this.cookTime[3];
		case 4:
			return this.cookTime[4];
		case 5:
			return this.cookTime[5];
		case 6:
			return this.cookTime[6];
		case 7:
			return this.cookTime[7];
		case 8:
			return this.tarStack;
		case 9:
			return this.oilStack;

			default:
				return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch(id) {
		case 0:
			this.cookTime[0] = value;
			break;
		case 1:
			this.cookTime[1] = value;
			break;
		case 2:
			this.cookTime[2] = value;
			break;
		case 3:
			this.cookTime[3] = value;
			break;
		case 4:
			this.cookTime[4] = value;
			break;
		case 5:
			this.cookTime[5] = value;
			break;
		case 6:
			this.cookTime[6] = value;
			break;
		case 7:
			this.cookTime[7] = value;
			break;
		case 8:
			this.tarStack = value;
			break;
		case 9:
			this.oilStack = value;
			break;
		}
	}

	@Override
	public int getFieldCount() {
		return 10;
	}

	@Override
	public void clear() {
		this.slotItem.clear();
	}

	@Override
	public String getName() {
		return "gui.cokeoven";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return SLOTS;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index >= 8 && index <= 17;
	}

	@Override
	public void update() {
		boolean needNotify = false;
		if(!this.world.isRemote) {
			int index = SLOT_INPUT[0];
			for(int slot : SLOT_INPUT) {
				if(canProcess(slot)) {
					this.cookTime[index]++;
					if(this.cookTime[index] >= 2000) {
						this.cookTime[index] = 0;
						this.processItem(slot);
						needNotify = true;
					}
					if(this.canGenerateTar()) this.tarStack++;
					if(this.canGenerateOil()) this.oilStack++;
				}
				else {
					this.cookTime[slot] = 0;
				}
				index++;
			}
			if(this.tarStack >= 12000) {
				this.tarStack = 0;
				this.generateTar();
				needNotify = true;
			}
			if(this.oilStack >= 18000) {
				this.oilStack = 0;
				this.generateOil();
				needNotify = true;
			}
			if(needNotify) {
				this.markDirty();
			}
		}
	}

	private boolean canProcess(int slotIn) {
		if(((ItemStack)this.slotItem.get(slotIn)).isEmpty()) {
			return false;
		}
		else {
			ItemStack result = CokeOvenRecipes.instance().getSmeltingResult(this.slotItem.get(slotIn));
			if(result.isEmpty()) {
				return false;
			}
			else {
				for(int slot : SLOT_OUTPUT) {
					ItemStack itemStack = this.slotItem.get(slot);
					if(itemStack.isEmpty()) return true;
					if(!itemStack.isItemEqual(result)) {
						continue;
					}
					else {
						int resultCount = itemStack.getCount() + result.getCount();
						if(resultCount <= this.getInventoryStackLimit() && resultCount <= result.getMaxStackSize()) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	private void processItem(int slotIn) {
		if(this.canProcess(slotIn)) {
			ItemStack ingredient = this.slotItem.get(slotIn);
			ItemStack result = CokeOvenRecipes.instance().getSmeltingResult(ingredient);
			for(int slot : SLOT_OUTPUT) {
				ItemStack itemStack = this.slotItem.get(slot);
				if(itemStack.isEmpty()) {
					this.slotItem.set(slot, result.copy());
					break;
				}
				if(result.isItemEqual(itemStack) && itemStack.getCount() < itemStack.getMaxStackSize()) {
					itemStack.grow(result.getCount());
					break;
				}
			}
			ingredient.shrink(1);
		}
	}

	private boolean canGenerateTar() {
		ItemStack result = new ItemStack(TFItems.COALTAR);
		ItemStack target = this.slotItem.get(SLOT_TAR);
		if(target.isEmpty()) return true;
		int resultCount = target.getCount() + 1;
		return resultCount <= this.getInventoryStackLimit() && resultCount <= result.getMaxStackSize();
	}

	private void generateTar() {
		if(this.canGenerateTar()) {
			ItemStack result = new ItemStack(TFItems.COALTAR);
			ItemStack target = this.slotItem.get(SLOT_TAR);
			if(target.isEmpty()) {
				this.slotItem.set(SLOT_TAR, result.copy());
			}
			else {
				target.grow(result.getCount());
			}
		}
	}

	private boolean canGenerateOil() {
		ItemStack result = new ItemStack(TFItems.WASTE_OIL);
		ItemStack target = this.slotItem.get(SLOT_OIL);
		if(target.isEmpty()) return true;
		int resultCount = target.getCount() + 1;
		return resultCount <= this.getInventoryStackLimit() && resultCount <= result.getMaxStackSize();
	}

	private void generateOil() {
		if(this.canGenerateTar()) {
			ItemStack result = new ItemStack(TFItems.WASTE_OIL);
			ItemStack target = this.slotItem.get(SLOT_OIL);
			if(target.isEmpty()) {
				this.slotItem.set(SLOT_OIL, result.copy());
			}
			else {
				target.grow(result.getCount());
			}
		}
	}

	//NBT
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.slotItem = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.slotItem);
		int index = SLOT_INPUT[0];
		for(int processTime : compound.getIntArray("ProcessTime")) {
			this.cookTime[index++] = processTime;
		}
		this.tarStack = compound.getInteger("TarStack");
		this.oilStack = compound.getInteger("OilStack");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setIntArray("ProcessTime", this.cookTime);
		compound.setInteger("TarStack", this.tarStack);
		compound.setInteger("OilStack", this.oilStack);
		ItemStackHelper.saveAllItems(compound, this.slotItem);
		return compound;
	}

	//Sync TileEntity
	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new SPacketUpdateTileEntity(this.pos, -50, nbt);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	//[GUI]進捗描画
	@SideOnly(Side.CLIENT)
	public int getProcessProgressScaled(int index, int pixels) {
		return this.cookTime[index] * pixels / 2000;
	}

	@SideOnly(Side.CLIENT)
	public int getTarProgressScaled(int pixels) {
		return this.tarStack * pixels / 12000;
	}

	@SideOnly(Side.CLIENT)
	public int getOilProgressScaled(int pixels) {
		return this.oilStack * pixels / 18000;
	}
}
