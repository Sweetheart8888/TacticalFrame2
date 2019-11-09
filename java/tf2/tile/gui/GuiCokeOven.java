package tf2.tile.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.tile.container.ContainerCokeOven;
import tf2.tile.tileentity.TileEntityCokeOven;

@SideOnly(Side.CLIENT)
public class GuiCokeOven extends GuiContainer {
	private final static ResourceLocation GUI_BACKGROUND = new ResourceLocation("tf2:textures/gui/container/cokeoven.png");
	private TileEntityCokeOven tileEntity;

	public GuiCokeOven(EntityPlayer inventory, TileEntityCokeOven tileEntity)
	{
		super(new ContainerCokeOven(inventory, tileEntity));
		this.tileEntity = tileEntity;
		this.xSize = 176;
		this.ySize = 195;
	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString(I18n.format(this.tileEntity.getName(), new Object[0]), 8, 6, 0x404040);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialRenderTick, int xMouse, int yMouse)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_BACKGROUND);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

		int i1 = this.tileEntity.getProcessProgressScaled(0, 18);
		this.drawTexturedModalRect(x + 16, y + 38, 176, 0, i1 + 1, 4);
		i1 = this.tileEntity.getProcessProgressScaled(1, 18);
		this.drawTexturedModalRect(x + 34, y + 38, 176, 4, i1 + 1, 4);
		i1 = this.tileEntity.getProcessProgressScaled(2, 18);
		this.drawTexturedModalRect(x + 52, y + 38, 176, 8, i1 + 1, 4);
		i1 = this.tileEntity.getProcessProgressScaled(3, 18);
		this.drawTexturedModalRect(x + 70, y + 38, 176, 12, i1 + 1, 4);

		i1 = this.tileEntity.getProcessProgressScaled(4, 18);
		this.drawTexturedModalRect(x + 16, y + 60, 176, 16, i1 + 1, 4);
		i1 = this.tileEntity.getProcessProgressScaled(5, 18);
		this.drawTexturedModalRect(x + 34, y + 60, 176, 20, i1 + 1, 4);
		i1 = this.tileEntity.getProcessProgressScaled(6, 18);
		this.drawTexturedModalRect(x + 52, y + 60, 176, 24, i1 + 1, 4);
		i1 = this.tileEntity.getProcessProgressScaled(7, 18);
		this.drawTexturedModalRect(x + 70, y + 60, 176, 28, i1 + 1, 4);

		i1 = this.tileEntity.getOilProgressScaled(54);
		this.drawTexturedModalRect(x + 16, y + 78, 176, 32, i1 + 1, 9);
		i1 = this.tileEntity.getTarProgressScaled(54);
		this.drawTexturedModalRect(x + 16, y + 87, 176, 41, i1 + 1, 9);
	}
}