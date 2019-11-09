package tf2;

import java.io.File;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.WorldEvent.CreateSpawnPosition;
import net.minecraftforge.event.world.WorldEvent.Save;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkCheckHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.client.GunRenderSetup;
import tf2.common.PacketHandler;
import tf2.entity.mob.enemy.EntityTM04;
import tf2.entity.mob.enemy.EntityTM05;
import tf2.entity.mob.enemy.EntityTM06;
import tf2.entity.mob.enemy.EntityTM07;
import tf2.entity.mob.enemy.EntityTM11;
import tf2.entity.mob.enemy.EntityTM12;
import tf2.entity.mob.enemy.EntityTM26A;
import tf2.entity.mob.enemy.EntityTM26B;
import tf2.entity.mob.enemy.EntityTM26C;
import tf2.entity.mob.enemy.EntityTM41;
import tf2.event.EventGunRender;
import tf2.event.EventWorldGen;
import tf2.event.KeyEvent;
import tf2.event.TFAnvilEvent;
import tf2.event.TFDeathEvent;
import tf2.event.TFHurtEvent;
import tf2.event.TFLivingUpdateEvent;
import tf2.event.TFMechaSkillEvent;
import tf2.event.TFRidingRenderEvent;
import tf2.event.TFSightEvent;
import tf2.event.TFZoomEvent;
import tf2.potion.TFPotionPlus;
import tf2.recipes.guncraft.CraftingManagerGunCraft;
import tf2.recipes.guncraft.CraftingManagerMachineStation;
import tf2.recipes.guncraft.CraftingManagerSkillStation;
import tf2.tile.tileentity.TileEntityBioGenerator;
import tf2.tile.tileentity.TileEntityCokeOven;
import tf2.tile.tileentity.TileEntityCupola;
import tf2.tile.tileentity.TileEntityMechaDock;
import tf2.tile.tileentity.TileEntityPulverizer;
import tf2.tile.tileentity.TileEntityStoneMaker;
import tf2.tile.tileentity.TileEntitySynthesizer;
import tf2.util.CreativeTabsTFBlocks;
import tf2.util.CreativeTabsTFGuns;
import tf2.util.CreativeTabsTFMain;
import tf2.util.CreativeTabsTFSkills;
import tf2.util.Reference;
import tf2.util.RegistryHandler;
import tf2.util.TFAdvancements;
import tf2.util.TFWorldConfigManager;

@Mod(modid = Reference.MOD_ID, version = Reference.VERSION, acceptedMinecraftVersions = Reference.ACCEPTED_VERSIONS, name = Reference.NAME, guiFactory = "tf2.client.gui.TFGuiFactory")
public class TF2Core {

	@Mod.Instance(Reference.MOD_ID)
	public static TF2Core INSTANCE;

