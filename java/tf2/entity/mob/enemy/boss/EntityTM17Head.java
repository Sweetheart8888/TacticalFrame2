package tf2.entity.mob.enemy.boss;

import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.util.EnumParticleTypes;

public class EntityTM17Head extends MultiPartEntityPart
{

	public EntityTM17Head(IEntityMultiPart parent, String partName, float width, float height)
	{
		super(parent, partName, width, height);
		this.ignoreFrustumCheck = true;
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		++this.ticksExisted;

        double d0 = (double)this.posX + 0.4D;
        double d1 = (double)this.posY + 1.0D;
        double d2 = (double)this.posZ + 0.1D;
        double d3 = 0.22D;
        double d4 = 0.27D;

        if(((EntityTM17)this.parent).healthCount <= 0)
        {
            this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }
	}
}
