package tf2.entity.mob.enemy;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityMobTF extends EntityMob
{
	public int attackTime;
	public int deathTicks;
	public EntityMobTF(World par1World)
    {
        super(par1World);
    }
    @Override
    public void setInWeb() {}

	@Override
	public boolean attackEntityFrom(DamageSource var1, float var2)
	{
		// defeatedcrowさんのHeatAndClimateModの寒暖ダメーシに対応
		return var1.damageType == "dcs_cold" ? false :
				var1.damageType == "dcs_heat" ? false :

				var1.damageType == "starve" ? false :
				var1.damageType == "fall" ? false :
				var1.damageType == "inFire" ? false :
				var1.damageType == "onFire" ? false :
				var1.damageType == "hotFloor" ? false :
				var1.damageType == "cactus" ? false : super.attackEntityFrom(var1, var2);
	}

	@Override
	public boolean isPotionApplicable(PotionEffect potioneffectIn)
	{
		if (potioneffectIn.getPotion() == MobEffects.POISON ||  potioneffectIn.getPotion() == MobEffects.WITHER)
		{
			return false;
		}
		return super.isPotionApplicable(potioneffectIn);
	}

	//--------ここからアニメーション関係
	@Override
	public void onEntityUpdate()
    {
		super.onEntityUpdate();

		if (this.attackTime > 0)
		{
			--this.attackTime;
		}
    }

	static class TFFloatingMoveHelper extends EntityMoveHelper
	{
		private final EntityLiving parentEntity;
		private int courseChangeCooldown;

		public TFFloatingMoveHelper(EntityLiving entity)
		{
			super(entity);
			this.parentEntity = entity;
		}

		public void onUpdateMoveHelper()
		{
			if (this.action == EntityMoveHelper.Action.MOVE_TO)
			{
				double d0 = this.posX - this.parentEntity.posX;
				double d1 = this.parentEntity.posY;
				double d2 = this.posZ - this.parentEntity.posZ;
				EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
				if (this.parentEntity.getAttackTarget() != null)
				{

					d0 = entitylivingbase.posX - this.parentEntity.posX;
					d2 = entitylivingbase.posZ - this.parentEntity.posZ;
				}

				double d3 = d0 * d0 + d2 * d2;

				if (this.courseChangeCooldown-- <= 0)
				{
					this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
					d3 = (double) MathHelper.sqrt(d3);

					if (this.isNotColliding(this.posX, this.posY, this.posZ, d3))
					{

						if (this.parentEntity.getAttackTarget() != null)
						{

							float f = this.parentEntity.getDistance(entitylivingbase) / 0.9F;

							if (f > 8.0D)
							{
								this.parentEntity.motionX += d0 / d3 * 0.045D;
								this.parentEntity.motionZ += d2 / d3 * 0.045D;
							}

						}
						else
						{
							this.parentEntity.motionX += d0 / d3 * 0.025D;
							this.parentEntity.motionZ += d2 / d3 * 0.025D;
						}
					}
					else
					{
						this.action = EntityMoveHelper.Action.WAIT;
					}
				}
			}
		}

		/**
		 * Checks if entity bounding box is not colliding with terrain
		 */
		private boolean isNotColliding(double x, double y, double z, double p_179926_7_)
		{
			double d0 = (x - this.parentEntity.posX) / p_179926_7_;
			double d1 = y;
			double d2 = (z - this.parentEntity.posZ) / p_179926_7_;
			AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();

			for (int i = 1; (double) i < p_179926_7_; ++i)
			{
				axisalignedbb = axisalignedbb.offset(d0, d1, d2);

				if (!this.parentEntity.world.getCollisionBoxes(this.parentEntity, axisalignedbb).isEmpty())
				{
					return false;
				}
			}

			return true;
		}
	}
}
