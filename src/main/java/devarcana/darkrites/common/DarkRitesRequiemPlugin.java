package devarcana.darkrites.common;

import ladysnake.requiem.api.v1.RequiemPlugin;
import ladysnake.requiem.api.v1.event.requiem.PossessionStartCallback;
import moriyashiine.bewitchment.common.entity.interfaces.FamiliarAccessor;
import net.minecraft.nbt.CompoundTag;

public class DarkRitesRequiemPlugin implements RequiemPlugin {
    @Override
    public void onRequiemInitialize() {
        PossessionStartCallback.EVENT.register(DarkRites.id("allow_familiars"), (target, possessor, simulate) -> {
            CompoundTag entityTag = new CompoundTag();
            target.saveSelfToTag(entityTag);

            if (entityTag.contains("Owner") && possessor.getUuid().equals(entityTag.getUuid("Owner"))) {
                if (((FamiliarAccessor) target).getFamiliar()) {
                    return PossessionStartCallback.Result.ALLOW;
                }
            }

            return PossessionStartCallback.Result.PASS;
        });
    }
}
