package tf2.entity.projectile.player;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import tf2.common.TFExplosion;
import tf2.entity.projectile.EntityTFProjectile;

public class EntityGrenadeHe extends EntityTFProjectile
{
	public EntityGrenadeHe(World worldIn)
	{
		super(worldIn);
		this.setSize(0.2F, 0.2F);
	}

	public EntityGrenadeHe(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public EntityGrenadeHe(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
		this.setTickAir(80);
	}

	@Override
    public void setEntityDead()
    {
		TFExplosion.doExplosion(this.world, this.thrower, this.posX, this.posY, this.posZ, 3.5D, this.getDamage());
    	this.world.createExplosion((Entity) null, this.posX, this.posY,this.posZ, 0.0F, false);
    	super.setDead();
    }

	@Override
    public void setDead()
	{
    	this.world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
		super.setDead();
	}

	@Override
	public void isGravity()
	{
		if (!this.inGround)
		{
			this.motionY -= 0.03D;
		}
	}


    @Override
    public void inGround()
    {
    	this.inGround = true;
    	this.motionX *= 0D;
        this.motionY *= 0D;
        this.motionZ *= 0D;
    }

	@Override
	public void onHit(RayTraceResult raytraceResultIn)
	{
		Entity entity = raytraceResultIn.entityHit;

		if (entity != null)
		{
			DamageSource damagesource = this.damageSource();

			if (entity.attackEntityFrom(damagesource, 0F))
			{
				if (entity instanceof EntityLivingBase)
				{
					EntityLivingBase entitylivingbase = (EntityLivingBase) entity;

					if (this.thrower instanceof EntityLivingBase)
					{
						EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.thrower);
						EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) this.thrower, entitylivingbase);
					}

					this.bulletHit(entitylivingbase);
					entitylivingbase.hurtResistantTime = 0;

					if (this.thrower != null && entitylivingbase != this.thrower && entitylivingbase instanceof EntityPlayer && this.thrower instanceof EntityPlayerMP)
					{
						((EntityPlayerMP) this.thrower).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
					}
				}

				this.motionX *= -0.02D;
				this.motionY *= -0.1D;
				this.motionZ *= -0.02D;
				this.rotationYaw += 180.0F;
				this.prevRotationYaw += 180.0F;
			}
		}
		else
		{
			this.motionX *= 0D;
			this.motionY *= 0D;
			this.motionZ *= 0D;
			this.onGround = true;
		}
	}
}
