package tf2.event;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tf2.TFItems;
import tf2.items.guns.ItemTFGuns;
import tf2.items.weapon.ItemBaseLevelUp;

public class TFAnvilEvent
{
	//@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onAnvilUpdate(AnvilUpdateEvent event)
	{
		ItemStack left = event.getLeft();
		ItemStack right = event.getRight();
		ItemStack ret = event.getOutput();

		/* Moldのレシピ登録 */
		if (!right.isEmpty())
		{
			if (!left.isEmpty() && left.getItem() instanceof ItemBaseLevelUp && right.getItem() == TFItems.UPGRADE_0 && ret.isEmpty())
			{
				NBTTagCompound tag = new NBTTagCompound();

				NBTTagCompound tag2 = left.getTagCompound();
				if (tag2 != null && tag2.hasKey("tf.level"))
				{
					int level = tag2.getInteger("tf.level");

					if (level < 3)
					{
						ItemStack next = new ItemStack(left.getItem());
						tag.setInteger("tf.level", level + 1);
						next.setTagCompound(tag);

						event.setOutput(next);
						event.setCost(1 + level);
						event.setMaterialCost(1);
					}
				}
				else
				{
					ItemStack next = new ItemStack(left.getItem());
					tag.setInteger("tf.level", 1);
					next.setTagCompound(tag);

					event.setOutput(next);
					event.setCost(1);
					event.setMaterialCost(1);
				}
			}
			if (!left.isEmpty() && left.getItem() instanceof ItemBaseLevelUp && right.getItem() == TFItems.UPGRADE_1 && ret.isEmpty())
			{
				NBTTagCompound tag = new NBTTagCompound();

				NBTTagCompound tag2 = left.getTagCompound();
				if (tag2 != null && tag2.hasKey("tf.level"))
				{
					int level = tag2.getInteger("tf.level");

					if (3 <= level && level < 6)
					{
						ItemStack next = new ItemStack(left.getItem());
						tag.setInteger("tf.level", level + 1);
						next.setTagCompound(tag);

						event.setOutput(next);
						event.setCost(1 + level);
						event.setMaterialCost(1);
					}
				}
			}
			if (!left.isEmpty() && left.getItem() instanceof ItemBaseLevelUp && right.getItem() == TFItems.UPGRADE_2 && ret.isEmpty())
			{
				NBTTagCompound tag = new NBTTagCompound();

				NBTTagCompound tag2 = left.getTagCompound();
				if (tag2 != null && tag2.hasKey("tf.level"))
				{
					int level = tag2.getInteger("tf.level");

					if (6 <= level && level < 10)
					{
						ItemStack next = new ItemStack(left.getItem());
						tag.setInteger("tf.level", level + 1);
						next.setTagCompound(tag);

						event.setOutput(next);
						event.setCost(1 + level );
						event.setMaterialCost(1);
					}
				}
			}
		}

		if (!right.isEmpty())
		{
			if (!left.isEmpty() && left.getItem() instanceof ItemTFGuns && right.getItem() == TFItems.UPGRADE_0 && ret.isEmpty())
			{
				NBTTagCompound tag = new NBTTagCompound();

				NBTTagCompound tag2 = left.getTagCompound();
				if (tag2 != null && tag2.hasKey("tf.level"))
				{
					int level = tag2.getInteger("tf.level");

					if (level < 3)
					{
						ItemStack next = new ItemStack(left.getItem());
						tag.setInteger("tf.level", level + 1);
						next.setTagCompound(tag);

						event.setOutput(next);
						event.setCost(3 + (level * 3));
						event.setMaterialCost(1);
					}
				}
				else
				{
					ItemStack next = new ItemStack(left.getItem());
					tag.setInteger("tf.level", 1);
					next.setTagCompound(tag);

					event.setOutput(next);
					event.setCost(3);
					event.setMaterialCost(1);
				}
			}
			if (!left.isEmpty() && left.getItem() instanceof ItemTFGuns && right.getItem() == TFItems.UPGRADE_1 && ret.isEmpty())
			{
				NBTTagCompound tag = new NBTTagCompound();

				NBTTagCompound tag2 = left.getTagCompound();
				if (tag2 != null && tag2.hasKey("tf.level"))
				{
					int level = tag2.getInteger("tf.level");

					if (3 <= level && level < 6)
					{
						ItemStack next = new ItemStack(left.getItem());
						tag.setInteger("tf.level", level + 1);
						next.setTagCompound(tag);

						event.setOutput(next);
						event.setCost(3 + (level * 3));
						event.setMaterialCost(1);
					}
				}
			}
			if (!left.isEmpty() && left.getItem() instanceof ItemTFGuns && right.getItem() == TFItems.UPGRADE_2 && ret.isEmpty())
			{
				NBTTagCompound tag = new NBTTagCompound();

				NBTTagCompound tag2 = left.getTagCompound();
				if (tag2 != null && tag2.hasKey("tf.level"))
				{
					int level = tag2.getInteger("tf.level");

					if (6 <= level && level < 10)
					{
						ItemStack next = new ItemStack(left.getItem());
						tag.setInteger("tf.level", level + 1);
						next.setTagCompound(tag);

						event.setOutput(next);
						event.setCost(3 + (level * 3));
						event.setMaterialCost(1);
					}
				}
			}
		}
	}
}
