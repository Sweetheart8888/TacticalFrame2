package tf2.recipes;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import tf2.TFBlocks;
import tf2.TFItems;

public class PulverizerRecipes
{
	private static final PulverizerRecipes SMELTING_BASE = new PulverizerRecipes();
	/** The list of smelting results. */
	private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack> newHashMap();
	/** A list which contains how many experience points each recipe output will give. */
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float> newHashMap();

	private final Map<String, ItemStack> smeltingOreList = Maps.<String, ItemStack> newHashMap();
	private final Map<String, Float> experienceOreList = Maps.<String, Float> newHashMap();

	/**
	 * Returns an instance of FurnaceRecipes.
	 */
	public static PulverizerRecipes instance()
	{
		return SMELTING_BASE;
	}

	private PulverizerRecipes()
	{
		        this.addSmelting(Items.BLAZE_ROD, new ItemStack(Items.BLAZE_POWDER, 6), 0.5F);
		        this.addSmelting(Items.BONE, new ItemStack(Items.DYE, 9, 15), 0.3F);
		        this.addSmelting(Items.REEDS, new ItemStack(Items.SUGAR, 3), 0.1F);
		        this.addSmeltingRecipeForBlock(Blocks.GRAVEL, new ItemStack(Items.FLINT, 3), 0.5F);
		        this.addSmeltingRecipeForBlock(Blocks.WOOL, new ItemStack(Items.STRING, 4), 0.5F);
		        this.addSmeltingRecipeForBlock(Blocks.PUMPKIN, new ItemStack(Items.PUMPKIN_SEEDS, 8), 0.1F);
		        this.addSmelting(Items.MELON, new ItemStack(Items.MELON_SEEDS, 3), 0.1F);
		        this.addSmeltingRecipeForBlock(Blocks.MELON_BLOCK, new ItemStack(Items.MELON, 9), 0.2F);

		        this.addSmeltingRecipeForBlock(TFBlocks.ORE_PYRODITE, new ItemStack(TFItems.FLAMLIGHT), 0.9F);

		        this.addSmelting(TFItems.NITER, new ItemStack(TFItems.POWDER, 1, 6), 0.3F);
		        this.addSmelting(TFItems.SULFUR, new ItemStack(TFItems.POWDER, 1, 7), 0.3F);
		        this.addSmelting(Items.QUARTZ, new ItemStack(TFItems.POWDER, 1, 4), 1.0F);
		        this.addSmelting(Items.ENDER_PEARL, new ItemStack(TFItems.POWDER, 1, 5), 1.0F);
		        this.addSmelting(Items.IRON_INGOT, new ItemStack(TFItems.POWDER_IRON, 1), 0.5F);
		        this.addSmelting(Items.GOLD_INGOT, new ItemStack(TFItems.POWDER, 1, 1), 0.5F);
		        this.addSmelting(Items.EMERALD, new ItemStack(TFItems.POWDER, 1, 3), 0.5F);
		        this.addSmelting(Items.DIAMOND, new ItemStack(TFItems.POWDER, 1, 2), 0.5F);
		        this.addSmeltingRecipe(new ItemStack(Items.COAL, 1, 0), new ItemStack(TFItems.POWDER, 1, 0), 0.2F);
		        this.addSmeltingRecipe(new ItemStack(Items.COAL, 1, 1), new ItemStack(TFItems.POWDER, 1, 0), 0.2F);

		        this.addSmeltingRecipe("oreIron", new ItemStack(TFItems.POWDER_IRON, 3), 0.4F);
		        this.addSmeltingRecipe("oreMagnetite", new ItemStack(TFItems.POWDER_IRON, 3), 0.4F);
		        this.addSmeltingRecipe("oreGold", new ItemStack(TFItems.POWDER, 3, 1), 0.8F);
		        this.addSmeltingRecipe("oreNiter", new ItemStack(TFItems.POWDER, 3, 6), 0.5F);
		        this.addSmeltingRecipe("oreSaltpeter", new ItemStack(TFItems.POWDER, 3, 6), 0.5F);
		        this.addSmeltingRecipe("oreSulfur", new ItemStack(TFItems.POWDER, 3, 7), 0.5F);
		        this.addSmeltingRecipe("oreQuartz", new ItemStack(TFItems.POWDER, 4, 4), 0.9F);

//		        this.addSmeltingRecipeForBlock(Blocks.IRON_ORE, new ItemStack(TFItems.POWDER_IRON, 2, 1), 0.4F);
//		        this.addSmeltingRecipeForBlock(Blocks.GOLD_ORE, new ItemStack(TFItems.POWDER, 2, 1), 0.8F);
		        this.addSmeltingRecipeForBlock(Blocks.EMERALD_ORE, new ItemStack(TFItems.POWDER, 3, 3), 1.0F);
		        this.addSmeltingRecipeForBlock(Blocks.DIAMOND_ORE, new ItemStack(TFItems.POWDER, 3, 2), 1.0F);
		        this.addSmeltingRecipeForBlock(Blocks.COAL_ORE, new ItemStack(TFItems.POWDER, 3, 0), 0.3F);
		        this.addSmeltingRecipeForBlock(Blocks.COAL_BLOCK, new ItemStack(TFItems.POWDER, 9, 0), 0.3F);
		        this.addSmeltingRecipeForBlock(Blocks.QUARTZ_BLOCK, new ItemStack(Items.QUARTZ, 4), 0.1F);
		        this.addSmeltingRecipeForBlock(Blocks.QUARTZ_STAIRS, new ItemStack(Items.QUARTZ, 1), 0.1F);
		        this.addSmeltingRecipeForBlock(Blocks.REDSTONE_ORE, new ItemStack(Items.REDSTONE, 15), 0.4F);
		        this.addSmeltingRecipeForBlock(Blocks.LAPIS_ORE, new ItemStack(Items.DYE, 18, 4), 0.5F);
		        this.addSmeltingRecipeForBlock(Blocks.GLOWSTONE, new ItemStack(Items.GLOWSTONE_DUST, 4), 0.3F);
		        this.addSmeltingRecipeForBlock(Blocks.COBBLESTONE, new ItemStack(Blocks.GRAVEL, 1), 0.1F);

		        this.addSmeltingRecipe(new ItemStack(Items.GOLDEN_SWORD, 1, 32767), new ItemStack(Items.GOLD_NUGGET, 14), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.GOLDEN_AXE, 1, 32767), new ItemStack(Items.GOLD_NUGGET, 21), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.GOLDEN_PICKAXE, 1, 32767), new ItemStack(Items.GOLD_NUGGET, 21), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.GOLDEN_SHOVEL, 1, 32767), new ItemStack(Items.GOLD_NUGGET, 7), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.GOLDEN_HOE, 1, 32767), new ItemStack(Items.GOLD_NUGGET, 14), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.GOLDEN_HELMET, 1, 32767), new ItemStack(Items.GOLD_NUGGET, 36), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.GOLDEN_CHESTPLATE, 1, 32767), new ItemStack(Items.GOLD_NUGGET, 57), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.GOLDEN_LEGGINGS, 1, 32767), new ItemStack(Items.GOLD_NUGGET, 50), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.GOLDEN_BOOTS, 1, 32767), new ItemStack(Items.GOLD_NUGGET, 28), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.GOLDEN_HORSE_ARMOR, 1, 32767), new ItemStack(Items.GOLD_NUGGET, 36), 0.1F);

		        this.addSmeltingRecipe(new ItemStack(Items.DIAMOND_HELMET, 1, 32767), new ItemStack(TFItems.POWDER, 3, 2), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.DIAMOND_CHESTPLATE, 1, 32767), new ItemStack(TFItems.POWDER, 5, 2), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.DIAMOND_LEGGINGS, 1, 32767), new ItemStack(TFItems.POWDER, 4, 2), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.DIAMOND_BOOTS, 1, 32767), new ItemStack(TFItems.POWDER, 2, 2), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.DIAMOND_AXE, 1, 32767), new ItemStack(TFItems.POWDER, 1, 2), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.DIAMOND_PICKAXE, 1, 32767), new ItemStack(TFItems.POWDER, 1, 2), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.DIAMOND_HORSE_ARMOR, 1, 32767), new ItemStack(TFItems.POWDER, 3, 2), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.DIAMOND_SWORD, 1, 32767), new ItemStack(TFItems.POWDER, 1, 2), 0.1F);
		        this.addSmeltingRecipe(new ItemStack(Items.DIAMOND_HOE, 1, 32767), new ItemStack(TFItems.POWDER, 1, 2), 0.1F);

