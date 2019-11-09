package tf2.event;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import tf2.TFItems;
import tf2.entity.mob.enemy.EntityEnemyMTT4;
import tf2.entity.mob.enemy.EntityTM02;
import tf2.entity.mob.enemy.EntityTM06;
import tf2.entity.mob.frend.EntityEvent1;
import tf2.entity.mob.frend.EntityEvent2;
import tf2.entity.mob.frend.EntityEvent3;

public class TFDeathEvent
{
	protected HashMap<String, InventoryPlayer> keepInveontoryMap = new HashMap();
	private static final Map<UUID, InventoryPlayer> playerKeepsMap = new HashMap<>();

	@SubscribeEvent
	public void onLivingDeathEvent(LivingDeathEvent event)
	{
		EntityLivingBase living = event.getEntityLiving();

		if (living.world.isRemote) return;
		if (living instanceof EntityPlayer && !living.world.getGameRules().getBoolean("keepInventory"))
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			for (int inv = 0; inv < 36; inv++)
			{
				ItemStack item = player.inventory.getStackInSlot(inv);

				if (item != null && item.getItem() == TFItems.ITEM_LOCK)
				{
					player.inventory.deleteStack(item);

					InventoryPlayer keepInventory = new InventoryPlayer(null);
					UUID playerUUID = player.getUniqueID();

					keepAllArmor(player, keepInventory);
					keepOffHand(player, keepInventory);
					for (int i = 0; i < player.inventory.mainInventory.size(); i++)
					{
						keepInventory.mainInventory.set(i, player.inventory.mainInventory.get(i).copy());
						player.inventory.mainInventory.set(i, ItemStack.EMPTY);
					}
					//this.keepInveontoryMap.put(player.getDisplayNameString(), keepInventory);
					playerKeepsMap.put(playerUUID, keepInventory);
				}
			}
		}
	}

	private static void keepAllArmor(EntityPlayer player, InventoryPlayer keepInventory)
	{
		for (int i = 0; i < player.inventory.armorInventory.size(); i++)
		{
			keepInventory.armorInventory.set(i, player.inventory.armorInventory.get(i).copy());
			player.inventory.armorInventory.set(i, ItemStack.EMPTY);
		}
	}

	private static void keepOffHand(EntityPlayer player, InventoryPlayer keepInventory)
	{
		for (int i = 0; i < player.inventory.offHandInventory.size(); i++)
		{
			keepInventory.offHandInventory.set(i, player.inventory.offHandInventory.get(i).copy());
			player.inventory.offHandInventory.set(i, ItemStack.EMPTY);
		}
	}

	@SubscribeEvent
	public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent event)
	{
		EntityPlayer player = event.player;
		InventoryPlayer keepInventory = playerKeepsMap.remove(player.getUniqueID());

		if (keepInventory != null)
		{
			NonNullList<ItemStack> displaced = NonNullList.create();
			for (int i = 0; i < player.inventory.armorInventory.size(); i++)
			{
				ItemStack kept = keepInventory.armorInventory.get(i);
				if (!kept.isEmpty())
				{
					ItemStack existing = player.inventory.armorInventory.set(i, kept);
					if (!existing.isEmpty())
					{
						displaced.add(existing);
					}
				}
			}

			for (int i = 0; i < player.inventory.offHandInventory.size(); i++)
			{
				ItemStack kept = keepInventory.offHandInventory.get(i);
				if (!kept.isEmpty())
				{
					ItemStack existing = player.inventory.offHandInventory.set(i, kept);
					if (!existing.isEmpty())
					{
						displaced.add(existing);
					}
				}
			}

			for (int i = 0; i < player.inventory.mainInventory.size(); i++)
			{
				ItemStack kept = keepInventory.mainInventory.get(i);
				if (!kept.isEmpty())
				{
					ItemStack existing = player.inventory.mainInventory.set(i, kept);
					if (!existing.isEmpty())
					{
						displaced.add(existing);
					}
				}
			}
		}
	}

	@SubscribeEvent
	public static void onPlayerLogout(PlayerLoggedOutEvent event)
	{
		dropStoredItems(event.player);
	}
	private static void dropStoredItems(EntityPlayer player)
	{
		InventoryPlayer keepInventory = playerKeepsMap.remove(player.getUniqueID());
		if (keepInventory != null)
		{
			keepInventory.player = player;
			keepInventory.dropAllItems();
		}
	}

	@SubscribeEvent
	public void onDeathScoreEvent(LivingDeathEvent event)
	{
		EntityLivingBase living = event.getEntityLiving();

		if (living instanceof EntityEnemyMTT4)
		{
			List k = living.world.getEntitiesWithinAABB(EntityEvent1.class, living.getEntityBoundingBox().grow(40.0D));
			for (int u = 0; u < k.size(); ++u)
			{
				EntityEvent1 target = (EntityEvent1) k.get(u);

				int targetCount = target.getCount();
				if(targetCount > 0)
				{
					--target.count;
					break;
				}
			}
		}

		if (living instanceof EntityTM06)
		{
			List k = living.world.getEntitiesWithinAABB(EntityEvent2.class, living.getEntityBoundingBox().grow(40.0D));
			for (int u = 0; u < k.size(); ++u)
			{
				EntityEvent2 target = (EntityEvent2) k.get(u);

				int targetCount = target.getCount();
				if(targetCount > 0)
				{
					--target.count;
					break;
				}
			}
		}

		if (living instanceof EntityTM02)
		{
			List k = living.world.getEntitiesWithinAABB(EntityEvent3.class, living.getEntityBoundingBox().grow(40.0D));
			for (int u = 0; u < k.size(); ++u)
			{
				EntityEvent3 target = (EntityEvent3) k.get(u);

				int targetCount = target.getCount();
				if(targetCount > 0)
				{
					--target.count;
					break;
				}
			}
		}
	}
}
