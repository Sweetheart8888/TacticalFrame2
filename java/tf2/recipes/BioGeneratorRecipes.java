package tf2.recipes;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.Maps;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BioGeneratorRecipes
{
    private static final BioGeneratorRecipes SMELTING_BASE = new BioGeneratorRecipes();
    /** The list of smelting results. */
    private final Map<ItemStack, ItemStack> smeltingList = Maps.<ItemStack, ItemStack>newHashMap();
    /** A list which contains how many experience points each recipe output will give. */
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    /**
     * Returns an instance of FurnaceRecipes.
     */
    public static BioGeneratorRecipes instance()
    {
        return SMELTING_BASE;
    }

    private BioGeneratorRecipes()
    {
    	this.addSmelting(tf2.TFItems.WASTE_OIL, new ItemStack(tf2.TFItems.DIESEL_BOX), 0.4F);
    	this.addSmelting(tf2.TFItems.COALTAR, new ItemStack(tf2.TFItems.PHENOL), 0.4F);

    	this.addSmelting(Items.ROTTEN_FLESH, new ItemStack(Items.LEATHER), 0.4F);
        this.addSmelting(Items.LEATHER, new ItemStack(Items.SLIME_BALL), 0.4F);
        //this.addSmelting(Items.SLIME_BALL, new ItemStack(TFItems.RUBBER), 0.4F);
        this.addSmelting(Items.BUCKET, new ItemStack(Items.WATER_BUCKET), 0.3F);
        //this.addSmelting(Items.GLASS_BOTTLE, new ItemStack(TFItems.BIO_POTION), 0.5F);
        this.addSmelting(Items.POTATO, new ItemStack(Items.POISONOUS_POTATO), 0.2F);
        this.addSmelting(Items.REDSTONE, new ItemStack(Items.GLOWSTONE_DUST), 0.1F);
        this.addSmelting(Items.GLOWSTONE_DUST, new ItemStack(Items.REDSTONE), 0.1F);
        //this.addSmeltingRecipe(new ItemStack(TFItems.POWDER, 1, 11), new ItemStack(TFItems.POWDER, 1, 12), 0.1F);
        this.addSmeltingRecipeForBlock(Blocks.GLOWSTONE, new ItemStack(Items.REDSTONE, 4), 0.4F);
        this.addSmeltingRecipeForBlock(Blocks.REDSTONE_BLOCK, new ItemStack(Items.GLOWSTONE_DUST, 9), 0.9F);

        this.addSmelting(Items.BEETROOT, new ItemStack(Items.DYE, 3, 1), 0.1F);
        this.addSmeltingRecipeForBlock(Blocks.CACTUS, new ItemStack(Items.DYE, 3, 2), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 0), new ItemStack(Items.DYE, 3, 1), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 4), new ItemStack(Items.DYE, 3, 1), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 4), new ItemStack(Items.DYE, 4, 1), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 3), new ItemStack(Items.DYE, 3, 7), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 6), new ItemStack(Items.DYE, 3, 7), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 8), new ItemStack(Items.DYE, 3, 7), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 7), new ItemStack(Items.DYE, 3, 9), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 5), new ItemStack(Items.DYE, 4, 9), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.YELLOW_FLOWER, 1, 0), new ItemStack(Items.DYE, 3, 11), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 0), new ItemStack(Items.DYE, 4, 11), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 1), new ItemStack(Items.DYE, 3, 12), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 2), new ItemStack(Items.DYE, 3, 13), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.DOUBLE_PLANT, 1, 1), new ItemStack(Items.DYE, 4, 13), 0.1F);
        this.addSmeltingRecipe(new ItemStack(Blocks.RED_FLOWER, 1, 5), new ItemStack(Items.DYE, 3, 14), 0.1F);
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
        if (getSmeltingResult(input) != ItemStack.EMPTY) { net.minecraftforge.fml.common.FMLLog.log.info("Ignored smelting recipe with conflicting input: {} = {}", input, stack); return; }
        this.smeltingList.put(input, stack);
        this.experienceList.put(stack, Float.valueOf(experience));
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

    public float getSmeltingExperience(ItemStack stack)
    {
        float ret = stack.getItem().getSmeltingExperience(stack);
        if (ret != -1) return ret;

        for (Entry<ItemStack, Float> entry : this.experienceList.entrySet())
        {
            if (this.compareItemStacks(stack, entry.getKey()))
            {
                return ((Float)entry.getValue()).floatValue();
            }
        }

        return 0.0F;
    }
}