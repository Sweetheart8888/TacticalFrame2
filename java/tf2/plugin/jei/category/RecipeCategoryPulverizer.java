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

public class RecipeCategoryPulverizer<RecipeWrapperPulverizer> implements IRecipeCategory {

	protected static final int inputSlot = 0;
	protected static final int fuelSlot = 1;
	protected static final int outputSlot = 2;

	protected final ResourceLocation backgroundLocation = new ResourceLocation(Reference.MOD_ID, "textures/gui/container/pulverizer.png");
	protected final IDrawable flame;
	protected final IDrawable arrow;
	protected final IDrawable background;
	protected final String localizedName;

	public RecipeCategoryPulverizer(IGuiHelper guiHelper) {

		IDrawableStatic flameDrawable = guiHelper.createDrawable(this.backgroundLocation, 176, 0, 24, 14);
		this.flame = guiHelper.createAnimatedDrawable(flameDrawable, 300, IDrawableAnimated.StartDirection.TOP, true);
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(this.backgroundLocation, 176, 14, 27, 19);
		this.arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);

		this.background = guiHelper.createDrawable(this.backgroundLocation, 43, 16, 94, 54);
		this.localizedName = I18n.format("gui.pulverizer", new Object[0]);
	}

	@Override
	public String getUid() {

		return PluginJEI.UID_Pulverizer;
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

		guiItemStackGroup.init(inputSlot, true, 3, 0);
		guiItemStackGroup.init(outputSlot, false, 72, 18);

		recipeWrapper.getIngredients(ingredients);

		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		guiItemStackGroup.set(inputSlot, inputs.get(0));
		List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);
		guiItemStackGroup.set(outputSlot, outputs.get(0));
	}

	@Override
	public void drawExtras(Minecraft minecraft) {

		this.flame.draw(minecraft, 0, 20);
		this.arrow.draw(minecraft, 32, 18);
	}
}
