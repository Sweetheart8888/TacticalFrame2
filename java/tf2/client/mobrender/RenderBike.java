package tf2.client.mobrender;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;
import tf2.entity.mob.frend.EntityBike;

@SideOnly(Side.CLIENT)
public class RenderBike extends Render
{
	private static final ResourceLocation tftexture = new ResourceLocation("tf2:textures/mob/bike.png");
	private static final ResourceLocation tfeyes = new ResourceLocation("tf2:textures/mob/bikeeye.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/mob/bike.obj"));

	public RenderBike(RenderManager renderManager)
	{
		super(renderManager);
	}

	public void doRender(Entity entity, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityBike) entity, entityX, entityY, entityZ, p_76986_8_, p_76986_9_);
	}

	public void doRender(EntityBike entity, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
	{
		this.bindEntityTexture(entity);

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) entityX, (float) entityY, (float) entityZ);
		GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
		GlStateManager.enableRescaleNormal();
		if (entity.deathTime > 0)
		{
			GlStateManager.color(0.1F, 0.1F, 0.1F, 1F);
		}

		entity.rotationYaw = entity.rotationYawHead;

		GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);

		tankk.renderPart("body");
		this.renderlegs(entity);

		this.bindTexture(tfeyes);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		float lastx = OpenGlHelper.lastBrightnessX;
		float lasty = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		tankk.renderPart("body");
		this.renderlegs(entity);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastx, lasty);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		GlStateManager.enableLighting();

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	private void renderlegs(EntityBike entity)
	{
		float x = entity.ticksExisted;

		double d0 = entity.posX - entity.prevPosX;
		double d1 = entity.posZ - entity.prevPosZ;
		float f = (float) (d0 * d0 + d1 * d1);
		if (f > 0F)
		{
			this.renderleg(0, x * 23, 0);
		}
		else
		{
			GlStateManager.pushMatrix();
			tankk.renderPart("tire1");
			tankk.renderPart("tire2");
			GlStateManager.popMatrix();
		}
	}

	private void renderleg(float i0, float iii2, float ii)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, 0.4291F, 0F);
		GlStateManager.translate(0F, 0F, 1.5839F);
		GlStateManager.rotate(-(iii2), 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0F, -0.4291F, 0F);
		GlStateManager.translate(0F, 0F, -1.5839F);
		tankk.renderPart("tire1");
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, 0.4291F, 0F);
		GlStateManager.translate(0F, 0F, -0.6284F);
		GlStateManager.rotate(-(iii2), 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0F, -0.4291F, 0F);
		GlStateManager.translate(0F, 0F, 0.6284F);
		tankk.renderPart("tire2");
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving)
	{
		return this.tftexture;
	}
}
