package tf2.items.armor;

import com.google.common.collect.Multimap;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;

public class ItemRigidoBody extends ItemRigidoArmor
{
	AttributeModifier damage = new AttributeModifier("ArmorDamage", 1.0D, 0);
	AttributeModifier knockBack = new AttributeModifier("ArmorRecistance", 0.4D, 2);

	public ItemRigidoBody(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn)
	{
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}

	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
	{
		Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

		if (equipmentSlot == EntityEquipmentSlot.CHEST)
		{
			multimap.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), this.knockBack);
			multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), this.damage);
		}

		return multimap;
	}
}
