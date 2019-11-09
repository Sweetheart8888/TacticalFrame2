package tf2.entity.mob.enemy;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import tf2.TFSoundEvents;
import tf2.entity.mob.ai.EntityAIAttackRangedGun;

public class EntityTM45A extends EntityMobTF implements IRangedAttackMob {

	public EntityTM45A(World worldIn) {
		super(worldIn);
		this.setSize(3.0F, 3.0F);
		this.moveHelper = new EntityTM45A.TM45AMoveHelper(this);
		this.isAirBorne = true;
	}

	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAIAttackRangedGun(this, 1.0D, 40.0F));
		this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
		this.tasks.addTask(5, new EntityTM45A.AIRandomFly(this));
		this.tasks.addTask(7, new EntityTM45A.AILookAround(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
		this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, false));
		this.targetTasks.addTask(3, new EntityAINearestAttackableTarget(this, EntityGolem.class, false));
		this.experienceValue = 5;
	}

	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(60.0D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(150.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.54D);
		this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float var2)
	{

	}

	protected void updateAITasks()
	{
		if (isBeingRidden() && getPassengers().get(0) instanceof EntityLiving && this.getAttackTarget() == null) {
			this.setAttackTarget(((EntityLiving) this.getPassengers().get(0)).getAttackTarget());
		}
		if (isBeingRidden() && getPassengers().get(0) instanceof EntityLiving && this.getAttackTarget() != null) {
			this.setAttackTarget(((EntityLiving) this.getPassengers().get(0)).getAttackTarget());
		}

		int height = 15;
		int i = 15;
		int j = 0;
		BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);


			while(i > 0) {
				blockpos = new BlockPos(this.posX, this.posY - (height - i), this.posZ);
				if(this.world.getBlockState(blockpos).getMaterial() != Material.AIR) {

				}
				else {
					j++;
				}
				i--;
			}
			if(j <= height - 1) {
				this.motionY += (0.15000001192092896D - this.motionY) * 0.10000001192092896D;

			}
			else {
				blockpos = new BlockPos(this.posX, this.posY - (height - (i + 0.5)), this.posZ);
				if(this.world.getBlockState(blockpos).getMaterial() == Material.AIR) {
					this.motionY = -0.05D;
				}
				else {
					this.motionY = 0.0D;
				}
			}


		if(this.getAttackTarget() != null)
		{
			if(!this.canEntityBeSeen(this.getAttackTarget()))
			{
				this.setAttackTarget(null);
			}
		}
		if(this.getAttackTarget() != null) {
            double d0 = this.getAttackTarget().posX - this.posX;
            double d1 = this.getAttackTarget().posZ - this.posZ;
            float f0 = MathHelper.sqrt(d0 * d0 + d1 * d1);

            if((!this.onGround) || (this.isBeingRidden() && this.getPassengers().get(0) instanceof EntityTM02))
            {
                this.motionX = d0 / (double)f0 * 0.25D * 0.5D + this.motionX * 0.1D;
                this.motionZ = d1 / (double)f0 * 0.25D * 0.5D + this.motionZ * 0.1D;
            }

            this.rotationYaw = -((float)MathHelper.atan2(d0, d1)) * (180F / (float)Math.PI);
            this.renderYawOffset = this.rotationYaw;


			float f = this.getDistance(this.getAttackTarget()) / 0.9F;

            float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1);

			if(f < 40.0D) {
	            this.motionX = - (d0 / (double)f2 * 0.1D * (30.0D - f) + this.motionX * 0.3D);
	            this.motionZ = - (d1 / (double)f2 * 0.1D * (30.0D - f) + this.motionZ * 0.3D);
			}
		}
		else
		{
			this.isAirBorne = true;
		}
	}

	static class AILookAround extends EntityAIBase {
		private final EntityTM45A parentEntity;

		public AILookAround(EntityTM45A TM45A) {
			this.parentEntity = TM45A;
			this.setMutexBits(2);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute() {
			return true;
		}

		/**
		 * Keep ticking a continuous task that has already been started
		 */
		public void updateTask() {

			BlockPos blockpos = new BlockPos(this.parentEntity.posX, this.parentEntity.posY - 9,
					this.parentEntity.posZ);

			EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();

			if (this.parentEntity.getAttackTarget() == null) {
				this.parentEntity.rotationYaw = -((float) MathHelper.atan2(this.parentEntity.motionX,
						this.parentEntity.motionZ)) * (180F / (float) Math.PI);
				this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
			} else {
				double d0 = 64.0D;

				if (entitylivingbase.getDistanceSq(this.parentEntity) < 4096.0D) {
					double d1 = entitylivingbase.posX - this.parentEntity.posX;
					double d2 = entitylivingbase.posZ - this.parentEntity.posZ;
					this.parentEntity.rotationYaw = -((float) MathHelper.atan2(d1, d2)) * (180F / (float) Math.PI);
					this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
				}
			}
		}
	}



	public void travel(float strafe, float vertical, float forward) {
		if (this.ticksExisted % 4 == 0 && !this.onGround) {
			//this.playSound(TFRSoundEvents.TFR_PROPELLER, 2.0F, 0.5F);
		}

		if (this.isInWater()) {
			this.moveRelative(strafe, vertical, forward, 0.02F);
			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.800000011920929D;
			this.motionZ *= 0.800000011920929D;
		} else if (this.isInLava()) {
			this.moveRelative(strafe, vertical, forward, 0.02F);
			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
			this.motionX *= 0.5D;
			this.motionZ *= 0.5D;
		} else {
			float f = 0.91F;

			if (this.onGround) {
				BlockPos underPos = new BlockPos(MathHelper.floor(this.posX),
						MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
				IBlockState underState = this.world.getBlockState(underPos);
				f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
			}

			float f1 = 0.16277136F / (f * f * f);
			this.moveRelative(strafe, vertical, forward, this.onGround ? 0.1F * f1 : 0.02F);
			f = 0.91F;

			if (this.onGround) {
				BlockPos underPos = new BlockPos(MathHelper.floor(this.posX),
						MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
				IBlockState underState = this.world.getBlockState(underPos);
				f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
			}

			this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
			this.motionX *= (double) f;
			this.motionZ *= (double) f;
		}

		this.prevLimbSwingAmount = this.limbSwingAmount;
		double d1 = this.posX - this.prevPosX;
		double d0 = this.posZ - this.prevPosZ;
		float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

		if (f2 > 1.0F) {
			f2 = 1.0F;
		}

		this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
		this.limbSwing += this.limbSwingAmount;

	}

	public boolean isOnLadder() {
		return false;
	}

	@Override
	public void setDead() {
		super.setDead();
	}

	public double getMountedYOffset() {
		return 0.0D;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return TFSoundEvents.TM_SAY;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return TFSoundEvents.TM_DEATH;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_IRONGOLEM_HURT;
	}

	@Override
	protected void playStepSound(BlockPos pos, Block blockIn) {
	}

	@Override
	public boolean doesEntityNotTriggerPressurePlate() {
		return true;
	}

	@Override
	public void setSwingingArms(boolean swingingArms) {
	}

	static class AIRandomFly extends EntityAIBase {
		private final EntityTM45A parentEntity;

		public AIRandomFly(EntityTM45A entityTM45A) {
			this.parentEntity = entityTM45A;
			this.setMutexBits(1);
		}

		/**
		 * Returns whether the EntityAIBase should begin execution.
		 */
		public boolean shouldExecute() {
			EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

			if (!entitymovehelper.isUpdating()) {
				return true;
			} else {
				double d0 = entitymovehelper.getX() - this.parentEntity.posX;
				//double d1 = entitymovehelper.getY() - this.parentEntity.posY;
				double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
				double d3 = d0 * d0 + d2 * d2;
				return d3 < 1.0D || d3 > 3600.0D;
			}
		}

		/**
		 * Returns whether an in-progress EntityAIBase should continue executing
		 */
		public boolean shouldContinueExecuting() {
			return false;
		}

		/**
		 * Execute a one shot task or start executing a continuous task
		 */
		public void startExecuting() {
			Random random = this.parentEntity.getRNG();
			double d0 = this.parentEntity.posX + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			double d1 = this.parentEntity.posY;
			double d2 = this.parentEntity.posZ + (double) ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
			this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
		}
	}

	static class TM45AMoveHelper extends EntityMoveHelper {
		private final EntityTM45A parentEntity;
		private int courseChangeCooldown;

		public TM45AMoveHelper(EntityTM45A TM45A) {
			super(TM45A);
			this.parentEntity = TM45A;
		}

		public void onUpdateMoveHelper() {
			if (this.action == EntityMoveHelper.Action.MOVE_TO) {
				double d0 = this.posX - this.parentEntity.posX;
				double d1 = this.parentEntity.posY;
				double d2 = this.posZ - this.parentEntity.posZ;
				EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
				if (this.parentEntity.getAttackTarget() != null) {

					d0 = entitylivingbase.posX - this.parentEntity.posX;
					d2 = entitylivingbase.posZ - this.parentEntity.posZ;
				}

				double d3 = d0 * d0 + d2 * d2;

				if (this.courseChangeCooldown-- <= 0) {
					this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
					d3 = (double) MathHelper.sqrt(d3);

					if (this.isNotColliding(this.posX, this.posY, this.posZ, d3)) {

						if (this.parentEntity.getAttackTarget() != null) {

							float f = this.parentEntity.getDistance(entitylivingbase) / 0.9F;

							if (f > 16.0D) {
								this.parentEntity.motionX += d0 / d3 * 0.045D;
								this.parentEntity.motionZ += d2 / d3 * 0.045D;
							}

						} else {
							this.parentEntity.motionX += d0 / d3 * 0.025D;
							this.parentEntity.motionZ += d2 / d3 * 0.025D;
						}
					} else {
						this.action = EntityMoveHelper.Action.WAIT;
					}
				}
			}
		}

		/**
		 * Checks if entity bounding box is not colliding with terrain
		 */
		private boolean isNotColliding(double x, double y, double z, double p_179926_7_) {
			double d0 = (x - this.parentEntity.posX) / p_179926_7_;
			double d1 = y;
			double d2 = (z - this.parentEntity.posZ) / p_179926_7_;
			AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();

			for (int i = 1; (double) i < p_179926_7_; ++i) {
				axisalignedbb = axisalignedbb.offset(d0, d1, d2);

				if (!this.parentEntity.world.getCollisionBoxes(this.parentEntity, axisalignedbb).isEmpty()) {
					return false;
				}
			}

			return true;
		}
	}

	@Override
	public void updatePassenger(Entity passenger) {
		if (!this.getPassengers().isEmpty()) {
			Vec3d riderPos = this.getRiderPosition();

			this.getPassengers().get(0).setPosition(riderPos.x, riderPos.y, riderPos.z);
		}
	}

	private Vec3d getRiderPosition() {

		if (!this.getPassengers().isEmpty()) {
			float distance = 0.9F;

			double d = 180;

			double var1 = Math.cos((this.rotationYaw + d) * Math.PI / 180.0D) * distance;
			double var3 = Math.sin((this.rotationYaw + d) * Math.PI / 180.0D) * distance;

			return new Vec3d(this.posX, this.posY + this.getMountedYOffset() + this.getPassengers().get(0).getYOffset(), this.posZ);
		} else {
			return new Vec3d(this.posX, this.posY, this.posZ);
		}
	}

	@Override
	public boolean canRiderInteract() {
		return true;
	}


}