package tf2.plugin.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientRegistry;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import mezz.jei.api.recipe.transfer.IRecipeTransferRegistry;
import mezz.jei.plugins.vanilla.crafting.ShapedOreRecipeWrapper;
import mezz.jei.plugins.vanilla.crafting.ShapedRecipesWrapper;
import mezz.jei.plugins.vanilla.crafting.ShapelessRecipeWrapper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tf2.TFBlocks;
import tf2.plugin.jei.category.RecipeCategoryBioGenerator;
import tf2.plugin.jei.category.RecipeCategoryCokeOven;
import tf2.plugin.jei.category.RecipeCategoryFuelBioGenerator;
import tf2.plugin.jei.category.RecipeCategoryGunCraft;
import tf2.plugin.jei.category.RecipeCategoryMachineStation;
import tf2.plugin.jei.category.RecipeCategoryPulverizer;
import tf2.plugin.jei.category.RecipeCategorySkillStation;
import tf2.plugin.jei.category.RecipeCategorySynthesizer;
import tf2.plugin.jei.maker.RecipeMakerBioGenerator;
import tf2.plugin.jei.maker.RecipeMakerCokeOven;
import tf2.plugin.jei.maker.RecipeMakerFuelBioGenerator;
import tf2.plugin.jei.maker.RecipeMakerGunCraft;
import tf2.plugin.jei.maker.RecipeMakerMachineStation;
import tf2.plugin.jei.maker.RecipeMakerPulverizer;
import tf2.plugin.jei.maker.RecipeMakerSkillStation;
import tf2.plugin.jei.maker.RecipeMakerSynthesizer;
import tf2.tile.container.ContainerBioGenerator;
import tf2.tile.container.ContainerCokeOven;
import tf2.tile.container.ContainerGunCraft;
import tf2.tile.container.ContainerMachineStation;
import tf2.tile.container.ContainerPulverizer;
import tf2.tile.container.ContainerSkillStation;
import tf2.tile.container.ContainerSynthesizer;
import tf2.tile.gui.GuiBioGenerator;
import tf2.tile.gui.GuiCokeOven;
import tf2.tile.gui.GuiGunCraft;
import tf2.tile.gui.GuiMachineStation;
import tf2.tile.gui.GuiPulverizer;
import tf2.tile.gui.GuiSkillStation;
import tf2.tile.gui.GuiSynthesizer;

@JEIPlugin
public class PluginJEI implements IModPlugin {

	public static String UID_GunCraft = "tfmod.guncraft";
	public static String UID_SKillStation = "tfmod.skillstation";
	public static String UID_MachineStation = "tfmod.machinestation";
	public static String UID_BioGenerator = "tfmod.biogenerator";
	public static String UID_FuelBioGenerator = "tfmod.fuelbiogenerator";
	public static String UID_CokeOven = "tfmod.cokeoven";
	public static String UID_Synthesizer = "tfmod.synthesizer";
	public static String UID_Pulverizer = "tfmod.pulverizer";

	@Override
	public void registerCategories(IRecipeCategoryRegistration registry) {

		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registry.addRecipeCategories(new RecipeCategoryGunCraft(guiHelper));
		registry.addRecipeCategories(new RecipeCategorySkillStation(guiHelper));
		registry.addRecipeCategories(new RecipeCategoryMachineStation(guiHelper));
		registry.addRecipeCategories(new RecipeCategoryBioGenerator(guiHelper));
		registry.addRecipeCategories(new RecipeCategoryFuelBioGenerator(guiHelper));
		registry.addRecipeCategories(new RecipeCategoryCokeOven(guiHelper));
		registry.addRecipeCategories(new RecipeCategorySynthesizer(guiHelper));
		registry.addRecipeCategories(new RecipeCategoryPulverizer(guiHelper));
	}

