package tf2.tile.tileentity;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
import tf2.TFBlocks;
import tf2.TFItems;
import tf2.entity.mob.frend.EntityFriendMecha;
import tf2.items.ItemSpawnFriendMecha;

public class TileEntityMechaDock extends TileEntity implements ITickable, ISidedInventory
{
	protected int repairTime;

	private static final int[] slots = new int[] {0, 1};

	private NonNullList<ItemStack> furnaceItemStacks = NonNullList.<ItemStack> withSize(3, ItemStack.EMPTY);

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
		this.repairTime = compound.getInteger("RepairTime");
	}

	/*
	 * フィールドの保存のためにNBTに変換するメソッド.
	 */
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound)
	{
		super.writeToNBT(compound);
		compound.setInteger("RepairTime", this.repairTime);
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

	@Override
	public void update()
    {
		if(!this.getStackInSlot(0).isEmpty())
		{
			ItemSpawnFriendMecha item = (ItemSpawnFriendMecha)this.getStackInSlot(0).getItem();

			EntityFriendMecha entity = (EntityFriendMecha) item.getSpawnCreature(this.world, this.getStackInSlot(0));

			int slot = (int)entity.slotSize;

			NBTTagCompound nbt = this.getStackInSlot(0).getTagCompound();

			if (nbt != null)
			{

				if(this.repairTime == 1)
				{
					nbt.setInteger("tf.mechaHealth", nbt.getInteger("tf.mechaMaxHealth"));
				}

				if(this.repairTime == 0)
				{
					if(nbt.getInteger("tf.mechaHealth") != nbt.getInteger("tf.mechaMaxHealth"))
					{
						this.repairTime = (nbt.getInteger("tf.mechaMaxHealth") - nbt.getInteger("tf.mechaHealth")) * (2 * (15 + (slot * 5))) + (60 * nbt.getInteger("tf.mechaLevel"));
					}
				}
				else
				{
					this.repairTime -= 1;
				}
			}
		}

		if(!this.getStackInSlot(1).isEmpty())
		{
			ItemStack itemStack = this.getStackInSlot(1);

			if(this.repairTime > 0)
			{
				if(this.burnItemTime(itemStack) > 0)
				{
					itemStack.shrink(1);
					this.repairTime -= this.burnItemTime(itemStack);
					if(this.repairTime < 0)
					{
						this.repairTime = 1;
					}
				}
			this.markDirty();
			}
		}

        if (!this.world.isRemote)
        {}
    }

	public int burnItemTime(ItemStack stack)
	{
		if(stack.getItem() == new ItemStack(Items.IRON_NUGGET).getItem())
		{
			return 50;
		}
		if(stack.getItem() == new ItemStack(Items.IRON_INGOT).getItem())
		{
			return 450;
		}
		if(stack.getItem() == new ItemStack(Blocks.IRON_BLOCK).getItem())
		{
			return 4050;
		}
		if(stack.getItem() == new ItemStack(TFItems.REINFORCED_IRON_INGOT).getItem())
		{
			return 540;
		}
		if(stack.getItem() == new ItemStack(TFBlocks.REINFORCED_IRON_BLOCK).getItem())
		{
			return 4860;
		}

		return 0;
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

		if (index == 0 && !flag)
		{
			this.repairTime = 0;
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
		if(index == 0 && this.getStackInSlot(0).isEmpty())
		{
			return stack.getItem() == TFItems.SPAWNFM;
		}

		if (index == 1)
		{
			return this.burnItemTime(stack) > 0;
		}
		else
		{
			return false;
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
		NBTTagCompound nbt = stack.getTagCompound();

		if (nbt != null && index == 0)
		{
			return nbt.getInteger("tf.mechaHealth") == nbt.getInteger("tf.mechaMaxHealth");
		}

		return index != 1;
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
                return this.repairTime;
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
                this.repairTime = value;
                break;
        }
    }

	@Override
	public int getFieldCount()
    {
        return 1;
    }

	@Override
	public void clear()
	{
		this.furnaceItemStacks.clear();
	}

	@Override
	public String getName()
	{
		return "gui.mechadock";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

}
