package tf2.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TF2Core;
import tf2.TFBlocks;
import tf2.TFItems;
import tf2.util.IHasModel;


public class BlockConcreteStairs extends BlockStairs implements IHasModel
{
	public BlockConcreteStairs(String name, IBlockState blockstate)
	{
		super(blockstate);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(TF2Core.tabstfBlocks);
		this.useNeighborBrightness = true;
		TFBlocks.BLOCKS.add(this);
		TFItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModel()
	{
		TF2Core.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "Inventory");
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
	{
		tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("Can't Mob Spawn"));
		super.addInformation(stack, player, tooltip, advanced);
	}
}
