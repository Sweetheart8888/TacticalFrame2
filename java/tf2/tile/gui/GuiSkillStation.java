package tf2.tile.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.tile.container.ContainerSkillStation;


@SideOnly(Side.CLIENT)
public class GuiSkillStation extends GuiContainer
{
    private static final ResourceLocation craftingTableGuiTextures = new ResourceLocation("tf2:textures/gui/container/skillstation.png");

    public GuiSkillStation(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition)
    {
    	super(new ContainerSkillStation(playerInv, worldIn, blockPosition));
        this.xSize = 176;
		this.ySize = 213;
    }
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
    {
        this.fontRenderer.drawString(I18n.format("gui.skill", new Object[0]), 8, 6, 0xFFFFFF);
        this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 0xFFFFFF);
    }


    @Override
	protected void drawGuiContainerBackgroundLayer(float partialRenderTick, int xMouse, int yMouse)
	{
    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(craftingTableGuiTextures);
		int x = (this.width  - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

	}
    @Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}
}