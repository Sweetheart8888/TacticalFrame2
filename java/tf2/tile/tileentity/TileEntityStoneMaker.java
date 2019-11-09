package tf2.tile.tileentity;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.recipes.StoneMakerRecipes;
import tf2.tile.VanillaItemBurnTime;

public class TileEntityStoneMaker extends TileEntity implements ITickable, ISidedInventory {
	public int burnTime;

	public int currentItemBurnTime;

	protected int cookTime;

	private static final int[] slots_bottom = new int[] {28, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27 };

	private NonNullList<ItemStack> furnaceItemStacks = NonNullList.<ItemStack> withSize(29, ItemStack.EMPTY);

	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.furnaceItemStacks = NonNullList.<ItemStack> withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);
		this.burnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks.get(28));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short) this.burnTime);
		compound.setInteger("CookTime", (short) this.cookTime);
		ItemStackHelper.saveAllItems(compound, this.furnaceItemStacks);
		return compound;
	}

	@Override
	@Nullable
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound nbtTagCompound = new NBTTagCompound();
		this.writeToNBT(nbtTagCompound);
		return new SPacketUpdateTileEntity(pos, -50, nbtTagCompound);
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1)
	{
		return this.cookTime * par1 / 20;
	}

	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1)
	{
		if (this.currentItemBurnTime == 0)
		{
			this.currentItemBurnTime = 20;
		}
		return this.burnTime * par1 / this.currentItemBurnTime;
	}

	public boolean isBurning()
	{
		return this.burnTime > 0;
	}

	@Override
	public void update()
	{
		boolean flag =  this.isBurning();
		boolean flag1 = false;

		if (this.isBurning())
		{
			--this.burnTime;
		}

		if (!this.world.isRemote)
		{
			ItemStack itemstack28 = this.furnaceItemStacks.get(28);
			if (this.burnTime != 0 || !itemstack28.isEmpty())
			{
				if (!this.isBurning() && this.canSmelt())
				{
					this.currentItemBurnTime = this.burnTime = getItemBurnTime(itemstack28);

					if (this.isBurning())
					{
						flag1 = true;
						if (!itemstack28.isEmpty())
						{
							Item item = itemstack28.getItem();
							itemstack28.shrink(1);

							if (itemstack28.isEmpty())
							{
								ItemStack item1 = item.getContainerItem(itemstack28);
								this.furnaceItemStacks.set(28, item1);
							}
						}
					}
				}

				if (this.isBurning() && this.canSmelt())
				{
					++this.cookTime;

					if (this.cookTime == 20)
					{
						this.cookTime = 0;
						this.smeltItem();
						flag1 = true;
					}
				}
				else
				{
					this.cookTime = 0;
				}
			}
			else if (!this.isBurning() && this.cookTime > 0)
			{
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, 20);
			}
			if (flag != this.isBurning())
			{
				flag1 = true;
			}
		}

		if (flag1)
		{
			this.markDirty();
		}
	}

	private boolean canSmelt()
	{
		if (((ItemStack) this.furnaceItemStacks.get(0)).isEmpty())
		{
			ItemStack itemstack = new ItemStack(Blocks.COBBLESTONE);
			for (int i = 1; i < 28; i++)
			{
				ItemStack itemstack1 = this.furnaceItemStacks.get(i);
				if (itemstack1.isEmpty()) return true;
				if (!itemstack1.isEmpty() && itemstack1.isItemEqual(itemstack) && itemstack1.getCount() < itemstack.getMaxStackSize())
				{
					int result = itemstack1.getCount() + itemstack.getCount();
					return (result <= this.getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
				}
			}
		}
		else
		{
			ItemStack itemstack = StoneMakerRecipes.instance().getSmeltingResult(this.furnaceItemStacks.get(0));
			if (itemstack.isEmpty())return false;

			for (int i = 1; i < 28; i++)
			{
				ItemStack itemstack1 = this.furnaceItemStacks.get(i);
				if (itemstack1.isEmpty()) return true;

				if (!itemstack1.isEmpty() && itemstack1.isItemEqual(itemstack) && itemstack1.getCount() < itemstack.getMaxStackSize())
				{
					int result = itemstack1.getCount() + itemstack.getCount();
					return (result <= this.getInventoryStackLimit() && result <= itemstack.getMaxStackSize());
				}
			}
		}
		return false;
	}

	public void smeltItem()
	{
		if (this.canSmelt())
		{
			ItemStack itemstack0 = this.furnaceItemStacks.get(0);
			if (itemstack0.isEmpty())
			{
				ItemStack itemstack = new ItemStack(Blocks.COBBLESTONE);
				for (int i = 1; i < 28; i++)
				{
					ItemStack itemstack2 = this.furnaceItemStacks.get(i);
					if (itemstack2.isEmpty())
					{
						this.furnaceItemStacks.set(i, itemstack.copy());
						break;
					}
					else if (itemstack2.getItem() == itemstack.getItem() && itemstack2.getCount() < itemstack.getMaxStackSize())
					{
						itemstack2.grow(itemstack.getCount());
						break;
					}
				}
			}
			else
			{
				ItemStack itemstack = StoneMakerRecipes.instance().getSmeltingResult(itemstack0);
				for (int i = 1; i < 28; i++)
				{
					ItemStack itemstack2 = this.furnaceItemStacks.get(i);
					if (itemstack2.isEmpty())
					{
						this.furnaceItemStacks.set(i, itemstack.copy());
						break;
					}
					else if (itemstack2.getItem() == itemstack.getItem() && itemstack2.getCount() < itemstack.getMaxStackSize())
					{
						itemstack2.grow(itemstack.getCount());
						break;
					}
				}
			}
		}
	}

	public static int getItemBurnTime(ItemStack stack)
    {
		if (stack.isEmpty())
		{
			return 0;
		}
        else
        {
        	int burnTime = net.minecraftforge.event.ForgeEventFactory.getItemBurnTime(stack);
			if (burnTime > 0)
				return burnTime;

			int vanillaBurnTime = VanillaItemBurnTime.getVanillaItemBurnTime(stack);
			return vanillaBurnTime;
        }
    }

	public static boolean isItemFuel(ItemStack par0ItemStack)
	{
		return getItemBurnTime(par0ItemStack) > 0;
	}

	@Override
	public int getSizeInventory()
	{
		return this.furnaceItemStacks.size();
	}

	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.furnaceItemStacks.get(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count)
	{
		return ItemStackHelper.getAndSplit(this.furnaceItemStacks, index, count);
	}
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStackHelper.getAndRemove(this.furnaceItemStacks, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack)
	{
		ItemStack itemstack = this.furnaceItemStacks.get(index);
		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		this.furnaceItemStacks.set(index, stack);

		if (stack.getCount() > this.getInventoryStackLimit())
		{
			stack.setCount(this.getInventoryStackLimit());
		}
	}
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
	{
		return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double) this.pos.getX() + 0.5D, (double) this.pos.getY() + 0.5D, (double) this.pos.getZ() + 0.5D) <= 64.0D;
	}
	@Override
	public boolean isEmpty()
	{
		for (ItemStack itemstack : this.furnaceItemStacks)
		{
			if (!itemstack.isEmpty())
			{
				return false;
			}
		}
		return true;
	}
	/*
	 * trueではHopperでアイテムを送れるようになる.
	 */

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack)
	{
		if (index < 28)
		{
			return false;
		}
		else
		{
			return isItemFuel(stack);
		}
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
	{
		return slots_bottom;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
	{
		return this.isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
	{
		return index != 28;
	}

	@Override
	public void openInventory(EntityPlayer player) {}

	@Override
	public void closeInventory(EntityPlayer player) {}

	@Override
	public int getField(int id)
	{
		switch (id)
		{
		case 0:
			return this.cookTime;
		case 1:
			return this.burnTime;
		case 2:
			return this.currentItemBurnTime;
		default:
			return 0;
		}
	}

	@Override
	public void setField(int id, int value)
	{
		switch (id)
		{
		case 0:
			this.cookTime = value;
			break;
		case 1:
			this.burnTime = value;
			break;
		case 2:
			this.currentItemBurnTime = value;
			break;
		}
	}

	@Override
	public int getFieldCount()
	{
		return 3;
	}

	@Override
	public void clear()
	{
		this.furnaceItemStacks.clear();
	}

	@Override
	public String getName()
	{
		return "gui.stonemaker";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

}
