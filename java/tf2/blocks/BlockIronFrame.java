package tf2.blocks;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
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

public class BlockIronFrame extends Block implements IHasModel
{
	public static final PropertyInteger METADATA = PropertyInteger.create("meta", 0, 2);

	public BlockIronFrame(String name)
	{
		super(Material.IRON);
		this.setSoundType(SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(METADATA, 0));

		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(TF2Core.tabstfBlocks);

		TFBlocks.BLOCKS.add(this);
		TFItems.ITEMS.add(new ItemBlockDamage(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public void registerModel()
	{
		TF2Core.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory_0");
		TF2Core.proxy.registerItemRenderer(Item.getItemFromBlock(this), 1, "inventory_1");
		TF2Core.proxy.registerItemRenderer(Item.getItemFromBlock(this), 2, "inventory_2");
	}

	@Override
	public boolean canCreatureSpawn(IBlockState state, IBlockAccess world, BlockPos pos, net.minecraft.entity.EntityLiving.SpawnPlacementType type)
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state)
	{
		return false;
	}

	//ItemStackのmetadataからIBlockStateを生成。設置時に呼ばれる。
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(METADATA, meta);
	}

	//IBlockStateからItemStackのmetadataを生成。ドロップ時とテクスチャ・モデル参照時に呼ばれる。
	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (Integer) state.getValue(METADATA);
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
	{
		return state;
	}

	//初期BlockStateの生成。
	@Override
	protected BlockStateContainer createBlockState()
	{
		return new BlockStateContainer(this, METADATA);
	}

	//ダメージ値に対応したブロックをドロップ
	@Override
	public int damageDropped(IBlockState state)
	{
		return (Integer) state.getValue(METADATA);
	}

	//複数種類のブロックをクリエイティブタブに登録するためのメソッド
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items)
	{
		for (int i = 0; i < 3; i++)
		{
			items.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
	{
		tooltip.add(TextFormatting.GRAY + I18n.translateToLocal("Can't Mob Spawn"));
		super.addInformation(stack, player, tooltip, advanced);
	}
}
