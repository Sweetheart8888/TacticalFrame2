package tf2.plugin.jei.maker;

import java.util.ArrayList;
import java.util.List;

import it.unimi.dsi.fastutil.ints.Int2BooleanArrayMap;
import it.unimi.dsi.fastutil.ints.Int2BooleanMap;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IStackHelper;
import mezz.jei.util.ErrorUtil;
import mezz.jei.util.Log;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;
import tf2.plugin.jei.wrapper.RecipeWrapperFuelBioGenerator;
import tf2.tile.tileentity.TileEntityBioGenerator;

public class RecipeMakerFuelBioGenerator {

	public static List<RecipeWrapperFuelBioGenerator> getFuel(IIngredientRegistry ingredientRegistry, IJeiHelpers jeiHelpers) {
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		IStackHelper stackHelper = jeiHelpers.getStackHelper();
		List<ItemStack> fuelStacks = getFuels(ingredientRegistry);
		List<RecipeWrapperFuelBioGenerator> fuelRecipes = new ArrayList<>(fuelStacks.size());
		for(ItemStack fuelStack : fuelStacks) {
			int burnTime = TileEntityBioGenerator.getItemBurnTime(fuelStack);
			List<ItemStack> subtypes = stackHelper.getSubtypes(fuelStack);
			List<ItemStack> fuels = new ArrayList<>();
			for(ItemStack subtype : subtypes) {
				if(TileEntityBioGenerator.getItemBurnTime(subtype) == burnTime) {
					fuels.add(subtype);
				}
			}
			if(fuels.isEmpty()) {
				fuels.add(fuelStack);
			}
			Int2BooleanMap oreIdsHaveRecipes = new Int2BooleanArrayMap();
			if(fuels.size() <= 1) {
				int[] oreIDs = OreDictionary.getOreIDs(fuelStack);
				boolean hasOreRecipe = false;
				for(int oreId : oreIDs) {
					if(!oreIdsHaveRecipes.containsKey(oreId)) {
						String oreName = OreDictionary.getOreName(oreId);
						List<ItemStack> ores = stackHelper.getAllSubtypes(OreDictionary.getOres(oreName));
						if(ores.size() > 1 && ores.stream().allMatch(itemStack -> TileEntityBioGenerator.getItemBurnTime(itemStack) == burnTime)) {
							oreIdsHaveRecipes.put(oreId, true);
							fuelRecipes.add(new RecipeWrapperFuelBioGenerator(guiHelper, ores, burnTime));
						}
						else {
							oreIdsHaveRecipes.put(oreId, false);
						}
					}
					hasOreRecipe |= oreIdsHaveRecipes.get(oreId);
				}
				if(!hasOreRecipe) {
					fuelRecipes.add(new RecipeWrapperFuelBioGenerator(guiHelper, fuels, burnTime));
				}
			}
			else {
				fuelRecipes.add(new RecipeWrapperFuelBioGenerator(guiHelper, fuels, burnTime));
			}
		}
		return fuelRecipes;
	}

	public static List<ItemStack> getFuels(IIngredientRegistry ingredientRegistry) {
		List<ItemStack> fuels = NonNullList.create();
		for(ItemStack itemStack : ingredientRegistry.getAllIngredients(VanillaTypes.ITEM)) {
			try {
				if(TileEntityBioGenerator.isItemFuel(itemStack)) {
					fuels.add(itemStack);
				}
			}catch(RuntimeException | LinkageError e) {
				String itemStackInfo = ErrorUtil.getItemStackInfo(itemStack);
				Log.get().error("Failed to check if item is fuel {}.", itemStackInfo, e);
			}
		}
		return fuels;
	}
}
