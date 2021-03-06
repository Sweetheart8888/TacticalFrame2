package tf2.client.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import objmodel.AdvancedModelLoader;
import objmodel.IModelCustom;

@SideOnly(Side.CLIENT)
public class RenderAreaHeal<T extends Entity> extends Render<T>
{
	private static final ResourceLocation tex = new ResourceLocation("tf2:textures/entity/potiongear.png");

	private static final IModelCustom tankk = AdvancedModelLoader.loadModel(new ResourceLocation("tf2:textures/entity/potiongear.obj"));

	public RenderAreaHeal(RenderManager renderManager)
	{
		super(renderManager);
	}

	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks)
	{
		this.bindEntityTexture(entity);

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);

		float t = entity.ticksExisted;
		GlStateManager.rotate(t * 20F, 0F, 1F, 0F);

		GlStateManager.enableRescaleNormal();
		tankk.renderPart("weapon");
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