package tf2.client.mobrender;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;
import tf2.entity.mob.frend.EntityMTT2;

public class RenderMTT2 extends Render
{
	private static final ResourceLocation tftexture = new ResourceLocation("tf2:textures/mob/mtt2.png");
	private static final ResourceLocation tfeyes = new ResourceLocation("tf2:textures/mob/mtt2_light.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/mob/mtt2.obj"));

	public RenderMTT2(RenderManager renderManager)
	{
		super(renderManager);
	}

	public void doRender(Entity p_76986_1_, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityMTT2) p_76986_1_, entityX, entityY, entityZ, p_76986_8_, p_76986_9_);
		this.shadowSize = 0.55F;
	}

	private float interpolateRotation(float p_77034_1_, float p_77034_2_, float p_77034_3_)
	{
		float f3;

		for (f3 = p_77034_2_ - p_77034_1_; f3 < -180.0F; f3 += 360.0F)
		{
			;
		}

		while (f3 >= 180.0F)
		{
			f3 -= 360.0F;
		}

		return p_77034_1_ + p_77034_3_ * f3;
	}

	public void doRender(EntityMTT2 p_76986_1_, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
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

		float f4 = MathHelper.wrapDegrees(p_76986_1_.rotationYawHead - p_76986_1_.rotationYaw);

		double d3 = Math.abs(f4);

		if (d3 > 70F)
		{
			p_76986_1_.rotationYaw = this.interpolateRotation(p_76986_1_.rotationYaw, p_76986_1_.rotationYawHead, 0.05F);
		}

		double d0 = p_76986_1_.posX - p_76986_1_.prevPosX;
		double d1 = p_76986_1_.posZ - p_76986_1_.prevPosZ;
		float f = (float) (d0 * d0 + d1 * d1);
		if (f > 0F)
		{
			p_76986_1_.rotationYaw = p_76986_1_.rotationYawHead;
		}

		this.renderhead(p_76986_1_);

		GlStateManager.rotate(180.0F - p_76986_1_.rotationYaw, 0.0F, 1.0F, 0.0F);

		tankk.renderPart("body");

		this.bindTexture(tfeyes);
		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		float lastx = OpenGlHelper.lastBrightnessX;
		float lasty = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		tankk.renderPart("body");

		GlStateManager.rotate(-(180.0F - p_76986_1_.rotationYaw), 0.0F, 1.0F, 0.0F);
		this.renderhead(p_76986_1_);

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastx, lasty);

		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		GlStateManager.enableLighting();

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();
	}

	private void renderhead(EntityMTT2 entity)
	{
		float f = entity.rotationPitch;
		f = MathHelper.clamp(f, -20F, 15F);
		GlStateManager.pushMatrix();
		GlStateManager.translate(0.0F, 1.5F, 0.0F);
		GlStateManager.rotate(180.0F - entity.rotationYawHead, 0.0F, 1.0F, 0.0F);
		GlStateManager.rotate(f, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0.0F, -1.5F, -0.0F);

		tankk.renderPart("head");
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving)
	{
		return this.tftexture;
	}
}
