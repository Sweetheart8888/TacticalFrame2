package tf2.items;

import java.util.List;

import javax.annotation.Nullable;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TF2Core;
import tf2.TFItems;
import tf2.tile.tileentity.TileEntityShield2;
import tf2.util.IHasModel;
import tf2.util.Reference;

public class ItemShieldRiot extends ItemShield implements IHasModel
{
	public ItemShieldRiot(String name)
	{
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(TF2Core.tabstfMain);
		TFItems.ITEMS.add(this);
	}
	@Override
	public void registerModel()
	{
		ModelLoader.setCustomModelResourceLocation(TFItems.RIOT_SHIELD, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "ironshield"), "inventory"));
		TF2Core.proxy.setCustomTileEntitySpecialRenderer(TFItems.RIOT_SHIELD, TileEntityShield2.class);
	}
	@SideOnly(Side.CLIENT)
	public CreativeTabs getCreativeTab()
	{
		return TF2Core.tabstfMain;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack)
	{
		return ("" + I18n.translateToLocal(this.getUnlocalizedNameInefficiently(stack) + ".name")).trim();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			tooltip.add(TextFormatting.BLUE + I18n.translateToLocal("tf.shield2"));
		}
		else
		{
			tooltip.add(TextFormatting.ITALIC + I18n.translateToLocal("LShift: Expand tooltip."));
		}
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
}
