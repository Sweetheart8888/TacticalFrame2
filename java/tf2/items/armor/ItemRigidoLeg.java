package tf2.items.armor;

import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemRigidoLeg extends ItemRigidoArmor
{
	AttributeModifier moveSpeed = new AttributeModifier("ArmorSpeed", 0.1D, 2);

	public ItemRigidoLeg(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
	{
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
	{
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);
		if (equipmentSlot == EntityEquipmentSlot.LEGS)
		{
			multimap.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), this.moveSpeed);
		}
		return multimap;
	}
}
