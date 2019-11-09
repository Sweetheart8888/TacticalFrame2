package tf2.tile;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import tf2.entity.mob.frend.EntityFriendMecha;

public class InventoryFriendMechaEquipment extends InventoryFriendMecha
{

	private final EntityFriendMecha entity;

	public InventoryFriendMechaEquipment(EntityFriendMecha entityMecha)
	{
		super(entityMecha, 3);
		this.entity = entityMecha;
	}

	public ItemStack getSkillAItem()
	{
		return this.getStackInSlot(0);
	}

	public ItemStack getSkillBItem()
	{
		return this.getStackInSlot(1);
	}

	public ItemStack getSkillCItem()
	{
		return this.getStackInSlot(2);
	}

	public void setSkillAItem(ItemStack itemStack)
	{
		this.setInventorySlotContents(0, itemStack);
	}

	public void setSkillBItem(ItemStack itemStack)
	{
		this.setInventorySlotContents(1, itemStack);
	}

	public void setSkillCItem(ItemStack itemStack)
	{
		this.setInventorySlotContents(2, itemStack);
	}

	public boolean getHasSkill(Item skill)
	{

		if(this.getSkillAItem() != null && this.getSkillAItem().getItem() == skill)
		{
			return true;
		}
		else if(this.getSkillBItem() != null && this.getSkillBItem().getItem() == skill)
		{
			return true;
		}
		else if(this.getSkillCItem() != null && this.getSkillCItem().getItem() == skill)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void setHasSkill(ItemStack itemStack)
	{
		entity.playSound(SoundEvents.ENTITY_PLAYER_LEVELUP, 0.5F, 1.0F);

		if(!entity.world.isRemote)
		{
    		if(!entity.getInventoryMechaEquipment().getHasSkill(itemStack.getItem()))
    		{
    			if(entity.getInventoryMechaEquipment().getSkillAItem().isEmpty())
    			{
    				entity.getInventoryMechaEquipment().setSkillAItem(itemStack);
    			}
    			else if(entity.getInventoryMechaEquipment().getSkillBItem().isEmpty() && entity.slotSize >= 2)
    			{
    				entity.getInventoryMechaEquipment().setSkillBItem(itemStack);
    			}
    			else if(entity.getInventoryMechaEquipment().getSkillCItem().isEmpty()  && entity.slotSize >= 3)
    			{
    				entity.getInventoryMechaEquipment().setSkillCItem(itemStack);
    			}
				else
				{
					entity.entityDropItem(itemStack, 0);
				}
    		}
			else
			{
				entity.entityDropItem(itemStack, 0);
			}
		}

		ITextComponent text = new TextComponentString("[");
		text.getStyle().setColor(TextFormatting.GREEN);
		ITextComponent itemName = new TextComponentString(itemStack.getDisplayName());
        text.appendSibling(itemName);
        text.appendText("]");

        String skillText = "skill.get";

        if (entity.getOwner() != null && entity.getOwner() instanceof EntityPlayerMP)
        {
        	entity.getOwner().sendMessage(new TextComponentTranslation(skillText, new Object[] {this.getDisplayName() , text}));
        }
	}

}
