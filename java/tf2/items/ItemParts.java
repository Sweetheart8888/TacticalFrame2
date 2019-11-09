package tf2.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import tf2.util.Reference;

public class ItemParts extends ItemBase
{
	public ItemParts(String name)
	{
		super(name);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
	}
	@Override
	public void registerModel()
	{
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "parts_0"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "parts_1"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 2, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "parts_2"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 3, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "parts_3"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 4, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "parts_4"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 5, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "parts_5"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 6, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "parts_6"), "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 7, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "parts_7"), "inventory"));
	}
	@Override
	public int getMetadata(int damage)
	{
		return damage;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemStack)
	{
		return super.getUnlocalizedName() + "_" + itemStack.getItemDamage();
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items)
	{
		if (this.isInCreativeTab(tab))
		{
			for (int i = 0; i < 8; ++i)
			{
				items.add(new ItemStack(this, 1, i));
			}
		}
	}
}
