package tf2.plugin.jei.maker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import tf2.plugin.jei.wrapper.RecipeWrapperCokeOven;
import tf2.recipes.CokeOvenRecipes;

public class RecipeMakerCokeOven {

	public static List<RecipeWrapperCokeOven> getRecipes(IJeiHelpers jeiHelpers) {

		IStackHelper stackHelper = jeiHelpers.getStackHelper();
		CokeOvenRecipes recipes = CokeOvenRecipes.instance();
		Map<ItemStack, ItemStack> recipeMap = recipes.getSmeltingList();
		List<RecipeWrapperCokeOven> recipeList = new ArrayList();

		for(Map.Entry<ItemStack, ItemStack> entry : recipeMap.entrySet()) {

			ItemStack input = entry.getKey();
			ItemStack output = entry.getValue();
			List<ItemStack> inputs = stackHelper.getSubtypes(input);
			RecipeWrapperCokeOven recipe = new RecipeWrapperCokeOven(inputs, output);
			recipeList.add(recipe);
		}

		return recipeList;
	}
}
