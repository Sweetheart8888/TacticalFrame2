package tf2.client.render;

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


@SideOnly(Side.CLIENT)
public class RenderSoundwave<T extends Entity> extends Render<T>
{
	private static final ResourceLocation tex = new ResourceLocation("tf2:textures/entity/shell.png");

	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/entity/bulletsoundwave.obj"));

    public RenderSoundwave(RenderManager renderManager)
    {
		super(renderManager);
	}

    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	this.bindEntityTexture(entity);

    	GlStateManager.pushMatrix();

    	GlStateManager.color(0.7F, 0F, 1F);
    	GlStateManager.translate(x, y + 0.5F, z);
    	GlStateManager.rotate(180F, 0F, 1F, 0F);

 		GlStateManager.rotate(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks , 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks + 180F, 1.0F, 0.0F, 0.0F);

 		GlStateManager.enableRescaleNormal();

 		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		float lastx = OpenGlHelper.lastBrightnessX;
		float lasty = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		tankk.renderPart("weapon");

		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lastx, lasty);
		OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO);
		GlStateManager.enableLighting();

		GlStateManager.disableRescaleNormal();

 		GlStateManager.popMatrix();


        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    @Override
	protected ResourceLocation getEntityTexture(T entity)
    {
		return tex;
	}
}