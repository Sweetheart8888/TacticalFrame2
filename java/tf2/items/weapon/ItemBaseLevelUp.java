package tf2.items.weapon;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.items.ItemBase;

public class ItemBaseLevelUp extends ItemBase
{
	public ItemBaseLevelUp(String name)
	{
		super(name);
	}

	public int getLevel(ItemStack itemstackIn)
	{
		int level = 0;
		NBTTagCompound nbt = itemstackIn.getTagCompound();

    	if(nbt != null && nbt.hasKey("tf.level"))
    	{
    		//if
    		level = nbt.getInteger("tf.level");
    	}
		return level;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if(this.getLevel(stack) == 10)
		{
			tooltip.add(TextFormatting.YELLOW + " " + I18n.translateToLocal("Lv: Max"));
		}
		else
		{
			tooltip.add(TextFormatting.GRAY + " " + I18n.translateToLocal("Lv: " + (this.getLevel(stack) + 1)));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}