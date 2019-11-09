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
public class RenderDimension<T extends Entity> extends Render<T>
{
	private static final ResourceLocation tex = new ResourceLocation("tf2:textures/entity/dimension.png");

	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/entity/dimension.obj"));

    public RenderDimension(RenderManager renderManager)
    {
		super(renderManager);
	}

    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	this.bindEntityTexture(entity);

    	GlStateManager.pushMatrix();

    	GlStateManager.translate(x, y, z);
    	GlStateManager.rotate(180F, 0F, 1F, 0F);
    	GlStateManager.scale(5F, 1F, 5F);
        GlStateManager.enableRescaleNormal();

        tankk.renderPart("entity");

        GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		float lastx = OpenGlHelper.lastBrightnessX;
		float lasty = OpenGlHelper.lastBrightnessY;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);

		tankk.renderPart("entity");

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