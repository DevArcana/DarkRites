package devarcana.darkrites.common.ritualfunction;

import ladysnake.requiem.api.v1.RequiemPlayer;
import ladysnake.requiem.api.v1.possession.PossessionComponent;
import ladysnake.requiem.api.v1.remnant.RemnantComponent;
import moriyashiine.bewitchment.api.BewitchmentAPI;
import moriyashiine.bewitchment.api.registry.RitualFunction;
import moriyashiine.bewitchment.common.item.TaglockItem;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.HashSet;
import java.util.Set;

public class DecayRitualFunction extends RitualFunction {
    public DecayRitualFunction() {
        super(ParticleTypes.ASH, null);
    }

    @Override
    public void start(ServerWorld world, BlockPos glyphPos, BlockPos effectivePos, Inventory inventory, boolean catFamiliar) {
        ItemStack taglock = null;
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (inventory.getStack(i).getItem() instanceof TaglockItem) {
                taglock = stack;
                break;
            }
        }

        if (taglock != null) {
            LivingEntity livingEntity = BewitchmentAPI.getTaglockOwner(world, taglock);

            if (livingEntity instanceof RequiemPlayer) {
                RequiemPlayer player = (RequiemPlayer) livingEntity;
                RemnantComponent remnant = RemnantComponent.get((PlayerEntity) player);

                if (remnant.getRemnantType().isDemon() && !player.asPossessor().isPossessing()) {
                    MobEntity body;

                    if (inventory.count(Items.BONE) > 0) {
                        body = EntityType.SKELETON.create(livingEntity.world);
                    } else {
                        body = EntityType.ZOMBIE.create(livingEntity.world);
                    }

                    if (body != null) {
                        body.copyPositionAndRotation(livingEntity);
                        livingEntity.world.spawnEntity(body);

                        remnant.setVagrant(true);
                        player.asPossessor().startPossessing(body);
                    }
                }
            }
        }

        super.start(world, glyphPos, effectivePos, inventory, catFamiliar);
    }
}
