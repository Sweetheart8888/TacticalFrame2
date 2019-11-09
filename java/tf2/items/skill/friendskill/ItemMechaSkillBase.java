package tf2.items.skill.friendskill;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.items.skill.ItemSkillBase;

public class ItemMechaSkillBase  extends ItemSkillBase
{
	private final EnumFriendSkillType skillType;
	public ItemMechaSkillBase(String name, EnumFriendSkillType type)
	{
		super(name);
		this.skillType = type;
		this.maxStackSize = 8;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{

		String text = ("" + I18n.translateToLocal(this.getUnlocalizedName().replaceAll("item", "skill"))).trim();

		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			tooltip.add(TextFormatting.BLUE + " " + text);
		}
		else
		{
			switch(this.skillType)
			{
				case UNIQUE: tooltip.add(TextFormatting.AQUA + " " + I18n.translateToLocal("skill.mechaskill_unique")); break;
				case COMMON: tooltip.add(TextFormatting.AQUA + " " + I18n.translateToLocal("skill.mechaskill")); break;
				case CONSUME: tooltip.add(TextFormatting.AQUA + " " + I18n.translateToLocal("skill.mechaskill_consume")); break;
			}

			tooltip.add(TextFormatting.ITALIC + I18n.translateToLocal("LShift: Expand tooltip."));
		}

        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    /**
     * Return an item rarity from EnumRarity
     */
    public EnumRarity getRarity(ItemStack stack)
    {
        return this.skillType == EnumFriendSkillType.UNIQUE ? EnumRarity.UNCOMMON : EnumRarity.COMMON;
    }

    public static enum EnumFriendSkillType
    {
    	UNIQUE,
    	COMMON,
    	CONSUME
    }
}
