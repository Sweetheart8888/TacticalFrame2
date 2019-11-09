package tf2.plugin.jei.wrapper;

import java.util.Collections;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

public class RecipeWrapperPulverizer implements IRecipeWrapper {

	private final List<List<ItemStack>> inputs;
	private final ItemStack output;

	public RecipeWrapperPulverizer(List<ItemStack> inputs, ItemStack output) {

		this.inputs = Collections.singletonList(inputs);
		this.output = output;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {

		ingredients.setInputLists(VanillaTypes.ITEM, this.inputs);
		ingredients.setOutput(VanillaTypes.ITEM, this.output);
	}

//	@Override
//	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
//
//		PulverizerRecipes recipes = PulverizerRecipes.instance();
//		float experience = recipes.getSmeltingExperience(this.output);
//		if(experience > 0.0F) {
//
//			String experienceString = Translator.translateToLocalFormatted("gui.jei.category.smelting.experience", experience);
//			FontRenderer fontRenderer = minecraft.fontRenderer;
//			int stringWidth = fontRenderer.getStringWidth(experienceString);
//			fontRenderer.drawString(experienceString, recipeWidth - stringWidth, 0, Color.gray.getRGB());
//		}
//	}
}
