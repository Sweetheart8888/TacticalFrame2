package tf2.plugin.jei.wrapper;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class RecipeWrapperFuelBioGenerator implements IRecipeWrapper {

	private final List<List<ItemStack>> inputs;
	private final String bioLiquidAmountString;
	private final IDrawableAnimated flask;

	public RecipeWrapperFuelBioGenerator(IGuiHelper guiHelper, List<ItemStack> input, int burnTime) {
		Preconditions.checkArgument(burnTime > 0, "burn time must be greater than 0");
		List<ItemStack> inputList = new ArrayList<>(input);
		this.inputs = Collections.singletonList(inputList);

		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(5);
		String bioLiquidAmount = numberFormat.format(burnTime);
		this.bioLiquidAmountString = Translator.translateToLocalFormatted("%s mb", bioLiquidAmount);

		this.flask = guiHelper.drawableBuilder(new ResourceLocation("tf2:textures/gui/container/bio.png"), 176, 0, 18, 23).buildAnimated(burnTime, IDrawableAnimated.StartDirection.TOP, true);
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(VanillaTypes.ITEM, this.inputs);
	}

	@Override
	public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
		this.flask.draw(minecraft, 0, 0);
		minecraft.fontRenderer.drawString(this.bioLiquidAmountString, 25, 13, Color.GRAY.getRGB());
	}
}
