package tf2.common;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class MessageCupola implements IMessage
{
	public int key;
	public int x;
	public int y;
	public int z;

	 public MessageCupola(){}

     public MessageCupola(int button,int x, int y, int z)
     {
         this.key = button;
         this.x = x;
         this.y = y;
         this.z = z;
     }

     @Override
     public void fromBytes(ByteBuf buf) {
         this.key = buf.readInt();
         this.x = buf.readInt();
         this.y = buf.readInt();
         this.z = buf.readInt();
     }


     @Override
     public void toBytes(ByteBuf buf) {
         buf.writeInt(this.key);
         buf.writeInt(this.x);
         buf.writeInt(this.y);
         buf.writeInt(this.z);
     }
}
