package tf2.tile.gui;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.TFBlocks;
import tf2.TFItems;
import tf2.tile.container.ContainerMachineStation;

@SideOnly(Side.CLIENT)
public class GuiMachineStation extends GuiContainer
{
	private static final ResourceLocation craftingTableGuiTextures = new ResourceLocation("tf2:textures/gui/container/machinestation.png");

	private int updateCount;
	private int changeSlot;

	private GuiRecipeButton nextRecipeButton;
	private GuiRecipeButton previousRecipeButton;
	private int currentRecipeIndex;
	private ItemStack itemid;

	public GuiMachineStation(InventoryPlayer playerInv, World worldIn, BlockPos blockPosition)
	{
		super(new ContainerMachineStation(playerInv, worldIn, blockPosition));
		this.xSize = 244;
		this.ySize = 210;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY)
	{
		this.fontRenderer.drawString(I18n.format("gui.machinestation", new Object[0]), 8, 6, 0xFFFF00);
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 0xFFFF00);
	}

	@Override
	public void updateScreen()
	{
		super.updateScreen();
		++this.updateCount;
		if (this.updateCount % 20 == 0)
		{
			++this.changeSlot;
		}

		this.nextRecipeButton.enabled = this.currentRecipeIndex < 4;
		this.previousRecipeButton.enabled = this.currentRecipeIndex > 0;
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton)
	{
		boolean flag = false;

		if (par1GuiButton == this.nextRecipeButton)
		{
			++this.currentRecipeIndex;
			flag = true;
		}
		else if (par1GuiButton == this.previousRecipeButton)
		{
			--this.currentRecipeIndex;
			flag = true;
		}

		if (flag)
		{
			ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
			DataOutputStream dataoutputstream = new DataOutputStream(bytearrayoutputstream);

			try
			{
				dataoutputstream.writeInt(this.currentRecipeIndex);
			} catch (Exception exception)
			{
				exception.printStackTrace();
			}
		}
	}

	@Override
	public void initGui()
	{
		super.initGui();
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.allowUserInput = true;
		this.buttonList.add(this.nextRecipeButton = new GuiRecipeButton(1, i + 225, j + 113, true));
		this.buttonList.add(this.previousRecipeButton = new GuiRecipeButton(2, i + 183, j + 113, false));
		this.currentRecipeIndex = 0;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialRenderTick, int xMouse, int yMouse)
	{
		//GL11.glPushMatrix();
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(craftingTableGuiTextures);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);
		RenderHelper.enableGUIStandardItemLighting();
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		//GL11.glPopMatrix();
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		RenderHelper.enableStandardItemLighting();
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);

		int originPosX = (this.width - this.xSize) / 2;
		int originPosY = (this.height - this.ySize) / 2;

		ItemStack[] stackResult = { null };
		ItemStack[] stackRecipe = null;

		int index = this.currentRecipeIndex;

		switch (index)
		{
		case 0:
			stackResult = new ItemStack[] { new ItemStack(TFItems.SPAWNFM, 1, 4) };
			stackRecipe = new ItemStack[] {
					ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY,
					new ItemStack(TFBlocks.MACHINE_CHASSIS), new ItemStack(TFItems.PARTS, 1, 1), new ItemStack(TFBlocks.MACHINE_CHASSIS),
					ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY
			};
			break;
		case 1:
			stackResult = new ItemStack[] { new ItemStack(TFItems.SPAWNFM, 1, 0) };
			stackRecipe = new ItemStack[] {
					ItemStack.EMPTY, new ItemStack(TFItems.PARTS, 1, 2), ItemStack.EMPTY,
					new ItemStack(TFBlocks.MACHINE_CHASSIS), new ItemStack(TFItems.DEVELOP_CHIP_0), new ItemStack(TFBlocks.MACHINE_CHASSIS),
					ItemStack.EMPTY, new ItemStack(TFItems.PARTS, 1, 0), ItemStack.EMPTY
			};
			break;
		case 2:
			stackResult = new ItemStack[] { new ItemStack(TFItems.SPAWNFM, 1, 1), new ItemStack(TFItems.SPAWNFM, 1, 2), new ItemStack(TFItems.SPAWNFM, 1, 3) };
			stackRecipe = new ItemStack[] {
					ItemStack.EMPTY, new ItemStack(TFItems.PARTS, 1, 2), ItemStack.EMPTY,
					new ItemStack(TFBlocks.MACHINE_CHASSIS), new ItemStack(TFItems.DEVELOP_CHIP_0), new ItemStack(TFBlocks.MACHINE_CHASSIS),
					ItemStack.EMPTY, new ItemStack(TFItems.PARTS, 1, 1), ItemStack.EMPTY
			};
			break;
		case 3:
			stackResult = new ItemStack[] { new ItemStack(TFItems.SPAWNFM, 1, 9) };
			stackRecipe = new ItemStack[] {
					ItemStack.EMPTY, new ItemStack(TFBlocks.MACHINE_CHASSIS), ItemStack.EMPTY,
					new ItemStack(TFItems.PARTS, 1, 7), new ItemStack(TFItems.DEVELOP_CHIP_0), new ItemStack(TFItems.PARTS, 1, 7),
					ItemStack.EMPTY, new ItemStack(TFItems.PARTS, 1, 0), ItemStack.EMPTY
			};
			break;
		case 4:
			stackResult = new ItemStack[] { new ItemStack(TFItems.SPAWNFM, 1, 5), new ItemStack(TFItems.SPAWNFM, 1, 6), new ItemStack(TFItems.SPAWNFM, 1, 7), new ItemStack(TFItems.SPAWNFM, 1, 8) };
			stackRecipe = new ItemStack[] {
					ItemStack.EMPTY, new ItemStack(TFItems.PARTS, 1, 4), ItemStack.EMPTY,
					new ItemStack(TFBlocks.MACHINE_CHASSIS), new ItemStack(TFItems.DEVELOP_CHIP_1), new ItemStack(TFBlocks.MACHINE_CHASSIS),
					ItemStack.EMPTY, new ItemStack(TFItems.PARTS, 1, 1), ItemStack.EMPTY
			};
			break;

		}

		for (int slot = 0; slot < stackResult.length; slot++)
		{
			this.drawItem(mouseX, mouseY, partialTicks, stackResult[changeSlot % (stackResult.length)], 202, 17);
			this.renderToolTip(mouseX, mouseY, partialTicks, stackResult[changeSlot % (stackResult.length)], 202, 17);
		}

		for (int slot = 0; slot < stackRecipe.length; slot++)
		{
			if (!stackRecipe[slot].isEmpty())
			{
				int heightX = 184;
				int heightY = 57;
				if (slot < 3)
				{}
				else if (slot < 6)
				{
					heightY += 18;
				}
				else
				{
					heightY += 36;
				}
				this.drawItem(mouseX, mouseY, partialTicks, stackRecipe[slot], heightX + ((slot % 3) * 18), heightY);
			}
		}
		for (int slot = 0; slot < stackRecipe.length; slot++)
		{
			if (!stackRecipe[slot].isEmpty())
			{
				int heightX = 184;
				int heightY = 57;
				if (slot < 3)
				{}
				else if (slot < 6)
				{
					heightY += 18;
				}
				else
				{
					heightY += 36;
				}
				this.renderToolTip(mouseX, mouseY, partialTicks, stackRecipe[slot], heightX + ((slot % 3) * 18), heightY);
			}
		}

		this.renderHoveredToolTip(mouseX, mouseY);
	}

	private void drawItem(int mouseX, int mouseY, float partialTicks, ItemStack stack, int x, int y)
	{
		int originPosX = (this.width - this.xSize) / 2;
		int originPosY = (this.height - this.ySize) / 2;

		itemRender.renderItemAndEffectIntoGUI(stack, originPosX + x, originPosY + y);
	}

	private void renderToolTip(int mouseX, int mouseY, float partialTicks, ItemStack stack, int x, int y)
	{
		if (this.isPointInRegion(x, y, 16, 16, mouseX, mouseY))
		{
			this.renderToolTip(stack, mouseX, mouseY);
		}
	}

	@SideOnly(Side.CLIENT)
	static class GuiRecipeButton extends GuiButton
	{
		private final boolean field_146157_o;

		public GuiRecipeButton(int p_i1095_1_, int p_i1095_2_, int p_i1095_3_, boolean p_i1095_4_)
		{
			super(p_i1095_1_, p_i1095_2_, p_i1095_3_, 12, 17, "");
			this.field_146157_o = p_i1095_4_;
		}

		/**
		 * Draws this button to the screen.
		 */
		public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
		{
			if (this.visible)
			{
				p_146112_1_.getTextureManager().bindTexture(craftingTableGuiTextures);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				boolean flag = p_146112_2_ >= this.x && p_146112_3_ >= this.y && p_146112_2_ < this.x + this.width && p_146112_3_ < this.y + this.height;
				int k = 222;
				int l = 0;

				if (!this.enabled)
				{
					l += this.width * 2;
				}
				else if (flag)
				{
					l += this.width;
				}

				if (!this.field_146157_o)
				{
					k += this.height;
				}

				this.drawTexturedModalRect(this.x, this.y, l, k, this.width, this.height);
			}
		}
	}
}