	@SidedProxy(modId = Reference.MOD_ID, clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;

	public static final CreativeTabs tabstfMain = new CreativeTabsTFMain("TFMain");
	public static final CreativeTabs tabstfGuns = new CreativeTabsTFGuns("TFGuns");
	public static final CreativeTabs tabstfBlocks = new CreativeTabsTFBlocks("TFBlocks");
	public static final CreativeTabs tabstfSkills = new CreativeTabsTFSkills("TFSkills");

	public static final int guiGun = 0;
	public static final int guiCupola = 1;
	public static final int guiCokeoven = 2;
	public static final int guiBiogenerator = 3;
	public static final int guiPulverizer = 4;
	public static final int guiStonemaker = 5;
	public static final int guiRigidobox = 6;
	public static final int guiContainerbox = 7;
	public static final int guiSkill = 8;
	public static final int guiMachine = 9;
	public static final int guiRigidofurnace = 10;
	public static final int guiSynthesizer = 11;
	public static final int guiNitroreactor = 12;
	public static final int guiAdvPulverizer = 13;
	public static final int guiAdvExtractor = 14;
	public static final int guiCokechamber = 15;
	public static final int guiPromoter = 16;
	public static final int guiTurret = 17;
	public static final int guiGynoid = 18;
	public static final int guiMechaDock = 18;

	public static final Logger logger = LogManager.getLogger(Reference.NAME);
	public static TFConfig CONFIG = new TFConfig();
	public static Configuration config;

	public static final ResourceLocation ENTITIES_TM04 = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/tm04"));
	public static final ResourceLocation ENTITIES_TM05 = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/tm05"));
	public static final ResourceLocation ENTITIES_TM06 = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/tm06"));
	public static final ResourceLocation ENTITIES_TM07 = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/tm07"));
	public static final ResourceLocation ENTITIES_TM11 = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/tm11"));
	public static final ResourceLocation ENTITIES_TM12 = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/tm12"));
	public static final ResourceLocation ENTITIES_TM18 = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/tm18"));
	public static final ResourceLocation ENTITIES_TM22 = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/tm22"));
	public static final ResourceLocation ENTITIES_TM26 = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/tm26"));
	public static final ResourceLocation ENTITIES_TM26D = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/tm26d"));
	public static final ResourceLocation ENTITIES_TM31 = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/tm31"));
	public static final ResourceLocation ENTITIES_TM33 = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/tm33"));
	public static final ResourceLocation ENTITIES_TM34 = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/tm34"));
	public static final ResourceLocation ENTITIES_TM41 = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/tm41"));
	public static final ResourceLocation ENTITIES_TF08 = LootTableList.register(new ResourceLocation(Reference.MOD_ID, "entities/tf08"));

	@Mod.EventHandler
	public void construct(FMLConstructionEvent event)
	{
		MinecraftForge.EVENT_BUS.register(this);
	}

	@SubscribeEvent
	public void registerItems(RegistryEvent.Register<Item> event)
	{
		RegistryHandler.onItemRegister(event);
	}

	@SubscribeEvent
	public void registerBlocks(RegistryEvent.Register<Block> event)
	{
		RegistryHandler.onBlockRegister(event);

		//--Add Liquid--
		//TFBlocks.init();
	}

	@SubscribeEvent
	public void registerEntityEntries(RegistryEvent.Register<EntityEntry> event)
	{
		RegistryHandler.registerEntities();
	}

	@SubscribeEvent
	public void registerRecipes(RegistryEvent.Register<IRecipe> event)
	{
		CraftingManagerGunCraft.init();
		CraftingManagerSkillStation.init();
		CraftingManagerMachineStation.init();
	}

	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void registerModels(ModelRegistryEvent event)
	{
		RegistryHandler.onModelRegister(event);
	}

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		loadConfig();
		syncConfig();

		PacketHandler.init();
		//TFRecipes.initRecipeLists();

		TFPotionPlus.potionSetup();

		if (event.getSide().isClient())
		{
			proxy.loadEntity();
		}
		GunRenderSetup.gunSetup(event);
	}

	public static void loadConfig()
	{
		File configFile = new File(Loader.instance().getConfigDir(), "tacticalframe" +  File.separatorChar + "tf.cfg");

		if (!configFile.exists())
		{
			try
			{
				configFile.createNewFile();
			} catch (Exception e)
			{
				logger.warn("Could not create a TacticalFrame config file.");
				logger.warn(e.getLocalizedMessage());
			}
		}
		config = new Configuration(configFile);
		config.load();
	}

	public static void syncConfig()
	{
		CONFIG.init(config);
		config.save();
	}

	@EventHandler
	public void serverStarting(FMLServerStartingEvent event)
	{
		event.registerServerCommand(new TFCommand());
		TFWorldConfigManager.loadWorldConfigFile(event.getServer().getEntityWorld());
	}

	@SubscribeEvent
	public void serverCreateTierConfig(CreateSpawnPosition event)
	{
		if(event.getWorld().provider.getDimension() == 0)
		{
			TFWorldConfigManager.createWorldConfigFile(event.getWorld());
		}
	}

	@SubscribeEvent
	public void serverSaveTierConfig(Save event)
	{
		TFWorldConfigManager.saveWorldConfigFile(event.getWorld());
	}

	@NetworkCheckHandler
	public boolean netCheckHandler(Map<String, String> mods, Side side)
	{
		return true;
	}

	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		this.addRecipe();
		TFAdvancements.init();

