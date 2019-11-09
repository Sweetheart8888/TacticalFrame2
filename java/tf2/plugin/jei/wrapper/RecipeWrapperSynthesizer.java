package tf2.plugin.jei.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import tf2.TFItems;

public class RecipeWrapperSynthesizer implements IRecipeWrapper {

	private final List<List<ItemStack>> inputs;
	private final List<ItemStack> outputs;

	public RecipeWrapperSynthesizer(List<ItemStack> inputs, ItemStack output) {

		this.inputs = Collections.singletonList(inputs);
		this.outputs = new ArrayList<>();
		this.outputs.add(0, output);
		this.outputs.add(1, new ItemStack(TFItems.REIMETAL));
	}

	@Override
	public void getIngredients(IIngredients ingredients) {

		ingredients.setInputLists(VanillaTypes.ITEM, this.inputs);
		ingredients.setOutputs(VanillaTypes.ITEM, this.outputs);
	}
}
