package tf2.plugin.jei.maker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import tf2.plugin.jei.wrapper.RecipeWrapperBioGenerator;
import tf2.recipes.BioGeneratorRecipes;

public class RecipeMakerBioGenerator {

	public static List<RecipeWrapperBioGenerator> getRecipes(IJeiHelpers jeiHelpers) {

		IStackHelper stackHelper = jeiHelpers.getStackHelper();
		BioGeneratorRecipes recipes = BioGeneratorRecipes.instance();
		Map<ItemStack, ItemStack> recipeMap = recipes.getSmeltingList();
		List<RecipeWrapperBioGenerator> recipeList = new ArrayList();

		for(Map.Entry<ItemStack, ItemStack> entry : recipeMap.entrySet()) {

			ItemStack input = entry.getKey();
			ItemStack output = entry.getValue();
			List<ItemStack> inputs = stackHelper.getSubtypes(input);
			RecipeWrapperBioGenerator recipe = new RecipeWrapperBioGenerator(inputs, output);
			recipeList.add(recipe);
		}

		return recipeList;
	}
}
