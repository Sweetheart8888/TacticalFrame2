package tf2.recipes.guncraft;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tf2.TFBlocks;
import tf2.TFItems;
import tf2.util.Reference;

public class CraftingManagerMachineStation {

	private static final Logger LOGGER = LogManager.getLogger();
	public static Map<ResourceLocation, IRecipe> REGISTRY = Maps.<ResourceLocation, IRecipe>newLinkedHashMap();

	public static boolean init() {

		try {

			registerRecipes();
			customParseJsonRecipes();
			return true;
		}
		catch(Throwable t) {

			return false;
		}
	}

	private static void registerRecipes()
	{
		register("bike", new ShapedOreRecipe(null, new ItemStack(TFItems.SPAWNFM, 1, 9), new Object[]{" A ", "BCB", " X ", 'X', new ItemStack(TFItems.PARTS, 1, 0), 'A', TFBlocks.MACHINE_CHASSIS, 'B', new ItemStack(TFItems.PARTS, 1, 7), 'C', TFItems.DEVELOP_CHIP_0}));

		register("cfr12", new ShapedOreRecipe(null, new ItemStack(TFItems.SPAWNFM, 1, 0), new Object[]{" A ", "BCB", " X ", 'X', new ItemStack(TFItems.PARTS, 1, 0), 'A', new ItemStack(TFItems.PARTS, 1, 2), 'B', TFBlocks.MACHINE_CHASSIS, 'C', TFItems.DEVELOP_CHIP_0}));

		register("mtt1", new ShapedOreRecipe(null, new ItemStack(TFItems.SPAWNFM, 1, 1), new Object[]{" A ", "BCB", " X ", 'X', new ItemStack(TFItems.PARTS, 1, 1), 'A', new ItemStack(TFItems.PARTS, 1, 2), 'B', TFBlocks.MACHINE_CHASSIS, 'C', TFItems.DEVELOP_CHIP_0}));
		register("mtt2", new ShapedOreRecipe(null, new ItemStack(TFItems.SPAWNFM, 1, 2), new Object[]{" A ", "BCB", " X ", 'X', new ItemStack(TFItems.PARTS, 1, 1), 'A', new ItemStack(TFItems.PARTS, 1, 2), 'B', TFBlocks.MACHINE_CHASSIS, 'C', TFItems.DEVELOP_CHIP_0}));
		register("mtt3", new ShapedOreRecipe(null, new ItemStack(TFItems.SPAWNFM, 1, 3), new Object[]{" A ", "BCB", " X ", 'X', new ItemStack(TFItems.PARTS, 1, 1), 'A', new ItemStack(TFItems.PARTS, 1, 2), 'B', TFBlocks.MACHINE_CHASSIS, 'C', TFItems.DEVELOP_CHIP_0}));

		register("mtt4", new ShapedOreRecipe(null, new ItemStack(TFItems.SPAWNFM, 1, 4), new Object[]{"BAB", 'A', new ItemStack(TFItems.PARTS, 1, 1), 'B', TFBlocks.MACHINE_CHASSIS}));

		register("tf77b", new ShapedOreRecipe(null, new ItemStack(TFItems.SPAWNFM, 1, 5), new Object[]{" A ", "BCB", " X ", 'X', new ItemStack(TFItems.PARTS, 1, 1), 'A', new ItemStack(TFItems.PARTS, 1, 4), 'B', TFBlocks.MACHINE_CHASSIS, 'C', TFItems.DEVELOP_CHIP_1}));
		register("tf78r", new ShapedOreRecipe(null, new ItemStack(TFItems.SPAWNFM, 1, 6), new Object[]{" A ", "BCB", " X ", 'X', new ItemStack(TFItems.PARTS, 1, 1), 'A', new ItemStack(TFItems.PARTS, 1, 4), 'B', TFBlocks.MACHINE_CHASSIS, 'C', TFItems.DEVELOP_CHIP_1}));
		register("tf79p", new ShapedOreRecipe(null, new ItemStack(TFItems.SPAWNFM, 1, 7), new Object[]{" A ", "BCB", " X ", 'X', new ItemStack(TFItems.PARTS, 1, 1), 'A', new ItemStack(TFItems.PARTS, 1, 4), 'B', TFBlocks.MACHINE_CHASSIS, 'C', TFItems.DEVELOP_CHIP_1}));
		register("tf80g", new ShapedOreRecipe(null, new ItemStack(TFItems.SPAWNFM, 1, 8), new Object[]{" A ", "BCB", " X ", 'X', new ItemStack(TFItems.PARTS, 1, 1), 'A', new ItemStack(TFItems.PARTS, 1, 4), 'B', TFBlocks.MACHINE_CHASSIS, 'C', TFItems.DEVELOP_CHIP_1}));
	}

