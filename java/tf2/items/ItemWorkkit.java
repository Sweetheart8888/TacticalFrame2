package tf2.items;

import java.util.Random;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemWorkkit extends ItemBase
{
    public ItemWorkkit(String name)
    {
    	super(name);
        this.setMaxDamage(100);
        this.setMaxStackSize(1);
        this.setHasSubtypes(false);
        this.setNoRepair();
    }

    //クラフト後のアイテムを、ダメージを与えて返す
    @Override

	public ItemStack getContainerItem(ItemStack stack) {

		if (!stack.isEmpty() && stack.getItem() == this)
		{
			Random rand = Item.itemRand;
			ItemStack copy = stack.copy();
			boolean flag = copy.attemptDamageItem(1, rand, null);
			return flag ? ItemStack.EMPTY : copy;
		}

		return ItemStack.EMPTY;

	}

	@Override
	public boolean hasContainerItem(ItemStack stack)
	{
		return true;
	}
}