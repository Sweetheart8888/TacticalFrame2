package tf2.tile.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import tf2.TF2Core;
import tf2.TFItems;
import tf2.entity.mob.frend.EntityFriendMecha;
import tf2.items.ItemSpawnFriendMecha;
import tf2.tile.container.ContainerMechaDock;
import tf2.tile.tileentity.TileEntityMechaDock;

public class GuiMechaDock extends GuiContainer {
	private final static ResourceLocation GUI_BACKGROUND = new ResourceLocation("tf2:textures/gui/container/mechadock.png");

	private final TileEntityMechaDock tileEntity;

	public GuiMechaDock(EntityPlayer inventory, TileEntityMechaDock tileEntity)
	{
		super(new ContainerMechaDock(inventory, tileEntity));
		this.tileEntity = tileEntity;
		this.xSize = 176;
		this.ySize = 166;
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

		if(tileEntity.getStackInSlot(0).getItem() == TFItems.SPAWNFM)
		{
			ItemStack itemStack = tileEntity.getStackInSlot(0);
			ItemSpawnFriendMecha item = (ItemSpawnFriendMecha)itemStack.getItem();
			EntityFriendMecha entity = (EntityFriendMecha) item.getSpawnCreature(tileEntity.getWorld(), itemStack);

			this.fontRenderer.drawString(entity.getDisplayName().getUnformattedText(), 8, 18, 0x00DDCC);

			NBTTagCompound nbt = itemStack.getTagCompound();

			if (nbt != null)
			{
				int color = 0x00DDCC;

				if(nbt.getInteger("tf.mechaLevel") >=  entity.maxLevel - 1)
				{
					color = 0xF3F781;
				}
				else if((nbt.getInteger("tf.mechaLevel") >= 29 && !TF2Core.CONFIG.spawnMobTMtier2))
				{
					color = 0xff5555;
				}
				else if((nbt.getInteger("tf.mechaLevel") >= 59 && !TF2Core.CONFIG.spawnMobTMtier3))
				{
					color = 0xff5555;
				}

				this.fontRenderer.drawString("Lv: " + (nbt.getInteger("tf.mechaLevel") + 1), 111, 18, color);

				if(nbt.getInteger("tf.mechaHealth") >  nbt.getInteger("tf.mechaMaxHealth") / 2)
				{
					color = 0x00DDCC;
				}
				else if(nbt.getInteger("tf.mechaHealth") >  nbt.getInteger("tf.mechaMaxHealth") / 4)
				{
					color = 0xF3F781;
				}
				else
				{
					color = 0xff5555;
				}

				this.fontRenderer.drawString("Health: " + nbt.getInteger("tf.mechaHealth") + ".0 / " + nbt.getInteger("tf.mechaMaxHealth") + ".0", 8, 30, color);
				//this.fontRenderer.drawString("オーナー: " + item.getLastKnownUsername(UUID.fromString(nbt.getString("tf.mechaOwner"))), 8, 39, 0x00DDCC);


				if(nbt.getInteger("tf.mechaHealth") < nbt.getInteger("tf.mechaMaxHealth"))
				{
					int repairTime = tileEntity.getField(0) / 20;

					int minuts = repairTime / 60;
					int seconds = repairTime % 60;

					if(seconds % 60 <= 9)
					{
						//this.fontRenderer.drawString("修理完了まで あと " + minuts + ":0" + seconds, 8, 48, 0x00DDCC);
						this.fontRenderer.drawString(I18n.format("gui.mechadock.time", new Object[] {minuts, seconds}), 8, 48, 0x00DDCC);
					}
					else
					{
						//this.fontRenderer.drawString("修理完了まで あと " + minuts + ":" + seconds, 8, 48, 0x00DDCC);
						this.fontRenderer.drawString(I18n.format("gui.mechadock.time1", new Object[] {minuts, seconds}), 8, 48, 0x00DDCC);
					}
				}
				else
				{
					//this.fontRenderer.drawString("修理完了", 8, 48, 0x00DDCC);
					this.fontRenderer.drawString(I18n.format("gui.mechadock.repair", new Object[0]), 8, 48, 0x00DDCC);
				}

			}
			else
			{
				this.fontRenderer.drawString("Lv: 1" , 111, 18, 0x00DDCC);
				this.fontRenderer.drawString(I18n.format("gui.mechadock.desc"), 8, 30, 0x00DDCC);
				this.fontRenderer.drawString(I18n.format("gui.mechadock.desc1"), 8, 39, 0x00DDCC);
			}
		}
		else
		{
			this.fontRenderer.drawString(I18n.format("gui.mechadock.desc"), 8, 30, 0x00DDCC);
			this.fontRenderer.drawString(I18n.format("gui.mechadock.desc1"), 8, 39, 0x00DDCC);
		}
		this.fontRenderer.drawString(I18n.format("container.inventory", new Object[0]), 8, 72, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialRenderTick, int xMouse, int yMouse)
	{
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_BACKGROUND);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, this.xSize, this.ySize);

	}
}