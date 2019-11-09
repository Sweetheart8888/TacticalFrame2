package tf2.client.mobrender;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;
import tf2.entity.mob.frend.EntityMTT3;

public class RenderMTT3 extends Render
{
	private static final ResourceLocation tftexture = new ResourceLocation("tf2:textures/mob/mtt3.png");
	private static final ResourceLocation tfeyes = new ResourceLocation("tf2:textures/mob/mtt3_light.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/mob/mtt3.obj"));

	public RenderMTT3(RenderManager renderManager)
	{
		super(renderManager);
	}

	public void doRender(Entity p_76986_1_, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityMTT3) p_76986_1_, entityX, entityY, entityZ, p_76986_8_, p_76986_9_);
		this.shadowSize = 0.55F;
	}



	public void doRender(EntityMTT3 p_76986_1_, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
	{
		this.bindEntityTexture(p_76986_1_);

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) entityX, (float) entityY, (float) entityZ);
		GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
		GlStateManager.enableRescaleNormal();
		if (p_76986_1_.deathTime > 0)
		{
			GlStateManager.color(0.1F, 0.1F, 0.1F, 1F);
		}
		if (p_76986_1_.hurtTime > 0)
		{
			GlStateManager.color(0.1F, 0.2F, 0.2F, 1F);
		}
		p_76986_1_.rotationYaw = p_76986_1_.rotationYawHead;

		GlStateManager.rotate(180.0F - p_76986_1_.rotationYaw, 0.0F, 1.0F, 0.0F);
		this.renderTier(p_76986_1_);
		tankk.renderPart("body");

		this.bindTexture(tfeyes);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		float lastx = OpenGlHelper.lastBrightnessX;
		float lasty = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		tankk.renderPart("body");
		this.renderTier(p_76986_1_);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastx, lasty);

		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		GlStateManager.enableLighting();

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	private void renderTier(EntityMTT3 entity)
	{
		double d0 = entity.posX - entity.prevPosX;
		double d1 = entity.posZ - entity.prevPosZ;
		float f = (float) (d0 * d0 + d1 * d1);

		GlStateManager.pushMatrix();
		if(f > 0F)
		{

			int x = entity.ticksExisted;
			GlStateManager.translate(0.0F, 0.302F, 0.0F);
			GlStateManager.translate(0.0F, 0.0F, 0.579F);
			GlStateManager.rotate(x * 20F, 1.0F, 0.0F, 0.0F);
			GlStateManager.translate(0.0F, 0.0F, -0.579F);
			GlStateManager.translate(0.0F, -0.302F, 0.0F);
		}
		tankk.renderPart("tire1");
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		if(f > 0.01F)
		{
			int x = entity.ticksExisted;
			GlStateManager.translate(0.0F, 0.302F, 0.0F);
			GlStateManager.translate(0.0F, 0.0F, 0.002F);
			GlStateManager.rotate(x * 20F, 1.0F, 0.0F, 0.0F);
			GlStateManager.translate(0.0F, 0.0F, -0.002F);
			GlStateManager.translate(0.0F, -0.302F, 0.0F);
		}
		tankk.renderPart("tire2");
		GlStateManager.popMatrix();

		GlStateManager.pushMatrix();
		if(f > 0.01F)
		{
			int x = entity.ticksExisted;
			GlStateManager.translate(0.0F, 0.302F, 0.0F);
			GlStateManager.translate(0.0F, 0.0F, -0.574F);
			GlStateManager.rotate(x * 20F, 1.0F, 0.0F, 0.0F);
			GlStateManager.translate(0.0F, 0.0F, 0.574F);
			GlStateManager.translate(0.0F, -0.302F, 0.0F);
		}
		tankk.renderPart("tire3");
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving)
	{
		return this.tftexture;
	}
}
