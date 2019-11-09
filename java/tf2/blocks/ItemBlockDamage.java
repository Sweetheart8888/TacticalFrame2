package tf2.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class ItemBlockDamage extends ItemBlock
{
    public ItemBlockDamage(Block block)
    {
    	super(block);
    	this.setMaxDamage(0);
    	this.setHasSubtypes(true);
    }

    //ItemStackのdamage値からmetadataの値を返す。
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
}