	private static void customParseJsonRecipes() {

		FileSystem file = null;
		Gson gson = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
		try {

			URL url = CraftingManagerMachineStation.class.getResource("/assets/.tacticalframeroot");
			if(url != null) {

				URI uri = url.toURI();
				Path path;
				if("file".equals(uri.getScheme())) {

					path = Paths.get(CraftingManagerMachineStation.class.getResource("/assets/tf/customrecipes").toURI());
				}
				else {

					if(!"jar".equals(uri.getScheme())) {

						LOGGER.error("Unsupported scheme " + uri + " trying to list all recipes");
						return; // CHANGE
					}
					file = FileSystems.newFileSystem(uri, Collections.emptyMap());
					path = file.getPath("/assets/tf/recipes");
				}
				Iterator<Path> iterator = Files.walk(path).iterator();
				while(iterator.hasNext()) {

					Path path1 = iterator.next();
					if("json".equals(FilenameUtils.getExtension(path1.toString()))) {

						Path path2 = path.relativize(path1);
						String s = FilenameUtils.removeExtension(path2.toString()).replaceAll("\\\\", "/");
						ResourceLocation resourceLocation = new ResourceLocation(Reference.MOD_ID, s); // "[json_recipe]"
						BufferedReader bufferedReader = null;
						try {

							try {

								bufferedReader = Files.newBufferedReader(path1);
								register(resourceLocation, customParseRecipeJson((JsonObject)JsonUtils.fromJson(gson, bufferedReader, JsonObject.class)));
							}
							catch(JsonParseException jsonParseException) {

								LOGGER.error("Parsing error loading recipe " + resourceLocation, (Throwable)jsonParseException);
								return; // CHANGE
							}
							catch(IOException ioException) {

								LOGGER.error("Couldn't read recipe " + resourceLocation + " from " + path1, (Throwable)ioException);
								return; // CHANGE
							}
						}
						finally {

							IOUtils.closeQuietly((Reader)bufferedReader);
						}
					}
				}
				return; // CHANGE
			}
			LOGGER.error("Couldn't find .mcassetsroot");
		}
		catch(IOException | URISyntaxException uriSyntaxException) {

			LOGGER.error("Couldn't get a list of all recipe files", (Throwable)uriSyntaxException);
			return; // CHANGE
		}
		finally {

			IOUtils.closeQuietly((Closeable)file);
		}
	}

	private static IRecipe customParseRecipeJson(JsonObject jsonObject) {

		String s = JsonUtils.getString(jsonObject, "type");
		if("crafting_shaped".equals(s)) {

			return deserializeShapedRecipe(jsonObject);
		}
		else if("crafting_shapeless".equals(s)) {

			return deserializeShapelessRecipe(jsonObject);
		}
		else if("forge:ore_shaped".equals(s)) {

			return ShapedOreRecipe.factory(new JsonContext(Reference.MOD_ID), jsonObject);
		}
		else if("forge:ore_shapeless".equals(s)) {

			return ShapelessOreRecipe.factory(new JsonContext(Reference.MOD_ID), jsonObject);
		}
		else {

			throw new JsonSyntaxException("Invalid or unsupported recipe type '" + s + "'");
		}
	}

