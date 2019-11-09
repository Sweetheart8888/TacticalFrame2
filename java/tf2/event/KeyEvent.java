package tf2.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tf2.client.ClientProxy;
import tf2.common.MessageKeyPressed;
import tf2.common.PacketHandler;
import tf2.items.guns.ItemTFGuns;

public class KeyEvent
{
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onUPEvent(LivingUpdateEvent event)
	{
		EntityLivingBase target = event.getEntityLiving();
		ItemStack itemstack = target.getHeldItemMainhand();
		if (target instanceof EntityPlayer)
		{
			if (target != null)
			{
				EntityPlayer player = (EntityPlayer) target;

				if (itemstack != null && itemstack.getItem() instanceof ItemTFGuns)
				{
					if (ClientProxy.keyReload.isPressed() && player.openContainer != null)
					{
						PacketHandler.INSTANCE.sendToServer(new MessageKeyPressed(1));
					}
				}
			}
		}
	}
}
