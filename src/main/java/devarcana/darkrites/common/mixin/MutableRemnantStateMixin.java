package devarcana.darkrites.common.mixin;

import ladysnake.requiem.common.impl.remnant.MutableRemnantState;
import moriyashiine.bewitchment.common.entity.interfaces.FamiliarAccessor;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MutableRemnantState.class, remap = false)
public class MutableRemnantStateMixin {
    @Inject(at = @At("HEAD"), method = "canDissociateFrom", cancellable = true)
    private void canDissociateFrom(MobEntity possessed, CallbackInfoReturnable<Boolean> callbackInfo) {
        if (((FamiliarAccessor) possessed).getFamiliar()) {
            callbackInfo.setReturnValue(true);
        }
    }
}
