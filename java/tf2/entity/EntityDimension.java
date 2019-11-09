package tf2.entity;

import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TF2Core;
import tf2.client.particle.ParticleSphere;
import tf2.util.RegistryHandler;

public class EntityDimension extends Entity
{

	public EntityDimension(World worldIn)
	{
		super(worldIn);
		this.setSize(20.0F, 0.5F);
		this.ignoreFrustumCheck = true;
	}

	public EntityDimension(World worldIn, double x, double y, double z)
	{
		this(worldIn);
		this.setPosition(x, y, z);
		this.motionX = 0.0D;
		this.motionY = 0.0D;
		this.motionZ = 0.0D;
		this.prevPosX = x;
		this.prevPosY = y;
		this.prevPosZ = z;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance)
	{
		double d0 = this.getEntityBoundingBox().getAverageEdgeLength() * 4.0D;

		if (Double.isNaN(d0))
		{
			d0 = 4.0D;
		}

		d0 = d0 * 128.0D;
		return distance < d0 * d0;
	}

	@Override
	protected void entityInit()
	{}

	@Override
	protected boolean canTriggerWalking()
	{
		return false;
	}

	@Override
	public void onUpdate()
	{
		super.onUpdate();

		if (this.ticksExisted >= 800)
		{
			this.setDead();
		}

		if (this.world.isRemote)
		{
			this.generateRandomParticles();
		}
	}

	public static double py3d(double dx, double dy, double dz)
    {
    	double val = dx * dx + dy * dy + dz * dz;
    	return Math.sqrt(val);
    }

	@SideOnly(Side.CLIENT)
	protected void generateRandomParticles()
	{
		if (this.world.isRemote)
		{
			double a = 60D * (float) Math.sin(Math.toRadians(this.ticksExisted * 4 % 360));
			//360を60度で割る
			for (int i = 0; i < 3; ++i)
			{
				//ここで回転形が変わる
				//いったりきたり double ang = Math.toRadians(a + i);
				//一個の円 double ang = ticksInAir * 6.0F;
				//2個の円 double ang = ticksInAir * 3.0F;
				double ang = ticksExisted * 2.0F;
				//double ang = Math.toRadians(a + i);

				double r2 = 12.0D;
				double rx = this.posX + r2 * Math.cos(ang);
				double ry = this.posY - 0.5F;
				double rz = this.posZ + r2 * Math.sin(ang);

				Particle newEffect = new ParticleSphere.Factory().createParticle(0, this.world, rx, ry, rz, 0, -0.1D, 0);
				FMLClientHandler.instance().getClient().effectRenderer.addEffect(newEffect);


				double r3 = 24.0D;
				double rx2 = this.posX + r3 * Math.cos(ang);
				double ry2 = this.posY - 0.5F;
				double rz2 = this.posZ + r3 * Math.sin(ang);

				Particle newEffect2 = new ParticleSphere.Factory().createParticle(0, this.world, rx2, ry2, rz2, 0, -0.1D, 0);
				FMLClientHandler.instance().getClient().effectRenderer.addEffect(newEffect2);

				double r4 = 36.0D;
				double rx3 = this.posX + r4 * Math.cos(ang);
				double ry3 = this.posY - 0.5F;
				double rz3 = this.posZ + r4 * Math.sin(ang);

				Particle newEffect3 = new ParticleSphere.Factory().createParticle(0, this.world, rx3, ry3, rz3, 0, -0.1D, 0);
				FMLClientHandler.instance().getClient().effectRenderer.addEffect(newEffect3);
			}
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound)
	{}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound)
	{}

	public static void registerEntity(Class<? extends Entity> clazz, ResourceLocation registryName, String name, int trackingRange, int updateFrequency, boolean sendsVelocityUpdates)
	{
		EntityRegistry.registerModEntity(registryName, clazz, registryName.getResourceDomain() + "." + registryName.getResourcePath(), RegistryHandler.entityId++, TF2Core.INSTANCE, trackingRange, updateFrequency, sendsVelocityUpdates);
	}
}
