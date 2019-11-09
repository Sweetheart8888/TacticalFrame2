package tf2;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import tf2.blocks.BlockBase;
import tf2.blocks.BlockBioGenerator;
import tf2.blocks.BlockCarryline;
import tf2.blocks.BlockCarrylinePowered;
import tf2.blocks.BlockCarryline_down;
import tf2.blocks.BlockCarryline_up;
import tf2.blocks.BlockCatapult;
import tf2.blocks.BlockCokeOven;
import tf2.blocks.BlockConcrete;
import tf2.blocks.BlockConcreteIron;
import tf2.blocks.BlockConcreteStairs;
import tf2.blocks.BlockCupola;
import tf2.blocks.BlockGunCraft;
import tf2.blocks.BlockIronFrame;
import tf2.blocks.BlockMachineChassis;
import tf2.blocks.BlockMachineStation;
import tf2.blocks.BlockMechaDock;
import tf2.blocks.BlockPulverizer;
import tf2.blocks.BlockSkillStation;
import tf2.blocks.BlockStoneMaker;
import tf2.blocks.BlockSynthesizer;
import tf2.blocks.BlockTFOre;
import tf2.blocks.slab.BlockConcreteSlab_0;
import tf2.blocks.slab.BlockConcreteSlab_1;
import tf2.blocks.slab.BlockConcreteSlab_2;
import tf2.blocks.slab.BlockConcreteSlab_3;
import tf2.blocks.slab.BlockConcreteSlab_4;
import tf2.blocks.slab.BlockConcreteSlab_5;
import tf2.blocks.slab.BlockConcreteSlab_6;
import tf2.blocks.slab.BlockConcreteSlab_7;
import tf2.blocks.slab.BlockConcreteSlab_8;

public class TFBlocks
{
	public static List<Block> BLOCKS = new ArrayList<Block>();

	public static final Block GUNCRAFT = new BlockGunCraft("guncraft").setHardness(2F).setResistance(20F);
	public static final Block SKILLSTATION = new BlockSkillStation("skillstation").setHardness(2F).setResistance(20F);
	public static final Block MACHINESTATION = new BlockMachineStation("machinestation").setHardness(2F).setResistance(20F);
	public static final Block MECHADOCK = new BlockMechaDock("mechadock").setHardness(2F).setResistance(20F);
	public static final Block CUPOLA = new BlockCupola("cupola").setHardness(3F).setResistance(20F);
	public static final Block COKE_OVEN = new BlockCokeOven("cokeoven").setHardness(3F).setResistance(20F);
	public static final Block BIO_GENERATOR = new BlockBioGenerator("biogenerator").setHardness(3F).setResistance(20F);
	public static final Block SYNTHESIZER = new BlockSynthesizer("synthesizer").setHardness(3F).setResistance(20F);
	public static final Block PULVERIZER = new BlockPulverizer("pulverizer").setHardness(3F).setResistance(20F);
	public static final Block STONEMAKER = new BlockStoneMaker("stonemaker").setHardness(3F).setResistance(20F);

	public static final Block ORE_NITER = new BlockTFOre("ore_niter", 1).setHardness(3F).setResistance(5F);
	public static final Block ORE_SULFUR = new BlockTFOre("ore_sulfur", 1).setHardness(3F).setResistance(5F);
	public static final Block ORE_MAGNETITE = new BlockTFOre("ore_magnetite", 1).setHardness(3F).setResistance(5F);
	public static final Block ORE_PYRODITE = new BlockTFOre("ore_pyrodite", 3).setHardness(10F).setResistance(200F);

	public static final Block COKE_BLOCK = new BlockBase("cokeblock", Material.ROCK, SoundType.STONE).setHardness(2F).setResistance(5F);
	public static final Block REINFORCED_IRON_BLOCK = new BlockBase("reinforced_ironblock", Material.IRON, SoundType.METAL).setHardness(3F).setResistance(20F);
	public static final Block RIGIDO_BLOCK = new BlockBase("rigidoblock", Material.IRON, SoundType.METAL).setHardness(5F).setResistance(2000F);

