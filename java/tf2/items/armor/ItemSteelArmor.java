package tf2.items.armor;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import tf2.TF2Core;
import tf2.TFItems;
import tf2.util.IHasModel;
import tf2.util.Reference;
public class ItemSteelArmor extends ItemArmor implements IHasModel
{
	private String tex;

	public ItemSteelArmor(String name,ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
	{
        super(materialIn, renderIndexIn, equipmentSlotIn);
    	this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.tex = name;
		this.setCreativeTab(TF2Core.tabstfMain);

		TFItems.ITEMS.add(this);
    }
	@Override
	public void registerModel()
	{
		//TFCore.proxy.registerItemRenderer(this, 0, "inventory");
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(new ResourceLocation(Reference.MOD_ID, "armor/" + tex), "inventory"));
	}
}