//		        this.addSmeltingRecipe(new ItemStack(TFItems.CLUMPS, 1, 0), new ItemStack(TFItems.POWDER_IRON, 1), 0.1F);
//		        this.addSmeltingRecipe(new ItemStack(TFItems.CLUMPS, 1, 1), new ItemStack(TFItems.POWDER, 1, 1), 0.1F);
//		        this.addSmeltingRecipe(new ItemStack(TFItems.CLUMPS, 1, 2), new ItemStack(TFItems.POWDER, 1, 2), 0.1F);
//		        this.addSmeltingRecipe(new ItemStack(TFItems.CLUMPS, 1, 3), new ItemStack(TFItems.POWDER_SULFUR, 1), 0.1F);
//		        this.addSmeltingRecipe(new ItemStack(TFItems.CLUMPS, 1, 4), new ItemStack(TFItems.POWDER_NITER, 1), 0.1F);
//		        this.addSmeltingRecipe(new ItemStack(TFItems.CLUMPS, 1, 5), new ItemStack(TFItems.POWDER, 1, 6), 0.1F);
//		        this.addSmeltingRecipe(new ItemStack(TFItems.CLUMPS, 1, 6), new ItemStack(TFItems.POWDER, 1, 7), 0.1F);
//		        this.addSmeltingRecipe(new ItemStack(TFItems.CLUMPS, 1, 7), new ItemStack(Items.REDSTONE, 3), 0.1F);
//		        this.addSmeltingRecipe(new ItemStack(TFItems.CLUMPS, 1, 8), new ItemStack(Items.DYE, 3, 4), 0.1F);
//		        this.addSmeltingRecipe(new ItemStack(TFItems.CLUMPS, 1, 9), new ItemStack(TFItems.POWDER, 1, 13), 0.1F);
//		        this.addSmeltingRecipe(new ItemStack(TFItems.CLUMPS, 1, 10), new ItemStack(TFItems.POWDER, 1, 5), 0.1F);
//		        this.addSmeltingRecipe(new ItemStack(TFItems.CLUMPS, 1, 11), new ItemStack(TFItems.POWDER, 1, 4), 0.1F);
	}

	/**
	 * Adds a smelting recipe, where the input item is an instance of Block.
	 */
	public void addSmeltingRecipeForBlock(Block input, ItemStack stack, float experience)
	{
		this.addSmelting(Item.getItemFromBlock(input), stack, experience);
	}
	/**
	 * Adds a smelting recipe using an Item as the input item.
	 */
	public void addSmelting(Item input, ItemStack stack, float experience)
	{
		this.addSmeltingRecipe(new ItemStack(input, 1, 32767), stack, experience);
	}
	/**
	 * Adds a smelting recipe using an ItemStack as the input for the recipe.
	 */
	public void addSmeltingRecipe(ItemStack input, ItemStack stack, float experience)
	{
		if (getSmeltingResult(input) != ItemStack.EMPTY)
		{
			net.minecraftforge.fml.common.FMLLog.log.info("Ignored smelting recipe with conflicting input: {} = {}", input, stack);
			return;
		}
		this.smeltingList.put(input, stack);
		this.experienceList.put(stack, Float.valueOf(experience));
	}

	public void addSmeltingRecipe(String oreName, ItemStack stack, float experience)
	{
		List<ItemStack> candidate = OreDictionary.getOres(oreName);
		if (!candidate.isEmpty())
		{
			for (ItemStack target : candidate)
			{

				if (this.getSmeltingResult(target) != ItemStack.EMPTY) return;
			}
			this.smeltingOreList.put(oreName, stack);
			this.experienceOreList.put(oreName, experience);
		}
	}

	/**
	 * Returns the smelting result of an item.
	 */
	public ItemStack getSmeltingResult(ItemStack stack)
	{
		for (Entry<ItemStack, ItemStack> entry : this.smeltingList.entrySet())
		{
			if (this.compareItemStacks(stack, entry.getKey()))
			{
				return entry.getValue();
			}
		}
		int[] candidateId = OreDictionary.getOreIDs(stack);
		if (candidateId.length > 0)
		{

			for (int i = 0; i < candidateId.length; i++)
			{

				String oreName = OreDictionary.getOreName(candidateId[i]);
				for (Entry<String, ItemStack> entry2 : this.smeltingOreList.entrySet())
				{

					if (oreName.equals(entry2.getKey()))
					{

						return (ItemStack) entry2.getValue();
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}

	/**
	 * Compares two itemstacks to ensure that they are the same. This checks both the item and the metadata of the item.
	 */
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
	{
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}

	public Map<ItemStack, ItemStack> getSmeltingList()
	{
		return this.smeltingList;
	}

	public Map<String, ItemStack> getSmeltingOreList()
	{
		return this.smeltingOreList;
	}

	public float getSmeltingExperience(ItemStack stack)
	{
		float ret = stack.getItem().getSmeltingExperience(stack);
		if (ret != -1) return ret;

		for (Entry<ItemStack, Float> entry : this.experienceList.entrySet())
		{
			if (this.compareItemStacks(stack, entry.getKey()))
			{
				return ((Float) entry.getValue()).floatValue();
			}
		}
		int[] candidateId = OreDictionary.getOreIDs(stack);
		if (candidateId.length > 0)
		{

			for (int i = 0; i < candidateId.length; i++)
			{

				String oreName = OreDictionary.getOreName(candidateId[i]);
				for (Entry<String, Float> entry2 : this.experienceOreList.entrySet())
				{

					if (oreName.equals(entry2.getKey()))
					{

						return ((Float) entry2.getValue()).floatValue();
					}
				}
			}
		}
		return 0.0F;
	}
}