package tf2.plugin.jei.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import tf2.TFItems;

public class RecipeWrapperCokeOven implements IRecipeWrapper {

	private final List<List<ItemStack>> inputs;
	private final List<ItemStack> outputs;

	public RecipeWrapperCokeOven(List<ItemStack> inputs, ItemStack output) {

		this.inputs = Collections.singletonList(inputs);
		this.outputs = new ArrayList<>();
		this.outputs.add(0, output);
		this.outputs.add(1, new ItemStack(TFItems.COALTAR));
		this.outputs.add(2, new ItemStack(TFItems.WASTE_OIL));
	}

	@Override
	public void getIngredients(IIngredients ingredients) {

		ingredients.setInputLists(VanillaTypes.ITEM, this.inputs);
		ingredients.setOutputs(VanillaTypes.ITEM, this.outputs);
	}

//	@Override
//	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
//
//		CokeOvenRecipes recipes = CokeOvenRecipes.instance();
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
