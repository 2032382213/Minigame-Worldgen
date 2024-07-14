package org.power.minigame.worldgen.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import org.power.minigame.worldgen.Minigame_WorldGen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "moveToWorld", at = @At(value = "HEAD"), cancellable = true)
    private void moveToWorldMixin(ServerWorld serverWorld, CallbackInfoReturnable<Entity> cir) {
        if (Minigame_WorldGen.CONFIG.get("blocked_dimensions", ArrayList.class).contains(serverWorld.getDimensionKey().getValue().toString())) {
            cir.cancel();
        }
    }
}
