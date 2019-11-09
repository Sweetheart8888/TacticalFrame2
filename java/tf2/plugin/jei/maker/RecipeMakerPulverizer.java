package tf2.plugin.jei.maker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tf2.plugin.jei.wrapper.RecipeWrapperPulverizer;
import tf2.recipes.PulverizerRecipes;

public class RecipeMakerPulverizer {

	public static List<RecipeWrapperPulverizer> getRecipes(IJeiHelpers jeiHelpers) {

		IStackHelper stackHelper = jeiHelpers.getStackHelper();
		PulverizerRecipes recipes = PulverizerRecipes.instance();
		Map<ItemStack, ItemStack> recipeMap = recipes.getSmeltingList();
		List<RecipeWrapperPulverizer> recipeList = new ArrayList();

		for(Map.Entry<ItemStack, ItemStack> entry : recipeMap.entrySet()) {

			ItemStack input = entry.getKey();
			ItemStack output = entry.getValue();
			List<ItemStack> inputs = stackHelper.getSubtypes(input);
			RecipeWrapperPulverizer recipe = new RecipeWrapperPulverizer(inputs, output);
			recipeList.add(recipe);
		}

		Map<String, ItemStack> recipeOreMap = recipes.getSmeltingOreList();

		for(Map.Entry<String, ItemStack> entry : recipeOreMap.entrySet()) {

			List<ItemStack> candidate = OreDictionary.getOres(entry.getKey());
			if(!candidate.isEmpty()) {

				for(ItemStack itemStack : candidate) {

					if(!itemStack.isEmpty()) {

						ItemStack output = entry.getValue();
						List<ItemStack> inputs = stackHelper.getSubtypes(itemStack);
						RecipeWrapperPulverizer recipe = new RecipeWrapperPulverizer(inputs, output);
						recipeList.add(recipe);
					}
				}
			}
		}

		return recipeList;
	}
}
