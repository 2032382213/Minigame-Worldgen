package org.power.minigame.worldgen;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.power.minigame.worldgen.config.Config;
import org.power.minigame.worldgen.utils.TranslatableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static net.minecraft.server.command.CommandManager.literal;

public class Minigame_WorldGen implements ModInitializer {
    public static final Logger logger = LoggerFactory.getLogger("Minigame-Worldgen");
    private static final Map<String, Object> example = new HashMap<>();
    public static final Config CONFIG;
    public static MinecraftServer server;

    static {
        example.put("blocked_dimensions", new ArrayList<>());
        example.put("world_generate", new HashMap<>());
        CONFIG = new Config("worldgen.toml", example);
    }

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> dispatcher.register(literal("minigame-wg")
                .requires((p) -> p.hasPermissionLevel(p.getServer().getOpPermissionLevel()))
                .then(literal("reload")
                        .executes((p) -> {
                            CONFIG.reload();
                            p.getSource().sendFeedback(() -> TranslatableUtils.getByKey("reload-msg"), true);
                            return 1;
                        })
                )
        ));
        ServerLifecycleEvents.SERVER_STARTED.register((s) -> server=s);
    }
}
