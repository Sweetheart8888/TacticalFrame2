package tf2.tile;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBoat;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class VanillaItemBurnTime
{
	public static int getVanillaItemBurnTime(ItemStack stack)
	{
		Item item = stack.getItem();

		if (item == Item.getItemFromBlock(Blocks.WOODEN_SLAB))
        {
            return 150;
        }
        else if (item == Item.getItemFromBlock(Blocks.WOOL))
        {
            return 100;
        }
        else if (item == Item.getItemFromBlock(Blocks.CARPET))
        {
            return 67;
        }
        else if (item == Item.getItemFromBlock(Blocks.LADDER))
        {
            return 300;
        }
        else if (item == Item.getItemFromBlock(Blocks.WOODEN_BUTTON))
        {
            return 100;
        }
        else if (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD)
        {
            return 300;
        }
        else if (item == Item.getItemFromBlock(Blocks.COAL_BLOCK))
        {
            return 16000;
        }
        else if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName()))
        {
            return 200;
        }
        else if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName()))
        {
            return 200;
        }
        else if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName()))
        {
            return 200;
        }
        else if (item == Items.STICK)
        {
            return 100;
        }
        else if (item != Items.BOW && item != Items.FISHING_ROD)
        {
            if (item == Items.SIGN)
            {
                return 200;
            }
            else if (item == Items.COAL)
            {
                return 1600;
            }
            else if (item == Items.LAVA_BUCKET)
            {
                return 20000;
            }
            else if (item != Item.getItemFromBlock(Blocks.SAPLING) && item != Items.BOWL)
            {
                if (item == Items.BLAZE_ROD)
                {
                    return 2400;
                }
                else if (item instanceof ItemDoor && item != Items.IRON_DOOR)
                {
                    return 200;
                }
                else
                {
                    return item instanceof ItemBoat ? 400 : 0;
                }
            }
            else
            {
                return 100;
            }
        }
        else
        {
            return 300;
        }
	}
}
