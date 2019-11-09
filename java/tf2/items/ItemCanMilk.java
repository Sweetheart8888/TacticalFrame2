package tf2.items;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import tf2.TFItems;

public class ItemCanMilk extends ItemBase
{
	static ItemStack milk = new ItemStack(Items.MILK_BUCKET);

	public ItemCanMilk(String name)
	{
		super(name);
	}

	@Override
	@Nullable
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving)
	{
		if (entityLiving instanceof EntityPlayer)
		{
			EntityPlayer entityplayer = (EntityPlayer) entityLiving;

			if (!worldIn.isRemote)
			{
				entityplayer.curePotionEffects(milk);
			}
			else
			{
				stack.shrink(1);
				if (stack.isEmpty())
	            {
	                return new ItemStack(TFItems.CAN);
	            }
				else
				{
					if (!entityplayer.inventory.addItemStackToInventory(new ItemStack(TFItems.CAN)))
					{
						entityplayer.dropItem(new ItemStack(TFItems.CAN), false);
					}
					entityplayer.addStat(StatList.getObjectUseStats(this));
				}
			}
		}
		return stack;
	}


	@Override
	public EnumAction getItemUseAction(ItemStack stack)
	{
		return EnumAction.DRINK;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack)
	{
		return 32;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
	{
		playerIn.setActiveHand(hand);
		ItemStack itemstack = playerIn.getHeldItem(hand);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	}
}
