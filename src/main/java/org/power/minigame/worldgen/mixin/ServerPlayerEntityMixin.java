package org.power.minigame.worldgen.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.network.packet.s2c.play.OverlayMessageS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.power.minigame.worldgen.Minigame_WorldGen;
import org.power.minigame.worldgen.utils.TranslatableUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
    @SuppressWarnings("UnreachableCode")
    @Inject(method = "moveToWorld", at = @At(value = "HEAD"), cancellable = true)
    private void moveToWorldMixin(ServerWorld serverWorld, CallbackInfoReturnable<Entity> cir) {
        ServerPlayerEntity player = ((ServerPlayerEntity) (Object)this);
        if (Minigame_WorldGen.CONFIG.get("blocked_dimensions", ArrayList.class).contains(serverWorld.getDimensionKey().getValue().toString())) {
            player.networkHandler.sendPacket(new OverlayMessageS2CPacket(TranslatableUtils.getByKey("try-to-join-blocked-dimensions", serverWorld.getDimensionKey().getValue().toString())));
            cir.cancel();
        }
    }
}
