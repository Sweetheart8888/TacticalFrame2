package tf2.common;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler
{
	public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("TacticalFrame2");

	public static void init()
	{
		INSTANCE.registerMessage(MessageKeyPressedHandler.class, MessageKeyPressed.class, 0, Side.SERVER);

		INSTANCE.registerMessage(MessageCupolaHandler.class, MessageCupola.class, 1, Side.SERVER);

//		INSTANCE.registerMessage(MessageSkillHandler.class, MessageSkill.class, 2, Side.SERVER);
//
		INSTANCE.registerMessage(MessageHandlerFriendMecha.class, MessageFriendMecha.class, 3, Side.SERVER);
	}
}
