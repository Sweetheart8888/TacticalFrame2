package tf2.entity.projectile.player;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import tf2.entity.projectile.IFriendProjectile;

public class EntityFriendSoundwave extends EntityFriendBullet implements IFriendProjectile
{
	public EntityFriendSoundwave(World worldIn)
	{
		super(worldIn);
		this.setSize(1.2F, 1.2F);
	}

	public EntityFriendSoundwave(World worldIn, double x, double y, double z)
	{
		super(worldIn, x, y, z);
	}

	public EntityFriendSoundwave(World worldIn, EntityLivingBase throwerIn)
	{
		super(worldIn, throwerIn);
		this.setTickAir(70);
	}
	@Override
	public float inWaterSpeed()
	{
		return 0.99F;
	}
	@Override
	protected void onHit(RayTraceResult raytraceResultIn)
	{
		Entity entity = raytraceResultIn.entityHit;
		if (entity != null)
		{
			DamageSource damagesource = this.damageSource();

			if (entity.attackEntityFrom(damagesource, this.directHitDamage()))
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

				if (!(entity instanceof EntityEnderman))
				{
					this.setEntityDead();
				}
			}
			else
			{
				this.setEntityDead();
			}

		}
	}
}