	@Override
	public void register(IModRegistry registry) {

		IIngredientRegistry ingredientRegistry = registry.getIngredientRegistry();
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
		IRecipeTransferRegistry recipeTransferRegistry = registry.getRecipeTransferRegistry();

		registry.addRecipes(RecipeMakerGunCraft.getRecipes(jeiHelpers), UID_GunCraft);
		registry.handleRecipes(ShapedOreRecipe.class, recipe -> new ShapedOreRecipeWrapper(jeiHelpers, recipe), UID_GunCraft);
		registry.handleRecipes(ShapedRecipes.class, recipe -> new ShapedRecipesWrapper(jeiHelpers, recipe), UID_GunCraft);
		registry.handleRecipes(ShapelessOreRecipe.class, recipe -> new ShapelessRecipeWrapper<>(jeiHelpers, recipe), UID_GunCraft);
		registry.handleRecipes(ShapelessRecipes.class, recipe -> new ShapelessRecipeWrapper<>(jeiHelpers, recipe), UID_GunCraft);

		registry.addRecipes(RecipeMakerSkillStation.getRecipes(jeiHelpers), UID_SKillStation);
		registry.handleRecipes(ShapedOreRecipe.class, recipe -> new ShapedOreRecipeWrapper(jeiHelpers, recipe), UID_SKillStation);
		registry.handleRecipes(ShapedRecipes.class, recipe -> new ShapedRecipesWrapper(jeiHelpers, recipe), UID_SKillStation);
		registry.handleRecipes(ShapelessOreRecipe.class, recipe -> new ShapelessRecipeWrapper<>(jeiHelpers, recipe), UID_SKillStation);
		registry.handleRecipes(ShapelessRecipes.class, recipe -> new ShapelessRecipeWrapper<>(jeiHelpers, recipe), UID_SKillStation);

		registry.addRecipes(RecipeMakerMachineStation.getRecipes(jeiHelpers), UID_MachineStation);
		registry.handleRecipes(ShapedOreRecipe.class, recipe -> new ShapedOreRecipeWrapper(jeiHelpers, recipe), UID_MachineStation);
		registry.handleRecipes(ShapedRecipes.class, recipe -> new ShapedRecipesWrapper(jeiHelpers, recipe), UID_MachineStation);
		registry.handleRecipes(ShapelessOreRecipe.class, recipe -> new ShapelessRecipeWrapper<>(jeiHelpers, recipe), UID_MachineStation);
		registry.handleRecipes(ShapelessRecipes.class, recipe -> new ShapelessRecipeWrapper<>(jeiHelpers, recipe), UID_MachineStation);

		registry.addRecipes(RecipeMakerBioGenerator.getRecipes(jeiHelpers), UID_BioGenerator);
		registry.addRecipes(RecipeMakerFuelBioGenerator.getFuel(ingredientRegistry, jeiHelpers), UID_FuelBioGenerator);
		registry.addRecipes(RecipeMakerCokeOven.getRecipes(jeiHelpers), UID_CokeOven);
		registry.addRecipes(RecipeMakerSynthesizer.getRecipes(jeiHelpers), UID_Synthesizer);
		registry.addRecipes(RecipeMakerPulverizer.getRecipes(jeiHelpers), UID_Pulverizer);

		registry.addRecipeClickArea(GuiGunCraft.class, 201, 36, 18, 18, UID_GunCraft);
		registry.addRecipeClickArea(GuiSkillStation.class, 74, 58, 17, 14, UID_SKillStation);
		registry.addRecipeClickArea(GuiMachineStation.class, 201, 36, 18, 18, UID_MachineStation);
		registry.addRecipeClickArea(GuiBioGenerator.class, 25, 14, 54, 36, UID_BioGenerator, UID_FuelBioGenerator);
		registry.addRecipeClickArea(GuiCokeOven.class, 91, 32, 28, 20, UID_CokeOven);
		registry.addRecipeClickArea(GuiSynthesizer.class, 90, 34, 24, 18, UID_Synthesizer);
		registry.addRecipeClickArea(GuiPulverizer.class, 75, 34, 27, 19, UID_Pulverizer);

		recipeTransferRegistry.addRecipeTransferHandler(ContainerGunCraft.class, UID_GunCraft, 12, 9, 21, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerSkillStation.class, UID_SKillStation, 15, 9, 24, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerMachineStation.class, UID_MachineStation, 9, 9, 18, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerBioGenerator.class, UID_BioGenerator, 0, 1, 5, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerBioGenerator.class, UID_FuelBioGenerator, 2, 1, 5, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerCokeOven.class, UID_CokeOven, 0, 8, 18, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerSynthesizer.class, UID_Synthesizer, 1, 1, 4, 36);
		recipeTransferRegistry.addRecipeTransferHandler(ContainerPulverizer.class, UID_Pulverizer, 0, 1, 3, 36);

		registry.addRecipeCatalyst(new ItemStack(TFBlocks.GUNCRAFT), UID_GunCraft);
		registry.addRecipeCatalyst(new ItemStack(TFBlocks.SKILLSTATION), UID_SKillStation);
		registry.addRecipeCatalyst(new ItemStack(TFBlocks.MACHINESTATION), UID_MachineStation);
		registry.addRecipeCatalyst(new ItemStack(TFBlocks.BIO_GENERATOR), UID_BioGenerator);
		registry.addRecipeCatalyst(new ItemStack(TFBlocks.BIO_GENERATOR), UID_FuelBioGenerator);
		registry.addRecipeCatalyst(new ItemStack(TFBlocks.COKE_OVEN), UID_CokeOven);
		registry.addRecipeCatalyst(new ItemStack(TFBlocks.SYNTHESIZER), UID_Synthesizer);
		registry.addRecipeCatalyst(new ItemStack(TFBlocks.PULVERIZER), UID_Pulverizer);
	}
}
