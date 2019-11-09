package tf2.client.mobrender;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.entity.mob.frend.EntityEvent1;

@SideOnly(Side.CLIENT)
public class RenderEvent1<T extends EntityEvent1> extends RenderLiving<T>
{
    private static final ResourceLocation COW_TEXTURES = new ResourceLocation("tf2:textures/mob/soldier.png");

    public RenderEvent1(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn)
    {
        super(renderManagerIn, modelBaseIn, shadowSizeIn);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityEvent1 entity)
    {
        return COW_TEXTURES;
    }
}