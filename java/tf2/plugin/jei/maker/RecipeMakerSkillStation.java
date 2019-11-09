package tf2.plugin.jei.maker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapperFactory;
import mezz.jei.plugins.vanilla.crafting.ShapedOreRecipeWrapper;
import mezz.jei.plugins.vanilla.crafting.ShapedRecipesWrapper;
import mezz.jei.plugins.vanilla.crafting.ShapelessRecipeWrapper;
import mezz.jei.util.ErrorUtil;
import mezz.jei.util.Log;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tf2.recipes.guncraft.CraftingManagerSkillStation;

public class RecipeMakerSkillStation {

	public static List<IRecipe> getRecipes(IJeiHelpers jeiHelpers) {

		RecipeValidator<ShapedOreRecipe> shapedOreRecipeValidator = new RecipeValidator<>(recipe -> new ShapedOreRecipeWrapper(jeiHelpers, recipe));
		RecipeValidator<ShapedRecipes> shapedRecipeValidator = new RecipeValidator<>(recipe -> new ShapedRecipesWrapper(jeiHelpers, recipe));
		RecipeValidator<ShapelessOreRecipe> shapelessOreRecipeValidator = new RecipeValidator<>(recipe -> new ShapelessRecipeWrapper<>(jeiHelpers, recipe));
		RecipeValidator<ShapelessRecipes> shapelessRecipeValidator = new RecipeValidator<>(recipe -> new ShapelessRecipeWrapper<>(jeiHelpers, recipe));

		Map<ResourceLocation, IRecipe> recipeMap = CraftingManagerSkillStation.REGISTRY;
		List<IRecipe> validRecipes = new ArrayList();
		for(Map.Entry<ResourceLocation, IRecipe> entry : recipeMap.entrySet()) {

			IRecipe recipe = entry.getValue();
			if(recipe instanceof ShapedOreRecipe) {

				if(shapedOreRecipeValidator.isRecipeValid((ShapedOreRecipe)recipe)) {

					validRecipes.add(recipe);
				}
			}
			else if(recipe instanceof ShapedRecipes) {

				if(shapedRecipeValidator.isRecipeValid((ShapedRecipes)recipe)) {

					validRecipes.add(recipe);
				}
			}
			else if(recipe instanceof ShapelessOreRecipe) {

				if(shapelessOreRecipeValidator.isRecipeValid((ShapelessOreRecipe)recipe)) {

					validRecipes.add(recipe);
				}
			}
			else if(recipe instanceof ShapelessRecipes) {

				if(shapelessRecipeValidator.isRecipeValid((ShapelessRecipes)recipe)) {

					validRecipes.add(recipe);
				}
			}
			else {

				validRecipes.add(recipe);
			}
		}
		return validRecipes;
	}

	private static final class RecipeValidator<T extends IRecipe> {

		private static final int INVALID_COUNT = -1;
		private final IRecipeWrapperFactory<T> recipeWrapperFactory;

		public RecipeValidator(IRecipeWrapperFactory<T> recipeWrapperFactoryIn) {

			this.recipeWrapperFactory = recipeWrapperFactoryIn;
		}

		public boolean isRecipeValid(T recipe) {

			ItemStack recipeOutput = recipe.getRecipeOutput();
			if(recipeOutput == null || recipeOutput.isEmpty()) {

				String recipeInfo = getInfo(recipe);
				Log.get().error("Recipe has no output. {}", recipeInfo);
				return false;
			}

			List<Ingredient> ingredients = recipe.getIngredients();
			if(ingredients == null) {

				String recipeInfo = getInfo(recipe);
				Log.get().error("Recipe has no input Ingredients. {}", recipeInfo);
				return false;
			}

			int inputCount = getInputCount(ingredients);
			if(inputCount == INVALID_COUNT) {

				return false;
			}
			else if(inputCount > 9) {

				String recipeInfo = getInfo(recipe);
				Log.get().error("Recipe has too many inputs. {}", recipeInfo);
				return false;
			}
			else if(inputCount == 0) {

				String recipeInfo = getInfo(recipe);
				Log.get().error("Recipe has no inputs. {}", recipeInfo);
				return false;
			}
			return true;
		}

		private String getInfo(T recipe) {

			IRecipeWrapper recipeWrapper = this.recipeWrapperFactory.getRecipeWrapper(recipe);
			return ErrorUtil.getInfoFromRecipe(recipe, recipeWrapper);
		}

		private int getInputCount(List<Ingredient> ingredients) {

			int inputCount = 0;
			for(Ingredient ingredient : ingredients) {

				ItemStack[] input = ingredient.getMatchingStacks();
				if(input == null) {

					return INVALID_COUNT;
				}
				else if(ingredient instanceof OreIngredient && input.length == 0) {

					return INVALID_COUNT;
				}
				else {

					inputCount++;
				}
			}
			return inputCount;
		}
	}
}
