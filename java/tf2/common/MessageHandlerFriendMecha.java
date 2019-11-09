package tf2.common;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tf2.TF2Core;
import tf2.entity.mob.frend.EntityFriendMecha;
import tf2.entity.mob.frend.EntityGynoid;

public class MessageHandlerFriendMecha implements IMessageHandler<MessageFriendMecha, IMessage>
{

	@Override
	public IMessage onMessage(MessageFriendMecha message, MessageContext ctx)
	{
		World world = ctx.getServerHandler().player.getEntityWorld();

		EntityPlayer entityPlayer = ctx.getServerHandler().player;
		EntityFriendMecha entityMecha = message.getEntityMecha(world);



		switch(message.key)
		{
		case 0:
			if (entityMecha != null)
			{
				switch(entityMecha.getMechaMode())
				{
				case 0: entityMecha.setMechaMode((byte)1); break;
				case 1:
					entityMecha.setMechaMode((byte)2);
					entityMecha.setHomePosAndDistance(entityMecha.getPosition(), 4);
					break;
				case 2:
					entityMecha.setMechaMode((byte)0);
					entityMecha.setAttackTarget((EntityLivingBase) null);
					break;
				}
			}
			break;
		case 1:

			if(!entityMecha.isBeingRidden() && entityMecha.canBeingRidden)
			{
				entityMecha.setMechaMode((byte)3);
				entityPlayer.startRiding(entityMecha);
				entityPlayer.closeScreen();
			}
			break;
		case 2:
			if(!world.isRemote)
				entityMecha.setDead();
			break;
//
//		case 3:
//			if (entityPlayer.openContainer != null && entityPlayer.openContainer instanceof ContainerGynoidTrade)
//			{
//				((ContainerGynoidTrade)entityPlayer.openContainer).changeRecipe(message.index);
//			}
//			break;
//
//		case 4:
//			if (entityPlayer.openContainer != null && entityPlayer.openContainer instanceof ContainerGynoidTrade)
//			{
//				((ContainerGynoidTrade)entityPlayer.openContainer).takeRecipe(message.index);
//			}
//			break;

		case 5:
			entityPlayer.closeScreen();
			if(entityMecha instanceof EntityGynoid)
			{
				entityPlayer.openGui(TF2Core.INSTANCE, TF2Core.guiGynoid, entityMecha.getEntityWorld(), entityMecha.getEntityId(), 0, 0);
			}
			break;
		case 6:
        	if(entityMecha.getMechaLevel() < entityMecha.maxLevel - 1)
        	{
        		int expLevel = (entityMecha.getMechaLevel() / 2) + 1;

				if(expLevel != 0)
				{
					if(entityPlayer.experienceLevel >= expLevel)
					{
						if(!entityPlayer.world.isRemote)
						{
							entityPlayer.addExperienceLevel(-expLevel);
						}
						entityMecha.isUpLevel();
					}
				}
        	}

			break;
		}
		return (IMessage) null;
	}

}
