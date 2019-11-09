package tf2.client.mobrender;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.entity.mob.frend.EntityLicoris;

@SideOnly(Side.CLIENT)
public class RenderLicoris<T extends EntityLicoris> extends RenderLiving<T>
{
    private static final ResourceLocation COW_TEXTURES = new ResourceLocation("tf2:textures/mob/soldier_girl.png");

    public RenderLicoris(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn)
    {
        super(renderManagerIn, modelBaseIn, shadowSizeIn);
        this.addLayer(new LayerHeldItem(this));
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityLicoris entity)
    {
        return COW_TEXTURES;
    }
}