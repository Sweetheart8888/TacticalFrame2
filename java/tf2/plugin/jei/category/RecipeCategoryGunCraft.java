package tf2.plugin.jei.category;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.ICraftingGridHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICraftingRecipeWrapper;
import mezz.jei.api.recipe.wrapper.ICustomCraftingRecipeWrapper;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import mezz.jei.config.Constants;
import mezz.jei.startup.ForgeModIdHelper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import tf2.plugin.jei.PluginJEI;
import tf2.util.Reference;

public class RecipeCategoryGunCraft implements IRecipeCategory<IRecipeWrapper> {

	protected static final int outputSlot = 0;
	protected static final int inputSlot = 1;

	protected final ResourceLocation backgroundLocation = Constants.RECIPE_GUI_VANILLA;
	protected final IDrawable background;
	protected final String localizedName;
	protected final ICraftingGridHelper craftingGridHelper;

	public RecipeCategoryGunCraft(IGuiHelper guiHelper) {

		this.background = guiHelper.createDrawable(this.backgroundLocation, 0, 60, 116, 54);
		this.localizedName = I18n.format("gui.guncraft", new Object[0]);
		this.craftingGridHelper = guiHelper.createCraftingGridHelper(inputSlot, outputSlot);
	}

	@Override
	public String getUid() {

		return PluginJEI.UID_GunCraft;
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

		guiItemStackGroup.init(outputSlot, false, 94, 18);

		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {

				guiItemStackGroup.init(inputSlot + j + i * 3, true, j * 18, i * 18);
			}
		}

		if(recipeWrapper instanceof ICustomCraftingRecipeWrapper) {

			ICustomCraftingRecipeWrapper customCraftingRecipeWrapper = (ICustomCraftingRecipeWrapper)recipeWrapper;
			customCraftingRecipeWrapper.setRecipe(recipeLayout, ingredients);
			return;
		}

		List<List<ItemStack>> inputs = ingredients.getInputs(VanillaTypes.ITEM);
		List<List<ItemStack>> outputs = ingredients.getOutputs(VanillaTypes.ITEM);

		if(recipeWrapper instanceof IShapedCraftingRecipeWrapper) {

			IShapedCraftingRecipeWrapper shapedCraftingRecipeWrapper = (IShapedCraftingRecipeWrapper)recipeWrapper;
			craftingGridHelper.setInputs(guiItemStackGroup, inputs, shapedCraftingRecipeWrapper.getWidth(), shapedCraftingRecipeWrapper.getHeight());
		}
		else {

			craftingGridHelper.setInputs(guiItemStackGroup, inputs);
			recipeLayout.setShapeless();
		}

		guiItemStackGroup.set(outputSlot, outputs.get(0));

		if(recipeWrapper instanceof ICraftingRecipeWrapper) {

			ICraftingRecipeWrapper craftingRecipeWrapper = (ICraftingRecipeWrapper)recipeWrapper;
			ResourceLocation registryName = craftingRecipeWrapper.getRegistryName();
			if(registryName != null) {

				guiItemStackGroup.addTooltipCallback((slotIndex, input, ingredient, tooltip) -> {

					if(slotIndex == outputSlot) {

						String recipeModId = registryName.getResourceDomain();
						boolean modIdDifferent = false;
						ResourceLocation itemRegistryName = ingredient.getItem().getRegistryName();
						if(itemRegistryName != null) {

							String itemModId = itemRegistryName.getResourceDomain();
							modIdDifferent = !recipeModId.equals(itemModId);
						}
						if(modIdDifferent) {

							String modName = ForgeModIdHelper.getInstance().getFormattedModNameForModId(recipeModId);
							tooltip.add(TextFormatting.GRAY + Translator.translateToLocalFormatted("jei.tooltip.recipe.by", modName));
						}

						boolean showAdvanced = Minecraft.getMinecraft().gameSettings.advancedItemTooltips || GuiScreen.isShiftKeyDown();
						if(showAdvanced) {

							tooltip.add(TextFormatting.DARK_GRAY + Translator.translateToLocalFormatted("jei.tooltip.recipe.id", registryName.toString()));
						}
					}
				});
			}
		}
	}
}
