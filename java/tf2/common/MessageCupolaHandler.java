package tf2.common;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import tf2.tile.tileentity.TileEntityCupola;

public class MessageCupolaHandler implements IMessageHandler<MessageCupola, IMessage>
{
    @Override
    public IMessage onMessage(MessageCupola message, MessageContext ctx)
    {

        World world = ctx.getServerHandler().player.world;
        BlockPos pos = new BlockPos(message.x, message.y, message.z);
    	TileEntity tileentity = world.getTileEntity(pos);
        if(tileentity instanceof TileEntityCupola)
        {
        	TileEntityCupola tile = (TileEntityCupola)tileentity;

        	switch(message.key)
        	{
        	case 1:
        		tile.startCooking(1);
        		break;
        	case 2:
        		tile.startCooking(2);
        		break;
        	case 3:
        		tile.startCooking(3);
        		break;
        	case 4:
        		tile.startCooking(4);
        		break;
        	case 5:
        		tile.startCooking(5);
        		break;
        	case 6:
        		tile.startCooking(6);
        		break;
        	case 7:
        		tile.startCooking(7);
        		break;
        	case 8:
        		tile.startCooking(8);
        		break;
        	case 9:
        		tile.startCooking(9);
        		break;
        	}
        }

        return null;
    }
}