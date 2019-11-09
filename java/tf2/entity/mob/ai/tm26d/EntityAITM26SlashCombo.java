package tf2.entity.mob.ai.tm26d;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import tf2.entity.mob.enemy.EntityMobTF;
import tf2.entity.mob.enemy.EntityTM26D;

public class EntityAITM26SlashCombo extends EntityAIBase
{
	/** The entity that is leaping. */
	EntityTM26D leaper;
	/** The entity that the leaper is leaping towards. */
	EntityLivingBase leapTarget;

	double startDistance;

	private int chargeTime;

	public EntityAITM26SlashCombo(EntityTM26D leapingEntity, double startDistanceIn)
	{
		this.leaper = leapingEntity;
		this.startDistance = startDistanceIn;
		this.setMutexBits(6);
	}

	/**
	 * Returns whether the EntityAIBase should begin execution.
	 */
	@Override
	public boolean shouldExecute()
	{
		this.leapTarget = this.leaper.getAttackTarget();

		if (this.leapTarget == null)
		{
			return false;
		}
		else
		{
			double d0 = this.leaper.getDistance(this.leapTarget);

			if (d0 <= this.startDistance)
			{
				if (!this.leaper.onGround)
				{
					return false;
				}
				else
				{
					return this.leaper.getRNG().nextInt(25) == 0;
				}
			}
			else
			{
				return false;
			}
		}
	}

	@Override
	public void resetTask()
	{
		super.resetTask();
		this.chargeTime = 0;
	}

	@Override
	public boolean shouldContinueExecuting()
	{
		return this.chargeTime < this.endTime();
	}
	@Override
	public void startExecuting()
	{
		super.startExecuting();
	}

	public int endTime()
	{
		if(this.leaper.getHealthCount() > 1)
		{
			return 70;
		}
		else
		{
			return 110;
		}
	}

	@Override
	public void updateTask()
	{
		EntityLivingBase attackTarget = this.leaper.getAttackTarget();

		if (attackTarget != null)
		{
			boolean flag = this.leaper.getEntitySenses().canSee(leapTarget);
			boolean flag1 = this.chargeTime > 0;
			if (flag != flag1)
			{
				this.chargeTime = 0;
			}
			if (flag)
			{
				++this.chargeTime;
			}
			else
			{
				--this.chargeTime;
			}

			double dx = this.leapTarget.posX - this.leaper.posX;
			double dz = this.leapTarget.posZ - this.leaper.posZ;
			double dy = this.leapTarget.posY - this.leaper.posY;
			this.leaper.getLookHelper().setLookPosition(attackTarget.posX, attackTarget.posY + (double)attackTarget.getEyeHeight(), attackTarget.posZ, (float)this.leaper.getHorizontalFaceSpeed(), (float)this.leaper.getVerticalFaceSpeed());

			if (this.chargeTime < this.endTime())
			{
				//this.leaper.getNavigator().clearPath();
				this.leaper.getNavigator().tryMoveToEntityLiving(attackTarget, 0F);
			}

			if (this.chargeTime == 1)
			{
				double f2 = MathHelper.sqrt(dx * dx + dz * dz);
				double y1 = Math.max(dy, 0D);
				double y2 = MathHelper.sqrt(y1 * y1);
				double y3 = Math.min(y2, 10D);

				this.leaper.motionX = (dx / f2) * MathHelper.sqrt(f2) * 1.0F;
				this.leaper.motionZ = (dz / f2) * MathHelper.sqrt(f2) * 1.0F;
				this.leaper.motionY = (0.14F * y3) + 0.3F;
			}

			if (this.chargeTime == 10)
			{
				this.leaper.setAnimetion((byte)2, 30);
			}

			if (this.chargeTime == 40)
			{
				List var7 = this.leaper.world.getEntitiesWithinAABB(EntityLivingBase.class, this.leaper.getEntityBoundingBox().grow(7.0D));
				int k;
				for (k = 0; k < var7.size(); ++k)
				{
					EntityLivingBase entity = (EntityLivingBase) var7.get(k);
					if (!(entity instanceof EntityMobTF))
					{
						DamageSource var201 = DamageSource.causeMobDamage(this.leaper);
						entity.attackEntityFrom(var201, 12F);
						entity.hurtResistantTime = 0;
					}
				}
			}
			if (this.chargeTime < 42 && this.chargeTime > 36 &&this.chargeTime % 1 == 0)
			{
				this.leaper.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 2.5F, 0.2F / (this.leaper.getRNG().nextFloat() * 0.4F + 0.8F));
			}

			if(this.leaper.getHealthCount() < 2)
			{
				if (this.chargeTime == 50)
				{
					this.leaper.motionY = 1.2D;
				}
				if (this.chargeTime == 63)
				{
					double f2 = MathHelper.sqrt(dx * dx + dz * dz);
					double y1 = Math.min(dy, 0D);
					double y2 = MathHelper.sqrt(y1 * y1);
					double y3 = Math.min(y2, 10D);

					this.leaper.motionX = (dx / f2) * MathHelper.sqrt(f2) * 1.45F;
					this.leaper.motionZ = (dz / f2) * MathHelper.sqrt(f2) * 1.45F;
					this.leaper.motionY = -(1.0F * y3);
				}
				if (this.chargeTime == 70)
				{
					this.leaper.setAnimetion((byte)2, 20);
				}
				if (this.chargeTime == 90)
				{
					List var7 = this.leaper.world.getEntitiesWithinAABB(EntityLivingBase.class, this.leaper.getEntityBoundingBox().grow(7.0D));
					int k;
					for (k = 0; k < var7.size(); ++k)
					{
						EntityLivingBase entity = (EntityLivingBase) var7.get(k);
						if (!(entity instanceof EntityMobTF))
						{
							DamageSource var201 = DamageSource.causeMobDamage(this.leaper);
							entity.attackEntityFrom(var201, 12F);
							entity.hurtResistantTime = 0;
						}
					}
				}
				if (this.chargeTime < 92 && this.chargeTime > 86 &&this.chargeTime % 1 == 0)
				{
					this.leaper.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 2.5F, 0.2F / (this.leaper.getRNG().nextFloat() * 0.4F + 0.8F));
				}

				double f = this.leaper.getDistance(this.leapTarget);

				if(this.chargeTime > 50 && f < 3.5F)
				{
					this.leaper.attackEntityAsMob(attackTarget);
				}
			}

			if (this.chargeTime == 95 && this.leaper.getHealthCount() < 1)
			{
				double f2 = MathHelper.sqrt(dx * dx + dz * dz);
				double y1 = Math.max(dy, 0D);
				double y2 = MathHelper.sqrt(y1 * y1);
				double y3 = Math.min(y2, 10D);

				this.leaper.motionX = (dx / f2) * MathHelper.sqrt(f2) * 1.2F;
				this.leaper.motionZ = (dz / f2) * MathHelper.sqrt(f2) * 1.2F;
				this.leaper.motionY = (0.14F * y3) + 0.3F;
			}
		}
	}
}