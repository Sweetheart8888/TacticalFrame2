package tf2;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import tf2.entity.mob.frend.EntityFriendMecha;
import tf2.recipes.OreDictionaryRegister;
import tf2.tile.container.ContainerBioGenerator;
import tf2.tile.container.ContainerCokeOven;
import tf2.tile.container.ContainerCupola;
import tf2.tile.container.ContainerFriendMechaInventory;
import tf2.tile.container.ContainerGunCraft;
import tf2.tile.container.ContainerMachineStation;
import tf2.tile.container.ContainerMechaDock;
import tf2.tile.container.ContainerPulverizer;
import tf2.tile.container.ContainerSkillStation;
import tf2.tile.container.ContainerStoneMaker;
import tf2.tile.container.ContainerSynthesizer;
import tf2.tile.gui.GuiBioGenerator;
import tf2.tile.gui.GuiCokeOven;
import tf2.tile.gui.GuiCupola;
import tf2.tile.gui.GuiEntityFriendMechaInventory;
import tf2.tile.gui.GuiGunCraft;
import tf2.tile.gui.GuiMachineStation;
import tf2.tile.gui.GuiMechaDock;
import tf2.tile.gui.GuiPulverizer;
import tf2.tile.gui.GuiSkillStation;
import tf2.tile.gui.GuiStoneMaker;
import tf2.tile.gui.GuiSynthesizer;
import tf2.tile.tileentity.TileEntityBioGenerator;
import tf2.tile.tileentity.TileEntityCokeOven;
import tf2.tile.tileentity.TileEntityCupola;
import tf2.tile.tileentity.TileEntityMechaDock;
import tf2.tile.tileentity.TileEntityPulverizer;
import tf2.tile.tileentity.TileEntityStoneMaker;
import tf2.tile.tileentity.TileEntitySynthesizer;

public class CommonProxy implements IGuiHandler
{
	public void registerItemRenderer(Item item, int meta, String id)
	{}

	public void loadEntity()
	{}

	public EntityPlayer getClientPlayer()
	{
		return null;
	}

	public void registerClientInformation()
	{}

	public boolean reload()
	{
		return false;
	}

	public boolean getoff()
	{
		return false;
	}

	public boolean slotchange()
	{
		return false;
	}

	public void loadRecipes()
	{
		OreDictionaryRegister.load();
	}

	public void registerFluidBlockRendering(Block block, String name)
	{}

	public boolean jumped()
	{
		return false;
	}

	public boolean shift()
	{
		return false;
	}

	public boolean rightclick()
	{
		return false;
	}

	public boolean leftclick()
	{
		return false;
	}

	public boolean rightmove()
	{
		return false;
	}

	public boolean leftmove()
	{
		return false;
	}

	public boolean frontmove()
	{
		return false;
	}

	public boolean backmove()
	{
		return false;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);

		if (ID == TF2Core.guiTurret)
		{
			Entity entity = world.getEntityByID(x);

			if (entity instanceof EntityFriendMecha)
			{
				return new ContainerFriendMechaInventory((EntityFriendMecha) entity, player);
			}
		}
//		if (ID == TFCore.guiGynoid)
//		{
//			Entity entity = world.getEntityByID(x);
//
//			if (entity instanceof EntityGynoid)
//			{
//				return new ContainerGynoidTrade(player.inventory, world, (EntityGynoid) entity);
//			}
//		}
//
		if (!world.isBlockLoaded(pos)) return null;
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityCupola)
		{
			return new ContainerCupola(player, (TileEntityCupola) tile);
		}
		if (tile instanceof TileEntityCokeOven)
		{
			return new ContainerCokeOven(player, (TileEntityCokeOven) tile);
		}
		if (tile instanceof TileEntityBioGenerator)
		{
			return new ContainerBioGenerator(player, (TileEntityBioGenerator) tile);
		}
		if (tile instanceof TileEntityPulverizer)
		{
			return new ContainerPulverizer(player, (TileEntityPulverizer) tile);
		}
		if (tile instanceof TileEntityStoneMaker)
		{
			return new ContainerStoneMaker(player, (TileEntityStoneMaker) tile);
		}
		if (tile instanceof TileEntitySynthesizer)
		{
			return new ContainerSynthesizer(player, (TileEntitySynthesizer) tile);
		}
