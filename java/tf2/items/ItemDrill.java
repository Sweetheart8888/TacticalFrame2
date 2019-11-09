package tf2.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemDrill extends ItemBase
{
	public ItemDrill(String name)
	{
		super(name);
		this.setMaxStackSize(1);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	public boolean canHarvestBlock(IBlockState blockIn)
	{
		Block block = blockIn.getBlock();

		if (block == Blocks.OBSIDIAN)
		{
			return true;
		}
		else
		{
			Material material = blockIn.getMaterial();
			return material == Material.ROCK ? true : (material == Material.IRON ? true : (material == Material.GROUND ? true : (material == Material.SAND ? true : material == Material.ANVIL)));
		}
	}

	@Override
	public float getDestroySpeed(ItemStack stack, IBlockState state)
	{
		if (this.getDamage(stack) < this.getMaxDamage(stack))
		{
			Material material = state.getMaterial();
			return material != Material.IRON && material != Material.ANVIL && material != Material.GROUND && material != Material.SAND && material != Material.ROCK ? 1.0F : 45F;
		}
		else
		{
			return 0.2F;
		}
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
	{
		if (this.getDamage(stack) < this.getMaxDamage(stack))
		{
			stack.damageItem(1, entityLiving);
		}
		return true;
	}
}