		GameRegistry.registerTileEntity(TileEntityCupola.class, "tf2:tile.cupola");
		GameRegistry.registerTileEntity(TileEntityCokeOven.class, "tf2:tile.cokeoven");
		GameRegistry.registerTileEntity(TileEntityBioGenerator.class, "tf2:tile.biogenerator");
		GameRegistry.registerTileEntity(TileEntitySynthesizer.class, "tf2:tile.synthesizer");
		GameRegistry.registerTileEntity(TileEntityPulverizer.class, "tf2:tile.pulverizer");
		GameRegistry.registerTileEntity(TileEntityStoneMaker.class, "tf2:tile.stonemaker");
//		GameRegistry.registerTileEntity(TileEntityContainerBox.class, "tf:tile.containerbox");
//		GameRegistry.registerTileEntity(TileEntityRigidoBox.class, "tf:tile.rigidobox");
//		GameRegistry.registerTileEntity(TileEntityRigidoFurnace.class, "tf:tile.rigidofurnace");
//		GameRegistry.registerTileEntity(TileEntityNitroReactor.class, "tf:tile.nitroreactor");
//		GameRegistry.registerTileEntity(TileEntityAdvPulverizer.class, "tf:tile.advpulverizer");
//		GameRegistry.registerTileEntity(TileEntityAdvExtractor.class, "tf:tile.advextractor");
//		GameRegistry.registerTileEntity(TileEntityCokeChamber.class, "tf:tile.cokechamber");
//		GameRegistry.registerTileEntity(TileEntityPromoter.class, "tf:tile.promoter");
//		GameRegistry.registerTileEntity(TileEntityTFMobSpawner.class, "tf:tile.tfmobspawner");
		GameRegistry.registerTileEntity(TileEntityMechaDock.class, "tf2:tile.mechadock");

		proxy.loadRecipes();
		proxy.registerClientInformation();

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new CommonProxy());
		MinecraftForge.ORE_GEN_BUS.register(new EventWorldGen());
		MinecraftForge.EVENT_BUS.register(new EventGunRender());
		MinecraftForge.EVENT_BUS.register(new TFRidingRenderEvent());
		MinecraftForge.EVENT_BUS.register(new TFZoomEvent());
		MinecraftForge.EVENT_BUS.register(new TFSightEvent());
		MinecraftForge.EVENT_BUS.register(new TFHurtEvent());
//		MinecraftForge.EVENT_BUS.register(new TFAchievementEvent());
		MinecraftForge.EVENT_BUS.register(new TFLivingUpdateEvent());
		MinecraftForge.EVENT_BUS.register(new KeyEvent());
