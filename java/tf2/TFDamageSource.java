package tf2;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

public class TFDamageSource extends DamageSource
{
	public static DamageSource HEAT = (new DamageSource("heat")).setDamageBypassesArmor().setFireDamage();

	public TFDamageSource(String p_i1566_1_)
	{
		super("TFDamageSource");
	}

	public static DamageSource causeBulletDamage(Entity par1, Entity par2)
    {
        return (new EntityDamageSourceIndirect("bullet", par1, par2)).setProjectile();
    }
	public static DamageSource causeGrenadeDamage(Entity par1)
	{
		return new EntityDamageSource("grenade", par1).setExplosion();
	}
}