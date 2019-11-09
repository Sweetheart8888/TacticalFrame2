package tf2.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TFBlocks;

public class BlockTFOre extends BlockBase
{
	public BlockTFOre(String name, int level)
	{
		super(name, Material.ROCK, SoundType.STONE);
		this.setHarvestLevel("pickaxe", level);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
	{
		if (this == TFBlocks.ORE_PYRODITE)
		{
			if (!entityIn.isImmuneToFire() && entityIn instanceof EntityLivingBase && !EnchantmentHelper.hasFrostWalkerEnchantment((EntityLivingBase) entityIn))
			{
				entityIn.attackEntityFrom(DamageSource.HOT_FLOOR, 1.0F);
			}
		}
		super.onEntityWalk(worldIn, pos, entityIn);
	}
}
