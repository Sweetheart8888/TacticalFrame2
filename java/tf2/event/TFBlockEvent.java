package tf2.event;


import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.PlaceEvent;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tf2.blocks.BlockProtection;

public class TFBlockEvent
{
	@SubscribeEvent
	public void TFBreakEvent(BreakEvent event)
	{
		BlockPos pos = event.getPos();
        IBlockState iblockstate = event.getWorld().getBlockState(pos);
        Block block = iblockstate.getBlock();
		if(!event.getWorld().isRemote)
		{
			if(!event.getPlayer().isCreative() && this.hasBlockProtection(event.getWorld(), event.getPos()) &&
					!(event.getState().getBlock() instanceof BlockProtection || event.getState().getBlock() instanceof BlockDoor)
			)
			{
				event.setCanceled(true);
			}
		}
	}

	@SubscribeEvent
	public void TFPlaceEvent(PlaceEvent event)
	{
		if(!event.getWorld().isRemote)
		{
			BlockPos pos = event.getPos();
            IBlockState iblockstate = event.getWorld().getBlockState(pos);
            Block block = iblockstate.getBlock();
			{
				if(!event.getPlayer().isCreative() && this.hasBlockProtection(event.getWorld(), event.getPos()) && !(event.getState().getBlock() instanceof BlockProtection))
				{
					event.getWorld().setBlockState(pos, Blocks.AIR.getDefaultState());
					block.spawnAsEntity(event.getWorld(), pos, block.getItem(event.getWorld(), pos, iblockstate));
				}
			}
		}
	}

	@SubscribeEvent
	public void TFExplosionEvent(Detonate event)
	{
		List<BlockPos> list = new ArrayList<BlockPos>();
		List<BlockPos> affectedBlock = event.getAffectedBlocks();

		boolean clear = false;

        CHECK : for (BlockPos blockpos : event.getAffectedBlocks())
        {
            IBlockState iblockstate = event.getWorld().getBlockState(blockpos);
            Block block = iblockstate.getBlock();

            if(iblockstate.getMaterial() != Material.AIR)
            {
        		if(this.hasBlockProtection(event.getWorld(), blockpos))
        		{
        			clear = true;
        		}
            }
    		if(clear) break CHECK;
        }

        if(clear)
        {
        	event.getAffectedBlocks().clear();
        }

//        event.getAffectedBlocks().removeAll(list);
	}

	@SubscribeEvent
	public void TFRightClickBlockEvent(FillBucketEvent event)
	{
//		if(!event.getWorld().isRemote)
		{
			if(event.getTarget() != null)
			{
				if(!event.getEntityPlayer().isCreative() && this.hasBlockProtection(event.getWorld(), event.getTarget().getBlockPos()))
				{
					event.setCanceled(true);
				}
			}
		}
	}

    private boolean hasBlockProtection(World worldIn, BlockPos pos)
    {
        for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-29, -16, -29), pos.add(29, 16, 29)))
        {
            if (worldIn.getBlockState(blockpos$mutableblockpos).getBlock() instanceof BlockProtection || worldIn.getBlockState(blockpos$mutableblockpos).getBlock() instanceof BlockDoor)
            {
                return true;
            }
        }
        return false;
    }
}
