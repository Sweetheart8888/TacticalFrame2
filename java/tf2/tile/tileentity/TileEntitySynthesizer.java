package tf2.tile.tileentity;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
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
import tf2.TFItems;
import tf2.recipes.SynthesizerRecipes;
import tf2.tile.VanillaItemBurnTime;

public class TileEntitySynthesizer extends TileEntity implements ITickable, ISidedInventory
{
	public int burnTime;

	public int currentItemBurnTime;

	protected int cookTime;

	private static final int[] slots = new int[] {0, 1, 2, 3};

	private NonNullList<ItemStack> furnaceItemStacks = NonNullList.<ItemStack> withSize(4, ItemStack.EMPTY);

	// NBTの実装
	//---------------------------------------------------------------------------------------
	/*
	 * NBT(Named By Tag)の読み込み.
	 * TileEntityやEntity, ItemStackのように実行中にインスタンスを生成するようなクラスはフィールドを別途保存しておく必要がある.
	 * そのためにNBTという形式で保存/読み込みをしている.
	 */

	/*
	 * フィールドをNBTから読み込むメソッド.
	 */
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		this.furnaceItemStacks = NonNullList.<ItemStack> withSize(this.getSizeInventory(), ItemStack.EMPTY);
		ItemStackHelper.loadAllItems(compound, this.furnaceItemStacks);
		this.burnTime = compound.getInteger("BurnTime");
		this.cookTime = compound.getInteger("CookTime");
		this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks.get(2));
	}

	/*
	 * フィールドの保存のためにNBTに変換するメソッド.
	 */
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
	public SPacketUpdateTileEntity getUpdatePacket()
	{
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

	//矢印の描画
		@SideOnly(Side.CLIENT)
		public int getCookProgressScaled(int par1)
		{
			return this.cookTime * par1 / 400;
		}
		//燃焼の描画
		@SideOnly(Side.CLIENT)
		public int getBurnTimeRemainingScaled(int par1)
		{
			if (this.currentItemBurnTime == 0)
			{
				this.currentItemBurnTime = 400;
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
		boolean flag = this.isBurning();
        boolean flag1 = false;

        if (this.isBurning())
        {
            --this.burnTime;
        }
        if (!this.world.isRemote)
        {
        	ItemStack itemstack2 = this.furnaceItemStacks.get(2);
			ItemStack itemstack1 = this.furnaceItemStacks.get(1);
			ItemStack itemstack0 = this.furnaceItemStacks.get(0);
            if (this.burnTime != 0 || !itemstack2.isEmpty() && !itemstack1.isEmpty() && !itemstack0.isEmpty() && this.isItemMetal(itemstack0))
            {
                if (!this.isBurning() && this.canSmelt())
                {
                    this.currentItemBurnTime = this.burnTime = getItemBurnTime(itemstack2);

                    if (this.isBurning())
                    {
                        flag1 = true;
                        if (!itemstack2.isEmpty())
						{
							Item item = itemstack2.getItem();
							itemstack2.shrink(1);

							if (itemstack2.isEmpty())
							{
								ItemStack item1 = item.getContainerItem(itemstack2);
								this.furnaceItemStacks.set(2, item1);
							}
						}
                    }
                }
                if (this.isBurning() && this.canSmelt())
                {
                	++this.cookTime;

                    if (this.cookTime >= 400)
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
				this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, 400);
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

	public static boolean isItemMetal(ItemStack itemStack)
	{
		return itemStack.getItem() == TFItems.REIMETAL;
	}

	private boolean canSmelt()
	{
		if (((ItemStack) this.furnaceItemStacks.get(0)).isEmpty())
		{
			return false;
		}
		if (((ItemStack) this.furnaceItemStacks.get(1)).isEmpty())
		{
			return false;
		}
		else
		{
			ItemStack itemstack = SynthesizerRecipes.instance().getSmeltingResult(this.furnaceItemStacks.get(1));
			if (itemstack.isEmpty())return false;
			else
			{
				ItemStack itemstack1 = this.furnaceItemStacks.get(3);
				if (itemstack1.isEmpty()) return true;
				if (!itemstack1.isItemEqual(itemstack)) return false;
				int result = itemstack1.getCount() + itemstack.getCount();
				return result <= getInventoryStackLimit() && result <= itemstack1.getMaxStackSize();
			}
		}
	}
	public void smeltItem()
    {
        if (this.canSmelt())
        {
        	ItemStack itemstack0 = this.furnaceItemStacks.get(0);
        	ItemStack itemstack1 = this.furnaceItemStacks.get(1);
			ItemStack itemstack2 = SynthesizerRecipes.instance().getSmeltingResult(itemstack1);
			ItemStack itemstack3 = this.furnaceItemStacks.get(3);

			if (itemstack3.isEmpty())
			{
				this.furnaceItemStacks.set(3, itemstack2.copy());
			}
			else if (itemstack3.getItem() == itemstack2.getItem())
			{
				itemstack3.grow(itemstack2.getCount());
			}
			itemstack0.shrink(1);
			itemstack1.shrink(1);
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
				return burnTime / 4;

			int vanillaBurnTime = VanillaItemBurnTime.getVanillaItemBurnTime(stack);
			return vanillaBurnTime / 4;
        }
    }
	//かまどの処理
	public static boolean isItemFuel(ItemStack par0ItemStack)
	{
		return getItemBurnTime(par0ItemStack) > 0;
	}

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
	public int getSizeInventory()
	{
		return this.furnaceItemStacks.size();
	}

	/*
	 * スロットの中身を返すメソッド.
	 * 引数はスロット番号
	 */
	@Override
	public ItemStack getStackInSlot(int index)
	{
		return this.furnaceItemStacks.get(index);
	}

	/*
	 * スロットの中身のスタックサイズを変更するメソッド.
	 * (メソッド名はおそらくdecrement stack size)
	 * 引数は(スロット番号, 分割するスタック数)
	 * 戻り値は分割後のItemStack
	 */
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
	/*
	 * InventoryにItemStackを入れるメソッド.
	 * 引数は(スロット番号, そのスロットに入れるItemStack)
	 */
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

		if (index == 1 && !flag)
		{
			this.cookTime = 0;
			this.markDirty();
		}
	}
	/*
	 * 1スロットあたりの最大スタック数
	 */
	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	/*
	 * 主にContainerで利用する, GUIを開けるかどうかを判定するメソッド.
	 */
	@Override
	public boolean isUsableByPlayer(EntityPlayer player)
    {
        return this.world.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
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
		if (index == 3)
		{
			return false;
		}
		else if (index == 1)
		{
			return !SynthesizerRecipes.instance().getSmeltingResult(stack).isEmpty();
		}
		else if (index == 0)
		{
			return stack.getItem() == TFItems.REIMETAL;
		}
		else
		{
			ItemStack itemstack = this.furnaceItemStacks.get(2);
			return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && (itemstack == null || itemstack.getItem() != Items.BUCKET);
		}
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return slots;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction)
    {
        return this.isItemValidForSlot(index, itemStackIn);
    }

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return index == 3 ;
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
		return "gui.synthesizer";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

}
