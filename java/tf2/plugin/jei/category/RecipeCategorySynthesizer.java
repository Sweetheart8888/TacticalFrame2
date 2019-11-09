package tf2.plugin.jei.category;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import tf2.plugin.jei.PluginJEI;
import tf2.util.Reference;

public class RecipeCategorySynthesizer<RecipeWrapperSynthesizer> implements IRecipeCategory {

	protected static final int inputSlot = 0;
	protected static final int fuelSlot = 1;
	protected static final int outputSlot = 2;
	protected static final int outputSlot_reimetal = 3;

	protected final ResourceLocation backgroundLocation = new ResourceLocation(Reference.MOD_ID, "textures/gui/container/synthesizer.png");
	protected final IDrawable flame;
	protected final IDrawable arrow;
	protected final IDrawable background;
	protected final String localizedName;

	public RecipeCategorySynthesizer(IGuiHelper guiHelper) {

		IDrawableStatic flameDrawable = guiHelper.createDrawable(this.backgroundLocation, 176, 0, 14, 14);
		this.flame = guiHelper.createAnimatedDrawable(flameDrawable, 300, IDrawableAnimated.StartDirection.TOP, true);
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(this.backgroundLocation, 176, 14, 24, 17);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);

		this.background = guiHelper.createDrawable(this.backgroundLocation, 36, 16, 112, 54);
		this.localizedName = I18n.format("gui.synthesizer", new Object[0]);
	}

	@Override
	public String getUid() {

		return PluginJEI.UID_Synthesizer;
	}

	@Override
	public String getTitle() {

		return this.localizedName;
	}

	@Override
	public String getModName() {

		return Reference.NAME;
	}

	@Override
	public IDrawable getBackground() {

		return this.background;
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {

		IGuiItemStackGroup guiItemStackGroup = recipeLayout.getItemStacks();

		guiItemStackGroup.init(inputSlot, true, 24, 0);
		guiItemStackGroup.init(outputSlot, false, 90, 18);

		recipeWrapper.getIngredients(ingredients);

		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		guiItemStackGroup.set(inputSlot, inputs.get(0));
		List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
		guiItemStackGroup.set(outputSlot, outputs.get(0));

		guiItemStackGroup.init(outputSlot_reimetal, false, 0, 0);

		guiItemStackGroup.set(outputSlot_reimetal, outputs.get(1));

	}

	@Override
	public void drawExtras(Minecraft minecraft) {

		this.flame.draw(minecraft, 13, 20);
		this.arrow.draw(minecraft, 54, 19);
	}
}
