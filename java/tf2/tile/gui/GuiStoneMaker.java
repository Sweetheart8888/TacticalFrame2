package tf2.tile.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TFItems;
import tf2.tile.container.ContainerStoneMaker;
import tf2.tile.tileentity.TileEntityStoneMaker;

@SideOnly(Side.CLIENT)
public class GuiStoneMaker extends GuiContainer
{
	private final static ResourceLocation GUI_BACKGROUND = new ResourceLocation("tf2:textures/gui/container/stonemaker.png");
	private TileEntityStoneMaker tileEntity;

	public GuiStoneMaker(EntityPlayer inventory, TileEntityStoneMaker tileEntity)
	{
		super(new ContainerStoneMaker(inventory, tileEntity));
		this.tileEntity = tileEntity;
		this.xSize = 176;
		this.ySize = 222;
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

		int i1;

		if (this.tileEntity.isBurning())
		{
			i1 = this.tileEntity.getCookProgressScaled(46);
			this.drawTexturedModalRect(x + 18, y + 13, 176, 0, i1 + 1, 31);
			i1 = this.tileEntity.getBurnTimeRemainingScaled(31);
			this.drawTexturedModalRect(x + 49, y + 47, 176, 31, i1 + 1, 14);
		}

		RenderHelper.enableGUIStandardItemLighting();
		GlStateManager.pushMatrix();
//		GL11.glDisable(GL11.GL_LIGHTING);
//		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
//		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		this.itemRender.zLevel = 100.0F;
        itemRender.renderItemAndEffectIntoGUI(new ItemStack(Items.COMPARATOR), x + 96, y + 13);
        itemRender.renderItemAndEffectIntoGUI(new ItemStack(TFItems.CRUSH_FILTER), x + 96, y + 31);
        itemRender.renderItemAndEffectIntoGUI(new ItemStack(TFItems.PARTICLE_FILTER), x + 96, y + 49);

        itemRender.renderItemAndEffectIntoGUI(new ItemStack(Blocks.STONE), x + 144, y + 13);
        itemRender.renderItemAndEffectIntoGUI(new ItemStack(Blocks.SAND), x + 144, y + 31);
        itemRender.renderItemAndEffectIntoGUI(new ItemStack(Blocks.CLAY), x + 144, y + 49);
        this.itemRender.zLevel = 0.0F;
//        GL11.glEnable(GL11.GL_LIGHTING);
//        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();

        if (this.isPointInRegion(96, 13, 16, 16, xMouse, yMouse))
        {
            this.renderToolTip(new ItemStack(Items.COMPARATOR), xMouse, yMouse);
        }
        if (this.isPointInRegion(96, 31, 16, 16, xMouse, yMouse))
        {
            this.renderToolTip(new ItemStack(TFItems.CRUSH_FILTER), xMouse, yMouse);
        }
        if (this.isPointInRegion(96, 49, 16, 16, xMouse, yMouse))
        {
            this.renderToolTip(new ItemStack(TFItems.PARTICLE_FILTER), xMouse, yMouse);
        }

        if (this.isPointInRegion(144, 13, 16, 16, xMouse, yMouse))
        {
            this.renderToolTip(new ItemStack(Blocks.STONE), xMouse, yMouse);
        }
        if (this.isPointInRegion(144, 31, 16, 16, xMouse, yMouse))
        {
            this.renderToolTip(new ItemStack(Blocks.SAND), xMouse, yMouse);
        }
        if (this.isPointInRegion(144, 49, 16, 16, xMouse, yMouse))
        {
            this.renderToolTip(new ItemStack(Blocks.CLAY), xMouse, yMouse);
        }
	}
}