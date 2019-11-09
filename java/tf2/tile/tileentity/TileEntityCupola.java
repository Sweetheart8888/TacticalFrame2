package tf2.tile.tileentity;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TFBlocks;
import tf2.TFItems;

public class TileEntityCupola extends TileEntity implements ITickable, ISidedInventory
{
	public int burnTime;

	public int currentItemBurnTime;

	//調理時間
	protected int cookTime;

	public int burnStack1;
	public int burnStack2;

	private int itemButton = 0;

	private static final int[] slots = new int[] {0, 1};

	//public ItemStack[] items = new ItemStack[4];
	private NonNullList<ItemStack> furnaceItemStacks = NonNullList.<ItemStack> withSize(2, ItemStack.EMPTY);


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
		this.burnStack1 = compound.getInteger("BurnStack1");
		this.burnStack2 = compound.getInteger("BurnStack2");
		this.cookTime = compound.getInteger("CookTime");
		this.currentItemBurnTime = getItemBurnTime(this.furnaceItemStacks.get(0));
		this.itemButton = compound.getInteger("ItemButton");
	}

	/*
	 * フィールドの保存のためにNBTに変換するメソッド.
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("BurnTime", (short) this.burnTime);
		compound.setInteger("BurnStack1", this.burnStack1);
		compound.setInteger("BurnStack2", this.burnStack2);
		compound.setInteger("CookTime", (short) this.cookTime);
		compound.setInteger("ItemButton", this.itemButton);
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


	public int getItemButton()
    {
        return this.itemButton;
    }

	public void setItemButton(int p_146001_1_)
    {
        this.itemButton = p_146001_1_;
    }

	//矢印の描画
	@SideOnly(Side.CLIENT)
	public int getCookProgressScaled(int par1)
	{
		return this.cookTime * par1 / 100;
	}

	@SideOnly(Side.CLIENT)
	public int getStackProgressScaled(int par1)
	{
		return ((this.burnStack1 + (this.burnStack2 * 20000)) * par1) / 200000;
	}

	//燃焼の描画
	@SideOnly(Side.CLIENT)
	public int getBurnTimeRemainingScaled(int par1)
	{
		if (this.currentItemBurnTime == 0)
		{
			this.currentItemBurnTime = 200;
		}
		return this.burnTime * par1 / this.currentItemBurnTime;
	}

	//かまどの処理
	public boolean isBurning()
	{
		return this.burnTime > 0;
	}

	@Override
	public void update()
    {
        boolean flag = isBurning();
        boolean flag1 = false;

        if (this.isBurning())
        {
            this.burnTime -= 10;
        }
        if (this.burnStack1 >= 20000)
        {
        	this.burnStack1 -= 20000;
        	this.burnStack2 += 1;
        }

        if (!this.world.isRemote)
        {
        	ItemStack itemstack0 = this.furnaceItemStacks.get(0);

            if (this.burnTime != 0 || !itemstack0.isEmpty())
            {
                if (this.burnTime <= 0 && this.burnStack1 + (this.burnStack2 * 20000) < 200000)
                {
                    this.currentItemBurnTime = this.burnTime = getItemBurnTime(itemstack0);

                    if (this.isBurning())
					{
						flag1 = true;

						if (!itemstack0.isEmpty())
						{
							Item item = itemstack0.getItem();
							itemstack0.shrink(1);

							if (itemstack0.isEmpty())
							{
								ItemStack item1 = item.getContainerItem(itemstack0);
								this.furnaceItemStacks.set(0, item1);
							}
						}
					}
                }
                if (this.isBurning() && this.burnStack1 < 20000 && this.burnStack2 < 10)
                {
                	this.burnStack1 += 10;
                }
            }

            if (this.itemButton != 0 && canSmelt())
            {
                ++this.cookTime;

                if (this.cookTime >= 100)
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

	public void startCooking(int i)
	{
		if(i == 1)
		{
			this.itemButton = 1;
		}
		else if(i == 2)
		{
			this.itemButton = 2;
		}
		else if(i == 3)
		{
			this.itemButton = 3;
		}
		else if(i == 4)
		{
			this.itemButton = 4;
		}
		else if(i == 5)
		{
			this.itemButton = 5;
		}
		else if(i == 6)
		{
			this.itemButton = 6;
		}
		else if(i == 7)
		{
			this.itemButton = 7;
		}
		else if(i == 8)
		{
			this.itemButton = 8;
		}
		else if(i == 9)
		{
			this.itemButton = 9;
		}
	}

	private boolean canSmelt()
	{
		ItemStack itemstack = null;
		int i = 0;
		if (this.itemButton == 1)
        {
            itemstack = new ItemStack(Items.IRON_INGOT);
            i = 100;
        }
		else if (this.itemButton == 2)
        {
            itemstack = new ItemStack(Blocks.IRON_BLOCK);
            i = 900;
        }
		else if (this.itemButton == 3)
        {
            itemstack = new ItemStack(TFBlocks.IRON_FRAME);
            i = 30;
        }
		else if (this.itemButton == 4)
        {
            itemstack = new ItemStack(TFItems.IRON_SHIELD);
            i = 600;
        }
		else if (this.itemButton == 5)
        {
            itemstack = new ItemStack(TFItems.GEAR_IRON);
            i = 400;
        }
		else if (this.itemButton == 6)
        {
            itemstack = new ItemStack(TFBlocks.MACHINE_CHASSIS);
            i = 500;
        }
		else if (this.itemButton == 7)
        {
            itemstack = new ItemStack(TFItems.COMPRESS_IRON);
            i = 6400;
        }
		else if (this.itemButton == 8)
        {
            itemstack = new ItemStack(TFItems.CAN);
            i = 10;
        }
		else if (this.itemButton == 9)
        {
            itemstack = new ItemStack(Items.LAVA_BUCKET);
            i = 400000;
        }

		if((this.burnStack2 * 20000) + this.burnStack1 < i) return false;
		if (itemstack.isEmpty()) return false;

		ItemStack itemstack1 = this.furnaceItemStacks.get(1);
		if (itemstack1.isEmpty())return true;
		if (!itemstack1.isItemEqual(itemstack))return false;

		int result = itemstack1.getCount() + itemstack.getCount();
		return result <= getInventoryStackLimit() && result <= itemstack1.getMaxStackSize();
	}

	public void smeltItem()
    {
		ItemStack itemstack = null;
		int i = 0;

		if (this.itemButton == 1)
        {
            itemstack = new ItemStack(Items.IRON_INGOT);
            i = 100;
        }
		else if (this.itemButton == 2)
        {
            itemstack = new ItemStack(Blocks.IRON_BLOCK);
            i = 900;
        }
		else if (this.itemButton == 3)
        {
            itemstack = new ItemStack(TFBlocks.IRON_FRAME);
            i = 30;
        }
		else if (this.itemButton == 4)
        {
            itemstack = new ItemStack(TFItems.IRON_SHIELD);
            i = 600;
        }
		else if (this.itemButton == 5)
        {
            itemstack = new ItemStack(TFItems.GEAR_IRON);
            i = 400;
        }
		else if (this.itemButton == 6)
        {
            itemstack = new ItemStack(TFBlocks.MACHINE_CHASSIS);
            i = 500;
        }
		else if (this.itemButton == 7)
        {
            itemstack = new ItemStack(TFItems.COMPRESS_IRON);
            i = 6400;
        }
		else if (this.itemButton == 8)
        {
            itemstack = new ItemStack(TFItems.CAN);
            i = 10;
        }
		else if (this.itemButton == 9)
        {
            itemstack = new ItemStack(Items.LAVA_BUCKET);
            i = 400000;
        }

		if(this.burnStack1 >= i)
  		{
  			 this.burnStack1 = this.burnStack1 - i;
  		}
  		else if(this.burnStack1 < i)
  		{
  			this.burnStack2 -= 1;
  			this.burnStack1 = 20000 - (i - this.burnStack1);
  		}


		ItemStack itemstack1 = this.furnaceItemStacks.get(1);

		if (itemstack1.isEmpty())
		{
			this.furnaceItemStacks.set(1, itemstack.copy());
		}
		else if (itemstack1.getItem() == itemstack.getItem())
		{
			itemstack1.grow(itemstack.getCount());
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
			Item item = stack.getItem();

			if (item == Item.getItemFromBlock(Blocks.IRON_ORE))
            {
                return 200;
            }
			if (item == Item.getItemFromBlock(TFBlocks.ORE_MAGNETITE))
            {
                return 200;
            }
            if (item == Item.getItemFromBlock(TFBlocks.IRON_FRAME))
            {
                return 30;
            }
            if (item == Item.getItemFromBlock(TFBlocks.MACHINE_CHASSIS))
            {
                return 500;
            }
            if (item == Item.getItemFromBlock(Blocks.IRON_BLOCK))
            {
                return 900;
            }
            if (item == Item.getItemFromBlock(Blocks.ANVIL))
            {
                return 3000;
            }
            if (item == Item.getItemFromBlock(Blocks.IRON_BARS))
            {
                return 30;
            }
            if (item == Item.getItemFromBlock(Blocks.HEAVY_WEIGHTED_PRESSURE_PLATE))
            {
                return 200;
            }
            if (item == Item.getItemFromBlock(Blocks.PISTON))
            {
                return 100;
            }
            if (item == Item.getItemFromBlock(Blocks.STICKY_PISTON))
            {
                return 100;
            }

            if (item == Item.getItemFromBlock(Blocks.RAIL)) return 30;
            if (item == Item.getItemFromBlock(Blocks.DETECTOR_RAIL)) return 100;
            if (item == Item.getItemFromBlock(Blocks.ACTIVATOR_RAIL)) return 100;
            if (item == Item.getItemFromBlock(Blocks.HOPPER)) return 500;

            if (item == Items.IRON_AXE) return 250;
            if (item == Items.IRON_PICKAXE) return 250;
            if (item == Items.IRON_HOE) return 150;
            if (item == Items.IRON_HORSE_ARMOR) return 400;
            if (item == Items.IRON_SHOVEL) return 50;
            if (item == Items.IRON_SWORD) return 100;
            if (item == Items.IRON_HELMET) return 450;
            if (item == Items.IRON_CHESTPLATE) return 750;
            if (item == Items.IRON_LEGGINGS) return 650;
            if (item == Items.IRON_BOOTS) return 350;
            if (item == Items.FLINT_AND_STEEL) return 50;
            if (item == Items.SHEARS) return 100;

            if (item == Items.COMPASS) return 400;
            if (item == Items.IRON_DOOR) return 600;
            if (item == Items.CAULDRON) return 700;
            if (item == Items.IRON_INGOT) return 100;
            if (item == Items.BUCKET) return 300;
            if (item == Items.MINECART) return 500;
            if (item == Items.CHEST_MINECART) return 500;
            if (item == Items.COMMAND_BLOCK_MINECART) return 500;
            if (item == Items.FURNACE_MINECART) return 500;
            if (item == Items.TNT_MINECART) return 500;
            if (item == Items.HOPPER_MINECART) return 1000;

            if (item == Items.IRON_INGOT) return 100;
            if (item == TFItems.CAN) return 10;
            if (item == TFItems.GEAR_IRON) return 400;
            if (item == TFItems.CAN_WATER) return 20;
            if (item == TFItems.CAN_LAVA) return 20;
            if (item == TFItems.CAN_MILK) return 20;
            if (item == TFItems.POWDER_IRON) return 100;
            if (item == TFItems.COMPRESS_IRON) return 6400;
            if (item == TFItems.SCRAP) return 40;
            if (item == TFItems.IRON_SHIELD) return 600;
            if (item == TFItems.REINFORCED_IRON_PICKAXE) return 500;
            if (item == TFItems.REINFORCED_IRON_AXE) return 500;
            if (item == TFItems.REINFORCED_IRON_SHOVEL) return 100;
            if (item == TFItems.REINFORCED_IRON_SWORD) return 100;
            if (item == TFItems.REINFORCED_IRON_HELMET) return 800;
            if (item == TFItems.REINFORCED_IRON_BODY) return 1400;
            if (item == TFItems.REINFORCED_IRON_LEG) return 1200;
            if (item == TFItems.REINFORCED_IRON_BOOT) return 600;
			return 0;
		}
	}

  //かまどの処理
  	public static boolean isItemFuel(ItemStack par0ItemStack)
  	{
  		return getItemBurnTime(par0ItemStack) > 0;
  	}

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
		if (index == 1)
		{
			return false;
		}
		else
		{
			ItemStack itemstack = this.furnaceItemStacks.get(0);
			return isItemFuel(stack);
		}
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side)
    {
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
        return index == 1;
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
                return this.burnTime;
            case 1:
                return this.currentItemBurnTime;
            case 2:
                return this.cookTime;
            case 3:
                return this.burnStack1;
            case 4:
                return this.burnStack2;
            case 5:
                return this.itemButton;
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
                this.burnTime = value;
                break;
            case 1:
                this.currentItemBurnTime = value;
                break;
            case 2:
                this.cookTime = value;
                break;
            case 3:
                this.burnStack1 = value;
                break;
            case 4:
                this.burnStack2 = value;
                break;
            case 5:
                this.itemButton = value;
                break;
        }
    }

	@Override
	public int getFieldCount()
    {
        return 6;
    }

	@Override
	public void clear()
	{
		this.furnaceItemStacks.clear();
	}

	@Override
	public String getName()
	{
		return "gui.cupola";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}
}
