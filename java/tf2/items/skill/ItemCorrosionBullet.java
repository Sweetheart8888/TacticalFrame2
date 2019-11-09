package tf2.items.skill;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCorrosionBullet extends ItemSkillBase
{
	public ItemCorrosionBullet(String name)
	{
		super(name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		 super.addInformation(stack, worldIn, tooltip, flagIn);


        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
        {
        	tooltip.add(TextFormatting.GRAY + " " + I18n.translateToLocal("skill.inventory"));
        	tooltip.add(TextFormatting.BLUE + " : " + I18n.translateToLocal("skill.arskill1"));
        	tooltip.add(TextFormatting.BLUE + " : " + I18n.translateToLocal("skill.arskill2"));
        }
        else
        {
        	tooltip.add(TextFormatting.ITALIC + I18n.translateToLocal("LShift: Expand tooltip."));
        }

    }
}
