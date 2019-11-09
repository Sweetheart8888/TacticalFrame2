package tf2.items;

import net.minecraft.item.Item;
import tf2.TF2Core;
import tf2.TFItems;
import tf2.util.IHasModel;

public class ItemBase extends Item implements IHasModel
{
	public ItemBase(String name)
	{
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.setCreativeTab(TF2Core.tabstfMain);

		TFItems.ITEMS.add(this);
	}

	@Override
	public void registerModel()
	{
		TF2Core.proxy.registerItemRenderer(this, 0, "inventory");
	}
}