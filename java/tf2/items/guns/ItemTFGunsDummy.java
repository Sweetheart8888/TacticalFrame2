package tf2.items.guns;

import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

/**
 * エラー回避用のダミークラス。
 */
public class ItemTFGunsDummy extends ItemBow
{

	public ItemTFGunsDummy()
	{
		super();
	}

	public boolean isEnchantable(ItemStack stack)
    {
        return false;
    }
}