	public static final Block MACHINE_CHASSIS = new BlockMachineChassis("machinechassis", Material.IRON, SoundType.METAL).setHardness(3F).setResistance(20F);

	public static final Block CARRYLINE = new BlockCarryline("carryline").setHardness(2F).setResistance(20F);
	public static final Block CARRYLINE_DOWN = new BlockCarryline_down("carryline_down").setHardness(2F).setResistance(20F);
	public static final Block CARRYLINE_UP = new BlockCarryline_up("carryline_up").setHardness(2F).setResistance(20F);
	public static final Block CARRYLINE_POWERED = new BlockCarrylinePowered("carryline_powered").setHardness(2F).setResistance(20F);
	public static final Block CATAPULT = new BlockCatapult("catapult").setHardness(2F).setResistance(20F);

	public static final Block CONCRETE = new BlockConcrete("concrete").setHardness(3F).setResistance(20F);
	public static final Block CONCRETE_STAIRS_0 = new BlockConcreteStairs("concrete_stairs_0", CONCRETE.getDefaultState());
	public static final Block CONCRETE_STAIRS_1 = new BlockConcreteStairs("concrete_stairs_1", CONCRETE.getDefaultState());
	public static final Block CONCRETE_STAIRS_2 = new BlockConcreteStairs("concrete_stairs_2", CONCRETE.getDefaultState());
	public static final Block CONCRETE_STAIRS_3 = new BlockConcreteStairs("concrete_stairs_3", CONCRETE.getDefaultState());
	public static final Block CONCRETE_STAIRS_4 = new BlockConcreteStairs("concrete_stairs_4", CONCRETE.getDefaultState());
	public static final Block CONCRETE_STAIRS_5 = new BlockConcreteStairs("concrete_stairs_5", CONCRETE.getDefaultState());
	public static final Block CONCRETE_STAIRS_6 = new BlockConcreteStairs("concrete_stairs_6", CONCRETE.getDefaultState());
	public static final Block CONCRETE_STAIRS_7 = new BlockConcreteStairs("concrete_stairs_7", CONCRETE.getDefaultState());
	public static final Block CONCRETE_STAIRS_8 = new BlockConcreteStairs("concrete_stairs_8", CONCRETE.getDefaultState());
	public static final Block CONCRETE_SLAB_0 = new BlockConcreteSlab_0("concrete_slab_0").setHardness(3F).setResistance(20F);
	public static final Block CONCRETE_SLAB_1 = new BlockConcreteSlab_1("concrete_slab_1").setHardness(3F).setResistance(20F);
	public static final Block CONCRETE_SLAB_2 = new BlockConcreteSlab_2("concrete_slab_2").setHardness(3F).setResistance(20F);
	public static final Block CONCRETE_SLAB_3 = new BlockConcreteSlab_3("concrete_slab_3").setHardness(3F).setResistance(20F);
	public static final Block CONCRETE_SLAB_4 = new BlockConcreteSlab_4("concrete_slab_4").setHardness(3F).setResistance(20F);
	public static final Block CONCRETE_SLAB_5 = new BlockConcreteSlab_5("concrete_slab_5").setHardness(3F).setResistance(20F);
	public static final Block CONCRETE_SLAB_6 = new BlockConcreteSlab_6("concrete_slab_6").setHardness(3F).setResistance(20F);
	public static final Block CONCRETE_SLAB_7 = new BlockConcreteSlab_7("concrete_slab_7").setHardness(3F).setResistance(20F);
	public static final Block CONCRETE_SLAB_8 = new BlockConcreteSlab_8("concrete_slab_8").setHardness(3F).setResistance(20F);
	public static final Block IRON_FRAME = new BlockIronFrame("ironframe").setHardness(3F).setResistance(20F);
	public static final Block CONCRETE_IRON = new BlockConcreteIron("concreteiron").setHardness(20F).setResistance(3000000F);
}
