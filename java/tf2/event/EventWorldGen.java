package tf2.event;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import tf2.TF2Core;
import tf2.TFBlocks;

public class EventWorldGen
{
	public Random rand;
    private World world;
    private Biome biome;

	@SubscribeEvent
	public void generateOrePre(OreGenEvent.Pre event)
	{
		this.rand = event.getRand();
        this.world = event.getWorld();
        this.biome = event.getWorld().getBiomeForCoordsBody(event.getPos());


		//鉱脈の大きさ
		WorldGenerator niter = new WorldGenMinable(TFBlocks.ORE_NITER.getDefaultState(), 10);
		//生成頻度
		if(TerrainGen.generateOre(event.getWorld(), event.getRand() ,niter, event.getPos(), OreGenEvent.GenerateMinable.EventType.CUSTOM))
			genStandardOre(event.getWorld(), event.getPos(), 3, niter, 0, 80, event.getRand());


		WorldGenerator sulfur = new WorldGenMinable(TFBlocks.ORE_SULFUR.getDefaultState(), 10);

		if(TerrainGen.generateOre(event.getWorld(), event.getRand() ,sulfur, event.getPos(), OreGenEvent.GenerateMinable.EventType.CUSTOM))
			genStandardOre(event.getWorld(), event.getPos(), 3, sulfur, 0, 80, event.getRand());



		WorldGenerator pyrodite = new WorldGenMinable(TFBlocks.ORE_PYRODITE.getDefaultState(), 5);

		if(TerrainGen.generateOre(event.getWorld(), event.getRand() ,pyrodite, event.getPos(), OreGenEvent.GenerateMinable.EventType.CUSTOM))
			genStandardOre(event.getWorld(), event.getPos(), 2, pyrodite, 0, 10, event.getRand());

		if (TF2Core.CONFIG.ironGenerate)
		{
			WorldGenerator magnetite = new WorldGenMinable(TFBlocks.ORE_MAGNETITE.getDefaultState(), 12);

			if(TerrainGen.generateOre(event.getWorld(), event.getRand() ,magnetite, event.getPos(), OreGenEvent.GenerateMinable.EventType.CUSTOM))
				genStandardOre(event.getWorld(), event.getPos(), 2, magnetite, 0, 40, event.getRand());
		}
	}

	protected void genStandardOre(World world, BlockPos pos, int size, WorldGenerator generator, int minY, int maxY, Random rnd)
	{
		int l;

		if(maxY < minY)
		{
			l = minY;
			minY = maxY;
			maxY = l;
		}
		else if(maxY == minY)
		{
			if(minY < 255)
			{
				++maxY;
			}
			else
			{
				--minY;
			}
		}

		for(l = 0; l < size; ++l){
			BlockPos blockpos = pos.add(rnd.nextInt(16), rnd.nextInt(maxY - minY) + minY, rnd.nextInt(16));
			generator.generate(world, rnd, blockpos);
		}
	}
}
