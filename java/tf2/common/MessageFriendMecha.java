package tf2.common;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import tf2.entity.mob.frend.EntityFriendMecha;

public class MessageFriendMecha implements IMessage
{
	private int entitiyID;
	public int key;
	public int index;

	public MessageFriendMecha()
	{
		// none
	}

	public MessageFriendMecha(Entity entity, int key)
	{
		this.entitiyID = entity.getEntityId();
		this.key = key;
		this.index = -1;
	}

	public MessageFriendMecha(Entity entity, int key, int index)
	{
		this.entitiyID = entity.getEntityId();
		this.key = key;
		this.index = index;
	}

	@Override
	public void fromBytes(ByteBuf buf)
	{
		this.entitiyID = buf.readInt();
		this.key = buf.readInt();
		this.index = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf)
	{
		buf.writeInt(this.entitiyID);
		buf.writeInt(this.key);
		buf.writeInt(this.index);
	}


	@Nullable
	public EntityFriendMecha getEntityMecha(World world)
	{
		Entity entity = world.getEntityByID(this.entitiyID);

		if (entity instanceof EntityFriendMecha)
		{
			return (EntityFriendMecha) entity;
		}

		return (EntityFriendMecha) null;
	}
}