//		if (tile instanceof TileEntityContainerBox)
//		{
//			return new ContainerContainerBox(player, (TileEntityContainerBox) tile);
//		}
//		if (tile instanceof TileEntityRigidoBox)
//		{
//			return new ContainerRigidoBox(player, (TileEntityRigidoBox) tile);
//		}
//		if (tile instanceof TileEntityRigidoFurnace)
//		{
//			return new ContainerRigidoFurnace(player, (TileEntityRigidoFurnace) tile);
//		}
//		if (tile instanceof TileEntityNitroReactor)
//		{
//			return new ContainerNitroReactor(player, (TileEntityNitroReactor) tile);
//		}
//		if (tile instanceof TileEntityAdvPulverizer)
//		{
//			return new ContainerAdvPulverizer(player, (TileEntityAdvPulverizer) tile);
//		}
//		if (tile instanceof TileEntityAdvExtractor)
//		{
//			return new ContainerAdvExtractor(player, (TileEntityAdvExtractor) tile);
//		}
//		if (tile instanceof TileEntityCokeChamber)
//		{
//			return new ContainerCokeChamber(player, (TileEntityCokeChamber) tile);
//		}

		if (tile instanceof TileEntityMechaDock)
		{
			return new ContainerMechaDock(player, (TileEntityMechaDock) tile);
		}
		if (ID == TF2Core.guiGun)
		{
			return world.getBlockState(new BlockPos(x, y, z)).getBlock() == TFBlocks.GUNCRAFT ? new ContainerGunCraft(player.inventory, world, new BlockPos(x, y, z)) : null;
		}
		if (ID == TF2Core.guiSkill)
		{
			return world.getBlockState(new BlockPos(x, y, z)).getBlock() == TFBlocks.SKILLSTATION ? new ContainerSkillStation(player.inventory, world, new BlockPos(x, y, z)) : null;
		}
		if (ID == TF2Core.guiMachine)
		{
			return world.getBlockState(new BlockPos(x, y, z)).getBlock() == TFBlocks.MACHINESTATION ? new ContainerMachineStation(player.inventory, world, new BlockPos(x, y, z)) : null;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		BlockPos pos = new BlockPos(x, y, z);

		if (ID == TF2Core.guiTurret)
		{
			Entity entity = world.getEntityByID(x);

			if (entity instanceof EntityFriendMecha)
			{
				return new GuiEntityFriendMechaInventory((EntityFriendMecha) entity, player);
			}
		}
//		if (ID == TFCore.guiGynoid)
//		{
//			Entity entity = world.getEntityByID(x);
//
//			if (entity instanceof EntityGynoid)
//			{
//				return new GuiEntityGynoidTrade(player.inventory, world, (EntityGynoid) entity);
//			}
//		}
//
		if (!world.isBlockLoaded(pos))
			return null;
		TileEntity tile = world.getTileEntity(pos);
		if (tile instanceof TileEntityCupola)
		{
			return new GuiCupola(player, (TileEntityCupola) tile);
		}
		if (tile instanceof TileEntityCokeOven)
		{
			return new GuiCokeOven(player, (TileEntityCokeOven) tile);
		}
		if (tile instanceof TileEntityBioGenerator)
		{
			return new GuiBioGenerator(player, (TileEntityBioGenerator) tile);
		}
		if (tile instanceof TileEntityPulverizer)
		{
			return new GuiPulverizer(player, (TileEntityPulverizer) tile);
		}
		if (tile instanceof TileEntityStoneMaker)
		{
			return new GuiStoneMaker(player, (TileEntityStoneMaker) tile);
		}
		if (tile instanceof TileEntitySynthesizer)
		{
			return new GuiSynthesizer(player, (TileEntitySynthesizer) tile);
		}
//		if (tile instanceof TileEntityContainerBox)
//		{
//			return new GuiContainerBox(player, (TileEntityContainerBox) tile);
//		}
//		if (tile instanceof TileEntityRigidoBox)
//		{
//			return new GuiRigidoBox(player, (TileEntityRigidoBox) tile);
//		}
//		if (tile instanceof TileEntityRigidoFurnace)
//		{
//			return new GuiRigidoFurnace(player, (TileEntityRigidoFurnace) tile);
//		}
//		if (tile instanceof TileEntityNitroReactor)
//		{
//			return new GuiNitroReactor(player, (TileEntityNitroReactor) tile);
//		}
//		if (tile instanceof TileEntityAdvPulverizer)
//		{
//			return new GuiAdvPulverizer(player, (TileEntityAdvPulverizer) tile);
//		}
//		if (tile instanceof TileEntityAdvExtractor)
//		{
//			return new GuiAdvExtractor(player, (TileEntityAdvExtractor) tile);
//		}
//		if (tile instanceof TileEntityCokeChamber)
//		{
//			return new GuiCokeChamber(player, (TileEntityCokeChamber) tile);
//		}
		if (tile instanceof TileEntityMechaDock)
		{
			return new GuiMechaDock(player, (TileEntityMechaDock) tile);
		}
		if (ID == TF2Core.guiGun)
		{
			return world.getBlockState(new BlockPos(x, y, z)).getBlock() == TFBlocks.GUNCRAFT ? new GuiGunCraft(player.inventory, world, new BlockPos(x, y, z)) : null;
		}
		if (ID == TF2Core.guiSkill)
		{
			return world.getBlockState(new BlockPos(x, y, z)).getBlock() == TFBlocks.SKILLSTATION ? new GuiSkillStation(player.inventory, world, new BlockPos(x, y, z)) : null;
		}
		if (ID == TF2Core.guiMachine)
		{
			return world.getBlockState(new BlockPos(x, y, z)).getBlock() == TFBlocks.MACHINESTATION ? new GuiMachineStation(player.inventory, world, new BlockPos(x, y, z)) : null;
		}
		return null;
	}

	public <T extends TileEntity> void setCustomTileEntitySpecialRenderer(Item itemBlock, Class<T> tileEntityClass)
	{}
}