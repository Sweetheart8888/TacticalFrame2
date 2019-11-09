package tf2.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import tf2.TFItems;

public class ItemCan extends ItemBase
{
	public ItemCan(String name)
	{
		super(name);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
	{
		IBlockState iblockstate2 = Blocks.AIR.getDefaultState();
		Block block = iblockstate2.getBlock();

		boolean flag = block == Blocks.AIR;
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		RayTraceResult raytraceresult = this.rayTrace(worldIn, playerIn, flag);

		if (raytraceresult == null)
		{
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
		}

		else if (raytraceresult.typeOfHit != RayTraceResult.Type.BLOCK)
		{
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
		}
		else
		{
			BlockPos blockpos = raytraceresult.getBlockPos();

			if (!worldIn.isBlockModifiable(playerIn, blockpos))
			{
				return new ActionResult(EnumActionResult.FAIL, itemstack);
			}
			else if (flag)
			{
				if (!playerIn.canPlayerEdit(blockpos.offset(raytraceresult.sideHit), raytraceresult.sideHit, itemstack))
				{
					return new ActionResult(EnumActionResult.FAIL, itemstack);
				}
				else
				{
					IBlockState iblockstate = worldIn.getBlockState(blockpos);
					Material material = iblockstate.getMaterial();

					if (material == Material.WATER && ((Integer) iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
					{
						worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
						playerIn.addStat(StatList.getObjectUseStats(this));
						playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
						return new ActionResult(EnumActionResult.SUCCESS, this.fillBucket(itemstack, playerIn, TFItems.CAN_WATER));
					}
					else if (material == Material.LAVA && ((Integer) iblockstate.getValue(BlockLiquid.LEVEL)).intValue() == 0)
					{
						worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 11);
						playerIn.addStat(StatList.getObjectUseStats(this));
						playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL_LAVA, 1.0F, 1.0F);
						return new ActionResult(EnumActionResult.SUCCESS, this.fillBucket(itemstack, playerIn, TFItems.CAN_LAVA));
					}
					else
					{
						return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
					}
				}
			}
			else
			{
				return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
			}
		}
	}

	private ItemStack fillBucket(ItemStack emptyBuckets, EntityPlayer player, Item fullBucket)
	{
		if (player.capabilities.isCreativeMode)
		{
			return emptyBuckets;
		}
		else
		{
			emptyBuckets.shrink(1);
			if (emptyBuckets.isEmpty())
			{
				return new ItemStack(fullBucket);
			}
			else
			{
				if (!player.inventory.addItemStackToInventory(new ItemStack(fullBucket)))
				{
					player.dropItem(new ItemStack(fullBucket), false);
				}

				return emptyBuckets;
			}
		}
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand)
	{
		if (target.world.isRemote)
		{
			return false;
		}
		if (target instanceof EntityCow)
		{
			if (!target.isChild())
			{
				playerIn.playSound(SoundEvents.ENTITY_COW_MILK, 1.0F, 1.0F);
				stack.shrink(1);
				if (stack.isEmpty())
				{
					playerIn.setHeldItem(hand, new ItemStack(TFItems.CAN_MILK));
				}
				else if (!playerIn.inventory.addItemStackToInventory(new ItemStack(TFItems.CAN_MILK)))
				{
					playerIn.dropItem(new ItemStack(TFItems.CAN_MILK), false);
				}
			}
			return false;
		}
		return false;
	}
}