//		MinecraftForge.EVENT_BUS.register(new TFBlockEvent());
		MinecraftForge.EVENT_BUS.register(new TFAnvilEvent());
		MinecraftForge.EVENT_BUS.register(new TFDeathEvent());
		MinecraftForge.EVENT_BUS.register(new TFMechaSkillEvent());

		for (Biome biome : Biome.REGISTRY)
		{
			if (biome != null && isSpawnableBiomeType(biome))
			{
				EntityRegistry.addSpawn(EntityTM06.class, TF2Core.CONFIG.spawnRatetier1, 1, 5, EnumCreatureType.MONSTER, biome);

				EntityRegistry.addSpawn(EntityTM04.class, TF2Core.CONFIG.spawnRatetier1, 1, 5, EnumCreatureType.MONSTER, biome);
				EntityRegistry.addSpawn(EntityTM05.class, TF2Core.CONFIG.spawnRatetier1, 1, 5, EnumCreatureType.MONSTER, biome);
				EntityRegistry.addSpawn(EntityTM07.class, TF2Core.CONFIG.spawnRatetier1, 1, 5, EnumCreatureType.MONSTER, biome);
				EntityRegistry.addSpawn(EntityTM11.class, TF2Core.CONFIG.spawnRatetier1, 1, 5, EnumCreatureType.MONSTER, biome);

				EntityRegistry.addSpawn(EntityTM26A.class, TF2Core.CONFIG.spawnRatetier2, 1, 5, EnumCreatureType.MONSTER, biome);
				EntityRegistry.addSpawn(EntityTM26B.class, TF2Core.CONFIG.spawnRatetier2, 1, 5, EnumCreatureType.MONSTER, biome);
				EntityRegistry.addSpawn(EntityTM26C.class, TF2Core.CONFIG.spawnRatetier2, 1, 5, EnumCreatureType.MONSTER, biome);
				EntityRegistry.addSpawn(EntityTM12.class, TF2Core.CONFIG.spawnRatetier2, 1, 2, EnumCreatureType.MONSTER, biome);

				EntityRegistry.addSpawn(EntityTM41.class, TF2Core.CONFIG.spawnRatetier3, 1, 2, EnumCreatureType.MONSTER, biome);
			}
		}
		for (Biome biome : Biome.REGISTRY)
		{
			if (biome != null && isSpawninNether(biome))
			{
//				EntityRegistry.addSpawn(EntityTM22.class, TFCore.CONFIG.spawnRateneter, 1, 5, EnumCreatureType.MONSTER, biome);
//				EntityRegistry.addSpawn(EntityTM31.class, TFCore.CONFIG.spawnRateneter, 1, 1, EnumCreatureType.MONSTER, biome);
			}
		}
	}
	private boolean isSpawnableBiomeType(Biome biome)
	{
		if (BiomeDictionary.hasType(biome, Type.NETHER))
		{
			return false;
		}
		if (BiomeDictionary.hasType(biome, Type.MUSHROOM))
		{
			return false;
		}
		if (BiomeDictionary.hasType(biome, Type.END))
		{
			return false;
		}
		return true;
	}
	private boolean isSpawninNether(Biome biome)
	{
		return BiomeDictionary.hasType(biome, Type.NETHER);
	}
	public void addRecipe()
	{
		GameRegistry.addSmelting(TFBlocks.ORE_MAGNETITE, new ItemStack(Items.IRON_INGOT), 0.2f);
		GameRegistry.addSmelting(TFBlocks.ORE_NITER, new ItemStack(TFItems.NITER), 0.2f);
		GameRegistry.addSmelting(TFBlocks.ORE_SULFUR, new ItemStack(TFItems.SULFUR), 0.2f);
		GameRegistry.addSmelting(TFItems.SCRAP_RUBBER, new ItemStack(TFItems.RUBBER), 0.3f);

		GameRegistry.addSmelting(new ItemStack(TFItems.POWDER_IRON), new ItemStack(Items.IRON_INGOT), 0.2f);
		GameRegistry.addSmelting(new ItemStack(TFItems.POWDER, 1, 1), new ItemStack(Items.GOLD_INGOT), 0.2f);
		GameRegistry.addSmelting(new ItemStack(TFItems.POWDER, 1, 2), new ItemStack(Items.DIAMOND), 0.4f);
		GameRegistry.addSmelting(new ItemStack(TFItems.POWDER, 1, 3), new ItemStack(Items.EMERALD), 0.4f);
		GameRegistry.addSmelting(new ItemStack(TFItems.POWDER, 1, 4), new ItemStack(Items.QUARTZ), 0.3f);
		GameRegistry.addSmelting(new ItemStack(TFItems.POWDER, 1, 6), new ItemStack(TFItems.NITER), 0.2f);
		GameRegistry.addSmelting(new ItemStack(TFItems.POWDER, 1, 7), new ItemStack(TFItems.SULFUR), 0.2f);

		GameRegistry.registerFuelHandler(new IFuelHandler()
		{
			@Override
			public int getBurnTime(ItemStack fuel)
			{
				if (fuel.getItem().equals(TFItems.COKE))
				{
					return 3200;
				}
				if (fuel.getItem().equals(TFItems.DIESEL_BOX))
				{
					return 4000;
				}
				if (fuel.getItem().equals(TFItems.WASTE_OIL))
				{
					return 1600;
				}
				if (fuel.getItem().equals(TFItems.CAN_LAVA))
				{
					return 20000;
				}
				if (fuel.getItem().equals(Item.getItemFromBlock(TFBlocks.COKE_BLOCK)))
				{
					return 28800;
				}
				return 0;
			}
		});
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event)
	{
		if(event.isWorldRunning())
		{
			TFConfig.configChange = true;
		}

		if (event.getModID().equalsIgnoreCase(Reference.MOD_ID))
		{
			TF2Core.syncConfig();
		}
	}
}
