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
import tf2.entity.mob.enemy.EntityTM02;

@SideOnly(Side.CLIENT)
public class RenderTM02 extends Render
{
	private static final ResourceLocation tftexture = new ResourceLocation("tf2:textures/mob/tm02.png");
	private static final ResourceLocation tfeyes = new ResourceLocation("tf2:textures/mob/tm02eye.png");
	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/mob/tm02.obj"));

	public RenderTM02(RenderManager renderManager)
	{
		super(renderManager);
	}

	public void doRender(Entity entity, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityTM02) entity, entityX, entityY, entityZ, p_76986_8_, p_76986_9_);
	}

	public void doRender(EntityTM02 entity, double entityX, double entityY, double entityZ, float p_76986_8_, float p_76986_9_)
	{
		this.bindEntityTexture(entity);

		GlStateManager.pushMatrix();
		GlStateManager.translate((float) entityX, (float) entityY, (float) entityZ);
		GlStateManager.rotate(180F, 0.0F, 1.0F, 0.0F);
		GlStateManager.enableRescaleNormal();
		if(entity.deathTime > 0)
		{
			GlStateManager.color(0.1F, 0.1F, 0.1F, 1F);
		}
		if (entity.hurtTime > 0)
        {
			GlStateManager.color(0.3F, 0.3F, 0.3F, 1F);
        }

		double d0 = entity.posX - entity.prevPosX;
		double d1 = entity.posZ - entity.prevPosZ;
		float f = (float)(d0 * d0 + d1 * d1);

		entity.rotationYaw = entity.rotationYawHead;

		GlStateManager.rotate(180.0F - entity.rotationYaw, 0.0F, 1.0F, 0.0F);

		if(f > 0F)
		{
			GlStateManager.translate(0F, -0.15F, 0F);
		}
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

	private void renderlegs(EntityTM02 entity)
	{
		GlStateManager.pushMatrix();
		tankk.renderPart("rightleg1");
		tankk.renderPart("rightleg2");
		tankk.renderPart("leftleg1");
		tankk.renderPart("leftleg2");
		GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1EntityLiving)
	{
		return this.tftexture;
	}
}
