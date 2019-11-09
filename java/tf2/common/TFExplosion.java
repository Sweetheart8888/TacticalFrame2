package tf2.common;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tf2.TFDamageSource;

public class TFExplosion
{
	public static void doExplosion(World worldIn, EntityLivingBase entityIn, double x, double y, double z, double size, double damage)
	{
		double f3 = size;
		int x1 = MathHelper.floor(x - (double) f3 - 1.0D);
		int x2 = MathHelper.floor(x + (double) f3 + 1.0D);
		int y1 = MathHelper.floor(y - (double) f3 - 1.0D);
		int y2 = MathHelper.floor(y + (double) f3 + 1.0D);
		int z1 = MathHelper.floor(z - (double) f3 - 1.0D);
		int z2 = MathHelper.floor(z + (double) f3 + 1.0D);
		List list = worldIn.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB((double) x1, (double) y1, (double) z1, (double) x2, (double) y2, (double) z2));

		Vec3d vec3d = new Vec3d(x, y, z);

		for (int k2 = 0; k2 < list.size(); ++k2)
		{
			EntityLivingBase entity = (EntityLivingBase) list.get(k2);

			if (entity != entityIn)
			{

				double d5 = entity.posX - x;
				double d7 = entity.posY + (double) entity.getEyeHeight() - y;
				double d9 = entity.posZ - z;
				double d13 = (double) MathHelper.sqrt(d5 * d5 + d7 * d7 + d9 * d9);

				if (d13 < f3 + 0.5D)
				{
					double k = 1.25D - (d13 / (f3 + 0.5D));

					double lastDamage = damage * k;

					double block = (double) worldIn.getBlockDensity(vec3d, entity.getEntityBoundingBox());

					block = 1.0D - ((1.0D - block) * (1.0D - block));
					if (block < 0.1D)
					{
						block = 0.1D;
					}
					lastDamage = lastDamage * block;

					if(lastDamage >= 0.1D)
					{
						entity.attackEntityFrom(damageSource(entityIn), (float) lastDamage);
						entity.hurtResistantTime = 0;
					}
				}
			}
		}
	}

	public static DamageSource damageSource(EntityLivingBase entityIn)
	{
		if (entityIn == null)
		{
			return TFDamageSource.causeGrenadeDamage(null);
		}
		else
		{
			return TFDamageSource.causeGrenadeDamage(entityIn);
		}
	}
}
