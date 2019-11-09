package tf2.tile.tileentity;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSeeds;
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
import tf2.recipes.BioGeneratorRecipes;

public class TileEntityBioGenerator extends TileEntity implements ITickable, ISidedInventory {

	public int[] burnTime = new int[3];
	public int[] currentItemBurnTime = new int[3];
	public int cookTime;

	public static final int[] SLOTS = new int[] {0, 1, 2, 3, 4};
	public static final int[] SLOT_FUEL = new int[] {1, 2, 3};
	public static final int SLOT_INPUT = 0;
	public static final int SLOT_OUTPUT = 4;

	private NonNullList<ItemStack> slotItem = NonNullList.<ItemStack>withSize(SLOTS.length, ItemStack.EMPTY);


	//IInventoryの実装
	//---------------------------------------------------------------------------------------
	/*
	 * IInventoryはインベントリ機能を提供するインタフェース.
	 * インベントリに必要なメソッドを適切にオーバーライドする.
	 */

	/*
	 * Inventoryの要素数を返すメソッド.
	 */
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

	/*
	 * スロットの中身を返すメソッド.
	 * 引数はスロット番号
	 */
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

	/*
	 * 1スロットあたりの最大スタック数
	 */
	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	/*
	 * 主にContainerで利用する, GUIを開けるかどうかを判定するメソッド.
	 */
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
		if(index == 4) { // Result
			return false;
		}
		else if(index == 0) { // Ingredient
			return !BioGeneratorRecipes.instance().getSmeltingResult(stack).isEmpty();
		}
		else { // Fuel
			ItemStack itemStack = this.slotItem.get(1);
			return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && (itemStack.isEmpty() || itemStack.getItem() != Items.BUCKET);
		}
	}

	public static boolean isItemFuel(ItemStack stack) {
		return getItemBurnTime(stack) > 0;
	}

	public static int getItemBurnTime(ItemStack stack) {
		if(stack.isEmpty()) {
			return 0;
		}
		else {
			Item item = stack.getItem();
			if(item == Item.getItemFromBlock(Blocks.MELON_BLOCK)) {
				return 180;
			}
			if(item == Item.getItemFromBlock(Blocks.PUMPKIN)) {
				return 80;
			}
			if(item == Item.getItemFromBlock(Blocks.PURPUR_BLOCK)) {
				return 60;
			}
			if(item == Item.getItemFromBlock(Blocks.HAY_BLOCK)) {
				return 360;
			}
			if(item == Item.getItemFromBlock(Blocks.DEADBUSH)) {
				return 20;
			}
			if(item == Item.getItemFromBlock(Blocks.TALLGRASS)) {
				return 20;
			}
			if(Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.LEAVES) {
				return 30;
			}
			if(Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.CACTUS) {
				return 30;
			}
			if(Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.PLANTS) {
				return 30;
			}
			if(item == Item.getItemFromBlock(Blocks.SAPLING)) return 30;
			if(item == Items.APPLE) return 40;
			if(item == Items.CARROT) return 40;
			if(item == Items.BEETROOT) return 40;
			if(item == Items.MELON) return 20;
			if(item == Items.POTATO) return 40;
			if(item == Items.WHEAT) return 40;
			if(item == Items.REEDS) return 40;
			if(item instanceof ItemSeeds) {
				return 20;
			}
		}
		return 0;
	}

	@Override
	public int getField(int id) {
		switch(id) {
		case 0:
			return this.burnTime[0];
		case 1:
			return this.burnTime[1];
		case 2:
			return this.burnTime[2];
		case 3:
			return this.currentItemBurnTime[0];
		case 4:
			return this.currentItemBurnTime[1];
		case 5:
			return this.currentItemBurnTime[2];
		case 6:
			return this.cookTime;

			default:
				return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch(id) {
		case 0:
			this.burnTime[0] = value;
			break;
		case 1:
			this.burnTime[1] = value;
			break;
		case 2:
			this.burnTime[2] = value;
			break;
		case 3:
			this.currentItemBurnTime[0] = value;
			break;
		case 4:
			this.currentItemBurnTime[1] = value;
			break;
		case 5:
			this.currentItemBurnTime[2] = value;
			break;
		case 6:
			this.cookTime = value;
			break;
		}
	}

	@Override
	public int getFieldCount() {
		return 7;
	}

	@Override
	public void clear() {
		this.slotItem.clear();
	}

	@Override
	public String getName() {
		return "gui.biogenerator";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	// 気になる
	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return this.SLOTS;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index == 4;
	}

	@Override
	public void update() {
		boolean flag = this.isBurning();
		boolean needNotify = false;

		for(int index = 0; index < this.SLOT_FUEL.length; index++) {
			if(this.burnTime[index] > 0) {
				this.burnTime[index]--;
			}
		}

		if(this.cookTime > 30000) this.cookTime = 30000;

		if(!this.world.isRemote) {
			int index = 0;
			for(int slot : SLOT_FUEL) {
				ItemStack itemStack = this.slotItem.get(slot);
				if(this.burnTime[index] != 0 || !itemStack.isEmpty()) {
					if(this.burnTime[index] == 0 && this.cookTime < 30000) {
						this.currentItemBurnTime[index] = this.burnTime[index] = getItemBurnTime(itemStack);
						if(this.burnTime[index] > 0) {
							needNotify = true;
							if(!itemStack.isEmpty()) {
								Item item = itemStack.getItem();
								itemStack.shrink(1);
								if(itemStack.isEmpty()) {
									ItemStack container = item.getContainerItem(itemStack);
									this.slotItem.set(slot, container);
								}
							}
						}
					}
					if(this.isBurning() && this.cookTime <= 30000) {
						this.cookTime++;
					}
				}
				index++;
			}
			if(this.cookTime >= 300 && this.canProcess()) {
				this.cookTime -= 300;
				this.processItem();
				needNotify = true;
			}
			for(index = 0; index < SLOT_FUEL.length; index++) {
				if(flag != this.burnTime[index] > 0) {
					needNotify = true;
				}
			}
			//[add]スロットのアイテム分配機能
			if(this.isBurning()) {
				this.spreadSlotItem();
			}
			if(needNotify) {
				this.markDirty();
			}
		}
	}

	public boolean isBurning() {
		return this.burnTime[0] > 0 || this.burnTime[1] > 0 || this.burnTime[2] > 0;
	}

	private boolean canProcess() {
		if(((ItemStack)this.slotItem.get(0)).isEmpty()) {
			return false;
		}
		else {
			ItemStack result = BioGeneratorRecipes.instance().getSmeltingResult(this.slotItem.get(0));
			if(result.isEmpty()) {
				return false;
			}
			else {
				ItemStack itemStack = this.slotItem.get(4);
				if(itemStack.isEmpty()) return true;
				if(!itemStack.isItemEqual(result)) return false;
				int resultCount = itemStack.getCount() + result.getCount();
				return resultCount <= this.getInventoryStackLimit() && resultCount <= result.getMaxStackSize();
			}
		}
	}

	private void processItem() {
		if(this.canProcess()) {
			ItemStack ingredient = this.slotItem.get(0);
			ItemStack result = BioGeneratorRecipes.instance().getSmeltingResult(ingredient);
			ItemStack itemStack = this.slotItem.get(4);
			if(itemStack.isEmpty()) {
				this.slotItem.set(4, result.copy());
			}
			else if(result.isItemEqual(itemStack)) {
				itemStack.grow(result.getCount());
			}
			ingredient.shrink(1);
		}
	}

	private void spreadSlotItem() {
		for(int slot : SLOT_FUEL) {
			int sum = 0;
			int num = 0;
			ItemStack target = this.slotItem.get(slot).copy();
			if(!target.isEmpty()) {
				ItemStack itemStack;
				for(int index : SLOT_FUEL) {
					itemStack = this.slotItem.get(index);
					if(itemStack.isEmpty() || target.isItemEqual(itemStack)) {
						sum += itemStack.getCount();
						num++;
					}
				}
				int share = sum / num;
				int excess = sum % num;

				int i = 0;
				for(int index : SLOT_FUEL) {
					itemStack = this.slotItem.get(index);
					if(itemStack.isEmpty()) {
						itemStack = target.copy();
						itemStack.setCount(share + (i < excess ? 1 : 0));
						this.setInventorySlotContents(index, itemStack);
					}
					else {
						if(itemStack.isItemEqual(target)) {
							itemStack.setCount(share + (i < excess ? 1 : 0));
						}
					}
					i++;
				}
			}
		}
	}

	//NBT
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.slotItem = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.slotItem);
		int index = 0;
		for(int burnTime : compound.getIntArray("BurnTime")) {
			this.burnTime[index++] = burnTime;
		}
		index = 0;
		for(int currentItemBurnTime : compound.getIntArray("CurrentItemBurnTime")) {
			this.currentItemBurnTime[index++] = currentItemBurnTime;
		}
		this.cookTime = compound.getInteger("CookTime");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setIntArray("BurnTime", this.burnTime);
		compound.setIntArray("CurrentItemBurnTime", this.currentItemBurnTime);
		compound.setInteger("CookTime", this.cookTime);
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
	public int getProcessProgressScaled(int pixels) {
		return this.cookTime * pixels / 30000;
	}

	//[GUI]燃料残量描画
	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int index, int pixels) {
		if(this.currentItemBurnTime[index] == 0) {
			this.currentItemBurnTime[index] = 200;
		}
		return this.burnTime[index] * pixels / this.currentItemBurnTime[index];
	}
}
