package tf2.items.armor;

import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemRigidoHelmet extends ItemRigidoArmor
{
	AttributeModifier maxHealth = new AttributeModifier("ArmorHealth", 4.0D, 0);

	public ItemRigidoHelmet(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
	{
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
	{
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.HEAD)
		{
			multimap.put(SharedMonsterAttributes.MAX_HEALTH.getName(), this.maxHealth);
		}
		return multimap;
	}
}
