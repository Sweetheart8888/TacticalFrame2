package tf2.items;

public class ItemAmmo extends ItemBase
{
	public ItemAmmo(String name, int damage)
	{
		super(name);
		this.setMaxStackSize(1);
		this.setMaxDamage(damage);
	}
}