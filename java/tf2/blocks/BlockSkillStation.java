package tf2.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import tf2.TF2Core;

public class BlockSkillStation extends BlockBase
{
	public BlockSkillStation(String name)
	{
		super(name, Material.IRON, SoundType.METAL);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		if (worldIn.isRemote)
		{
			return true;
		} else
		{
			playerIn.openGui(TF2Core.INSTANCE, TF2Core.guiSkill, worldIn, pos.getX(), pos.getY(), pos.getZ());
			return true;
		}

	}

}
