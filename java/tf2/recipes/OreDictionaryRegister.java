package tf2.recipes;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tf2.TFBlocks;
import tf2.TFItems;

public class OreDictionaryRegister
{
	public static void load() {
		loadOres();
	}

	static void loadOres()
	{
		OreDictionary.registerOre("fuelCoke", new ItemStack(TFItems.COKE));
		OreDictionary.registerOre("dustIron", new ItemStack(TFItems.POWDER_IRON));
		OreDictionary.registerOre("dustGold", new ItemStack(TFItems.POWDER, 1, 1));
		OreDictionary.registerOre("dustCoal", new ItemStack(TFItems.POWDER, 1, 0));
		OreDictionary.registerOre("dustSulfur", new ItemStack(TFItems.POWDER, 1, 7));
		OreDictionary.registerOre("dustNiter", new ItemStack(TFItems.POWDER, 1, 6));
		OreDictionary.registerOre("dustSaltpeter", new ItemStack(TFItems.POWDER, 1, 6));
		OreDictionary.registerOre("dustDiamond", new ItemStack(TFItems.POWDER, 1, 2));
		OreDictionary.registerOre("dustEmerald", new ItemStack(TFItems.POWDER, 1, 3));
//		OreDictionary.registerOre("foodFlour", new ItemStack(TFItems.POWDER, 1, 0));
//		OreDictionary.registerOre("dustFlour", new ItemStack(TFItems.POWDER, 1, 0));
//		OreDictionary.registerOre("dustWheat", new ItemStack(TFItems.POWDER, 1, 0));
//		OreDictionary.registerOre("foodSalt", new ItemStack(TFItems.POWDER, 1, 10));
//		OreDictionary.registerOre("dustSalt", new ItemStack(TFItems.POWDER, 1, 10));
		OreDictionary.registerOre("dustQuartz", new ItemStack(TFItems.POWDER, 1, 4));

		OreDictionary.registerOre("oreIron", new ItemStack(TFBlocks.ORE_MAGNETITE));
		OreDictionary.registerOre("oreNiter", new ItemStack(TFBlocks.ORE_NITER));
		OreDictionary.registerOre("oreSaltpeter", new ItemStack(TFBlocks.ORE_NITER));
		OreDictionary.registerOre("oreSulfur", new ItemStack(TFBlocks.ORE_SULFUR));
		OreDictionary.registerOre("oreMagnetite", new ItemStack(TFBlocks.ORE_MAGNETITE));
		OreDictionary.registerOre("gemNiter", new ItemStack(TFItems.NITER));
		OreDictionary.registerOre("gemSulfur", new ItemStack(TFItems.SULFUR));
		OreDictionary.registerOre("itemRubber", new ItemStack(TFItems.RUBBER));
		OreDictionary.registerOre("bucketMilk", new ItemStack(TFItems.CAN_MILK));
		OreDictionary.registerOre("bucketMilk", new ItemStack(Items.MILK_BUCKET));
		OreDictionary.registerOre("bucketLava", new ItemStack(TFItems.CAN_LAVA));
		OreDictionary.registerOre("bucketLava", new ItemStack(Items.LAVA_BUCKET));
		OreDictionary.registerOre("bucketWater", new ItemStack(TFItems.CAN_WATER));
		OreDictionary.registerOre("bucketWater", new ItemStack(Items.WATER_BUCKET));
		OreDictionary.registerOre("gearIron", new ItemStack(TFItems.GEAR_IRON));
//		OreDictionary.registerOre("gearGold", new ItemStack(TFItems.GEAR_GOLD));
	}

}
