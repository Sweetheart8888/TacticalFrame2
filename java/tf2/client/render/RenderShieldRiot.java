package tf2.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelShield;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import tf2.tile.tileentity.TileEntityShield2;

public class RenderShieldRiot extends TileEntitySpecialRenderer<TileEntityShield2>
{

	public static final ResourceLocation SHIELD_BASE_TEXTURE = new ResourceLocation("tf2:textures/entity/riot_shield.png");
	private final ModelShield modelShield = new ModelShield();

	@Override
	public void render(TileEntityShield2 tes, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
	{
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.SHIELD_BASE_TEXTURE);

		GlStateManager.pushMatrix();
		GlStateManager.scale(1.0F, -1.0F, -1.0F);
		this.modelShield.render();
		GlStateManager.popMatrix();

	}

}