	private static IRecipe deserializeShapedRecipe(JsonObject jsonObject) {

		String s = JsonUtils.getString(jsonObject, "group", "");
		Map<String, Ingredient> map = customDeserializeKey(JsonUtils.getJsonObject(jsonObject, "key"));
		String[] astring = customShrink(customPatternFromJson(JsonUtils.getJsonArray(jsonObject, "pattern")));
		int i = astring[0].length();
		int j = astring.length;
		NonNullList<Ingredient> nnList = customDeserializeIngredients(astring, map, i, j);
		ItemStack itemstack = ShapedRecipes.deserializeItem(JsonUtils.getJsonObject(jsonObject, "result"), true);
		return new ShapedRecipes(s, i, j, nnList, itemstack);
	}

	private static Map<String, Ingredient> customDeserializeKey(JsonObject jsonObject) {

		Map<String, Ingredient> map = Maps.<String, Ingredient>newHashMap();
		for(Entry<String, JsonElement> entry : jsonObject.entrySet()) {

			if(((String)entry.getKey()).length() != 1) {

				throw new JsonSyntaxException("Invalid key entry: '" + (String)entry.getKey() + "' is an invalid symbol (must be 1 character only).");
			}
			if(" ".equals(entry.getKey())) {

				throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol");
			}
			map.put(entry.getKey(), ShapedRecipes.deserializeIngredient(entry.getValue()));
		}
		map.put(" ", Ingredient.EMPTY);
		return map;
	}

	@VisibleForTesting
	private static String[] customShrink(String... string) {

		int i = Integer.MAX_VALUE;
		int j = 0;
		int k = 0;
		int l = 0;
		for(int i1 = 0; i1 < string.length; i1++) {

			String s = string[i1];
			i = Math.min(i, customFirstNonSpace(s));
			int j1 = customLastNonSpace(s);
			j = Math.max(j, j1);
			if(j1 < 0) {

				if(k == i1) {

					k++;
				}
				l++;
			}
			else {

				l = 0;
			}
		}
		if(string.length == l) {

			return new String[0];
		}
		else {

			String[] astring = new String[string.length - l - k];
			for(int k1 = 0; k1 < astring.length; k1++) {

				astring[k1] = string[k1 + k].substring(i, j + 1);
			}
			return astring;
		}
	}

	private static int customFirstNonSpace(String s) {

		int i;
		for(i = 0; i < s.length() && s.charAt(i) == ' '; i++) {

			;
		}
		return i;
	}

	private static int customLastNonSpace(String s) {

		int i;
		for(i = s.length() - 1; i >= 0 && s.charAt(i) == ' '; i--) {

			;
		}
		return i;
	}

	private static String[] customPatternFromJson(JsonArray jsonArray) {

		String[] astring = new String[jsonArray.size()];
		if(astring.length > 3) { // crafting slot row

			throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
		}
		else if(astring.length == 0) {

			throw new JsonSyntaxException("Invalid patter: empty pattern not allowed");
		}
		else {

			for(int i = 0; i < astring.length; i++) {

				String s = JsonUtils.getString(jsonArray.get(i), "pattern[" + i + "]");
				if(s.length() > 3) { // crafting slot column

					throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
				}
				if(i > 0 && astring[0].length() != s.length()) {

					throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
				}
				astring[i] = s;
			}
			return astring;
		}
	}

