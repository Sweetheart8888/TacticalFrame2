package tf2.plugin.jei.category;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.util.ResourceLocation;
import tf2.plugin.jei.PluginJEI;
import tf2.util.Reference;

public class RecipeCategoryFuelBioGenerator<RecipeWrapperFuelBioGenerator> implements IRecipeCategory {

	protected static final int fuelSlot = 1;

	private final IDrawableStatic background;
	private final IDrawableStatic flaskTransparentBackground;
	private final String localizedName;

	protected final ResourceLocation backgroundLocation = new ResourceLocation(Reference.MOD_ID, "textures/gui/container/bio.png");

	public RecipeCategoryFuelBioGenerator(IGuiHelper guiHelper) {
		this.background = guiHelper.drawableBuilder(this.backgroundLocation, 25, 27, 18, 45).addPadding(0, 0, 0, 44).build(); // 25, 14, 86, 58
		this.flaskTransparentBackground = guiHelper.createDrawable(this.backgroundLocation, 176, 0, 18, 23);
		this.localizedName = Translator.translateToLocal("gui.biogenerator.fuel");
	}

	@Override
	public String getUid() {
		return PluginJEI.UID_FuelBioGenerator;
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

		guiItemStackGroup.init(fuelSlot, true, 0, 27);
		guiItemStackGroup.set(ingredients);
	}
}
