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
import tf2.entity.mob.enemy.EntityTM07;

@SideOnly(Side.CLIENT)
public class RenderTM07 extends Render
{
	private static final ResourceLocation tftexture = new ResourceLocation("tf2:textures/mob/tm07.png");
	private static final ResourceLocation tfeyes = new ResourceLocation("tf2:textures/mob/tm07_light.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/mob/tm07.obj"));

	public RenderTM07(RenderManager renderManager)
	{
		super(renderManager);
	}

	public void doRender(Entity entity, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityTM07) entity, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}

	public void doRender(EntityTM07 entity, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
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
		if (entity.hurtTime > 0)
		{
			GlStateManager.color(0.3F, 0.5F, 0.3F, 1F);
		}

		entity.rotationYaw = entity.rotationYawHead;

		GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);

		tankk.renderPart("body");
		this.renderlegs(entity);

		//start発光処理
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
		//end

		GlStateManager.disableRescaleNormal();
		GlStateManager.popMatrix();

	}

	private void renderlegs(EntityTM07 entity)
	{
		float f = entity.rotationPitch;
		if (f > 0)
		{
			f = 0;
		}

		GlStateManager.pushMatrix();
		GlStateManager.translate(0F, 0.453F, 0F);
		GlStateManager.translate(0F, 0F, 0.727F);
		GlStateManager.rotate(f, 1.0F, 0.0F, 0.0F);
		GlStateManager.translate(0.0F, 0F, -0.727F);
		GlStateManager.translate(0.0F, -0.453F, 0F);
		tankk.renderPart("weapon");
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving)
	{
		return this.tftexture;
	}
}
