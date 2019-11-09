package tf2.entity.projectile;

import java.util.UUID;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TF2Core;
import tf2.util.RegistryHandler;

public class EntityBarrier extends Entity
{
	protected EntityLivingBase shootingEntity;
    protected EntityLivingBase caster;
    protected UUID shooterUuid;
    protected int ticksAlive;
    protected int ticksInAir;
    protected int life;
    protected int maxLife;
    protected int maxAmount;
    protected int amount;
    protected int barrierAmount;
    protected double distance;
    private static final DataParameter<Integer> DAMAGE_TAKEN = EntityDataManager.<Integer>createKey(EntityEnemyBarrier.class, DataSerializers.VARINT);

    public EntityBarrier(World worldIn)
    {
        super(worldIn);
        this.setSize(1.8F, 1.5F);
    }

    protected void entityInit()
    {
    	this.dataManager.register(DAMAGE_TAKEN, Integer.valueOf(0));
    }

    /**
     * Checks if the entity is in range to render.
     */
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;

        if (Double.isNaN(d0))
        {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0D;
        return distance < d0 * d0;
    }

    public EntityBarrier(World worldIn, EntityLivingBase shooter, int life, int ticks, int max, int amount, double distance)
    {
        super(worldIn);
        this.setShooter(shooter);
        this.maxLife = life;
        this.ticksAlive = ticks;
        this.maxAmount = max;
        this.amount = amount;
        this.barrierAmount = amount;
        this.distance = distance;
    }

    public void setShooter(@Nullable EntityLivingBase p_190549_1_)
    {
        this.shootingEntity = p_190549_1_;
        this.setShooterUuid(p_190549_1_ == null ? null : p_190549_1_.getUniqueID());
    }

    @Nullable
    public EntityLivingBase getShooter()
    {
        if (this.shootingEntity == null && this.getShooterUuid() != null && this.world instanceof WorldServer)
        {
            Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.getShooterUuid());

            if (entity instanceof EntityLivingBase)
            {
                this.shootingEntity = (EntityLivingBase)entity;
            }
        }

        return this.shootingEntity;
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound compound)
    {
        this.setShooterUuid(compound.getUniqueId("ShooterUUID"));
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound compound)
    {

        if (this.getShooterUuid() != null)
        {
            compound.setUniqueId("ShooterUUID", this.getShooterUuid());
        }
    }

	public UUID getShooterUuid() {
		return shooterUuid;
	}

	public void setShooterUuid(UUID shooterUuid) {
		this.shooterUuid = shooterUuid;
	}

    /**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return false;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if (this.world.isRemote || (this.getShooter() == null || !this.getShooter().isDead) && this.world.isBlockLoaded(new BlockPos(this)))
        {
            super.onUpdate();

            if (!this.world.isRemote)
            {
	//            this.world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, this.posX, this.posY + this.height/2, this.posZ, 0.0D, 0.0D, 0.0D);

	            ++this.ticksInAir;

		    	if(this.getShooter() != null && this.getShooterUuid() == this.getShooter().getUniqueID())
		    	{
		            double d1 = this.getShooter().posX - this.posX;
		            double d2 = this.getShooter().posZ - this.posZ;

//					this.posX = this.getShooter().posX - Math.sin((this.ticksExisted + (45 /maxAmount) * amount) * Math.PI / 22.5D) * distance;
//					this.posY = this.getShooter().posY + this.getShooter().height / 3;
//					this.posZ = this.getShooter().posZ - Math.cos((this.ticksExisted + (45 / maxAmount) * amount) * Math.PI / 22.5D) * distance;

					this.setPosition(this.getShooter().posX - Math.sin((this.ticksExisted + (45 /maxAmount) * amount) * Math.PI / 22.5D) * distance, this.getShooter().posY + this.getShooter().height / 3, this.getShooter().posZ - Math.cos((this.ticksExisted + (45 / maxAmount) * amount) * Math.PI / 22.5D) * distance);


					this.rotationYaw = -((float)MathHelper.atan2(d1, d2)) * (180F / (float)Math.PI);
		    	}
		    	else
		    	{
		    		this.setDead();
		    	}
            }
        }
        else
        {
            this.setDead();
        }

        if (!this.world.isRemote)
        {

            for (Entity entity : this.world.getEntitiesWithinAABB(Entity.class, this.getEntityBoundingBox().grow(0.8D, 0.0D, 0.8D)))
            {
                this.onImpact(entity);
            }

			if ((this.getDamageTaken() >= this.maxLife && this.getDamageTaken() != -1) || (this.ticksAlive < this.ticksInAir && this.ticksAlive != -1))
			{
				this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, this.getDeathSound(), this.getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
			    this.setDead();
			}
        }
    }

    public void onImpact(Entity targetIn)
    {

        EntityLivingBase owner = this.getShooter();

        if(targetIn instanceof IProjectile)
        {

    		this.world.playSound((EntityPlayer)null, this.posX, this.posY, this.posZ, SoundEvents.ITEM_SHIELD_BREAK, this.getSoundCategory(), 1.0F, 3.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);

    		this.setDamageTaken(this.getDamageTaken() + 1);
    		targetIn.setDead();
        }
    }

    public boolean isBurning()
    {
        return false;
    }

    /**
     * Sets the damage taken from the last hit.
     */
    public void setDamageTaken(int damageTaken)
    {
        this.dataManager.set(DAMAGE_TAKEN, Integer.valueOf(damageTaken));
    }

    /**
     * Gets the damage taken from the last hit.
     */
    public int getDamageTaken()
    {
        return ((Integer)this.dataManager.get(DAMAGE_TAKEN)).intValue();
    }

	@Nullable
	protected SoundEvent getDeathSound() {
		return SoundEvents.BLOCK_GLASS_BREAK;
	}

	public static void registerEntity(Class<? extends Entity> clazz, ResourceLocation registryName, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerModEntity(registryName, clazz, registryName.getResourceDomain() + "." + registryName.getResourcePath(), RegistryHandler.entityId++, TF2Core.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates);
	}

}
