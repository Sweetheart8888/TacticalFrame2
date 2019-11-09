package tf2.plugin.jei.maker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import tf2.plugin.jei.wrapper.RecipeWrapperSynthesizer;
import tf2.recipes.SynthesizerRecipes;

public class RecipeMakerSynthesizer {

	public static List<RecipeWrapperSynthesizer> getRecipes(IJeiHelpers jeiHelpers) {

		IStackHelper stackHelper = jeiHelpers.getStackHelper();
		SynthesizerRecipes recipes = SynthesizerRecipes.instance();
		Map<ItemStack, ItemStack> recipeMap = recipes.getSmeltingList();
		List<RecipeWrapperSynthesizer> recipeList = new ArrayList();

		for(Map.Entry<ItemStack, ItemStack> entry : recipeMap.entrySet()) {

			ItemStack input = entry.getKey();
			ItemStack output = entry.getValue();
			List<ItemStack> inputs = stackHelper.getSubtypes(input);
			RecipeWrapperSynthesizer recipe = new RecipeWrapperSynthesizer(inputs, output);
			recipeList.add(recipe);
		}

		return recipeList;
	}
}