	private static NonNullList<Ingredient> customDeserializeIngredients(String[] astring, Map<String, Ingredient> map, int a, int b) {

		NonNullList<Ingredient> nnList = NonNullList.<Ingredient>withSize(a * b, Ingredient.EMPTY);
		Set<String> set = Sets.newHashSet(map.keySet());
		set.remove(" ");
		for(int i = 0; i < astring.length; i++) {

			for(int j = 0; j < astring[i].length(); j++) {

				String s = astring[i].substring(j, j + 1);
				Ingredient ingredient = map.get(s);
				if(ingredient == null) {

					throw new JsonSyntaxException("Pattern references symbol '" + s + "' but it's not defined in the key");
				}
				set.remove(s);
				nnList.set(j + a * i, ingredient);
			}
		}
		if(!set.isEmpty()) {

			throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + set);
		}
		else {

			return nnList;
		}
	}

	private static IRecipe deserializeShapelessRecipe(JsonObject jsonObject) {

		String s = JsonUtils.getString(jsonObject, "group", "");
		NonNullList<Ingredient> nnList = customDeserializeIngredients(JsonUtils.getJsonArray(jsonObject, "ingredients"));
		if(nnList.isEmpty()) {

			throw new JsonParseException("No ingredients for shapeless recipe");
		}
		else if(nnList.size() > 9) { // crafting slot num

			throw new JsonParseException("Too many ingredients for shapeless recipe");
		}
		else {

			ItemStack itemstack = ShapedRecipes.deserializeItem(JsonUtils.getJsonObject(jsonObject, "result"), true);
			return new ShapelessRecipes(s, itemstack, nnList);
		}
	}

	private static NonNullList<Ingredient> customDeserializeIngredients(JsonArray jsonArray) {

		NonNullList<Ingredient> nnList = NonNullList.<Ingredient>create();
		for(int i = 0; i < jsonArray.size(); i++) {

			Ingredient ingredient = ShapedRecipes.deserializeIngredient(jsonArray.get(i));
			if(ingredient != Ingredient.EMPTY) {

				nnList.add(ingredient);
			}
		}
		return nnList;
	}

	private static void register(String name, IRecipe recipe) {

		register(new ResourceLocation(Reference.MOD_ID, name), recipe); //  "[java_recipe]"
	}

	private static void register(ResourceLocation name, IRecipe recipe) {

		REGISTRY.put(name, recipe);
	}

	public static List<ItemStack> findMatchingResult(InventoryCrafting craftMatrix, World worldIn) {

		List<ItemStack> result = new ArrayList();
		for(Map.Entry<ResourceLocation, IRecipe> entry : REGISTRY.entrySet()) {

			if(entry.getValue().matches(craftMatrix, worldIn)) {

				result.add(entry.getValue().getCraftingResult(craftMatrix));
			}
		}
		return result;
	}

	public static NonNullList<ItemStack> getRemainingItems(InventoryCrafting craftMatrix, World worldIn) {

		for(Map.Entry<ResourceLocation, IRecipe> entry : REGISTRY.entrySet()) {

			if(entry.getValue().matches(craftMatrix, worldIn)) {

				return entry.getValue().getRemainingItems(craftMatrix);
			}
		}
		NonNullList<ItemStack> nnList = NonNullList.<ItemStack>withSize(craftMatrix.getSizeInventory(), ItemStack.EMPTY);
		for(int i = 0; i < nnList.size(); i++) {

			nnList.set(i, craftMatrix.getStackInSlot(i));
		}
		return nnList;
	}


	// ↓いらない
	public static String getInputs(IRecipe recipe) {

		String inputs = "";
		NonNullList<Ingredient> list = recipe.getIngredients();
		Iterator iterator = list.iterator();
		while(iterator.hasNext()) {

			Ingredient ingredient = (Ingredient)iterator.next();
			ItemStack[] stack = ingredient.getMatchingStacks();
			if(stack.length > 1) {

				String buffer = "[ ";
				for(int i = 0; i < stack.length; i++) {

					ItemStack itemStack = stack[i];
					if(!itemStack.isEmpty()) {

						buffer += itemStack.getItem().getRegistryName();
					}
					if(i < stack.length - 1) {

						buffer += ",";
					}
					buffer += " ";
				}
				buffer += "]";
				inputs += buffer + ", ";
			}
			else if(stack.length == 1) {

				ItemStack itemStack = stack[0];
				if(!itemStack.isEmpty()) {

					inputs += itemStack.getItem().getRegistryName();
				}
			}
			if(iterator.hasNext()) {

				inputs += ", ";
			}
		}
		return inputs;
	}
}
