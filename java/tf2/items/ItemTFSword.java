package tf2.items;

import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraftforge.oredict.OreDictionary;
import tf2.TF2Core;
import tf2.TFItems;
import tf2.util.IHasModel;

public class ItemTFSword extends ItemSword implements IHasModel
{
	private final ItemStack repair;

	public ItemTFSword(String name, ToolMaterial material, ItemStack repairItem)
	{
		super(material);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.repair = repairItem;
		this.setCreativeTab(TF2Core.tabstfMain);
		TFItems.ITEMS.add(this);
	}
	@Override
	public void registerModel()
	{
		TF2Core.proxy.registerItemRenderer(this, 0, "inventory");
	}
	@Override
	public boolean getIsRepairable(ItemStack toRepair, ItemStack repair)
    {
        ItemStack mat = this.repair;
        if (!mat.isEmpty() && OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
    }

}
