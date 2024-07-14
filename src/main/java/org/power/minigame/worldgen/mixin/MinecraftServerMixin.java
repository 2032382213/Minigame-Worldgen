package org.power.minigame.worldgen.mixin;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Lifecycle;
import net.minecraft.registry.*;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.entry.RegistryEntryOwner;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.dimension.DimensionOptions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.power.minigame.worldgen.Minigame_WorldGen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    @Shadow public abstract DynamicRegistryManager.Immutable getRegistryManager();

    @SuppressWarnings("unchecked")
    @Redirect(method = "createWorlds", at = @At(value = "INVOKE", target = "Lnet/minecraft/registry/DynamicRegistryManager$Immutable;get(Lnet/minecraft/registry/RegistryKey;)Lnet/minecraft/registry/Registry;"))
    private Registry<DimensionOptions> doesNotGenerateBlockedDimension(DynamicRegistryManager.Immutable instance, RegistryKey registryKey) {
        Registry<DimensionOptions> result = instance.get(registryKey);
        Set<Map.Entry<RegistryKey<DimensionOptions>,DimensionOptions>> result_entry = new HashSet<>(result.getEntrySet());
        result_entry.removeIf(entry -> Minigame_WorldGen.CONFIG.get("blocked_dimensions", ArrayList.class).contains(String.valueOf(entry.getKey().getValue())));
        Map<RegistryKey<DimensionOptions>,DimensionOptions> map = result_entry.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return new Registry<>() {
            @Override
            public RegistryKey<? extends Registry<DimensionOptions>> getKey() {
                return null;
            }

            @Nullable
            @Override
            public Identifier getId(DimensionOptions object) {
                return null;
            }

            @Override
            public Optional<RegistryKey<DimensionOptions>> getKey(DimensionOptions object) {
                return Optional.empty();
            }

            @Override
            public int getRawId(@Nullable DimensionOptions object) {
                return 0;
            }

            @Nullable
            @Override
            public DimensionOptions get(@Nullable RegistryKey<DimensionOptions> registryKey) {
                return map.getOrDefault(registryKey, null);
            }

            @Nullable
            @Override
            public DimensionOptions get(@Nullable Identifier identifier) {
                return null;
            }

            @Override
            public Lifecycle getEntryLifecycle(DimensionOptions object) {
                return null;
            }

            @Override
            public Lifecycle getLifecycle() {
                return null;
            }

            @Override
            public Set<Identifier> getIds() {
                return Set.of();
            }

            @Override
            public Set<Map.Entry<RegistryKey<DimensionOptions>, DimensionOptions>> getEntrySet() {
                return map.entrySet();
            }

            @Override
            public Set<RegistryKey<DimensionOptions>> getKeys() {
                return Set.of();
            }

            @Override
            public Optional<RegistryEntry.Reference<DimensionOptions>> getRandom(Random random) {
                return Optional.empty();
            }

            @Override
            public boolean containsId(Identifier identifier) {
                return false;
            }

            @Override
            public boolean contains(RegistryKey<DimensionOptions> registryKey) {
                return false;
            }

            @Override
            public Registry<DimensionOptions> freeze() {
                return null;
            }

            @Override
            public RegistryEntry.Reference<DimensionOptions> createEntry(DimensionOptions object) {
                return null;
            }

            @Override
            public Optional<RegistryEntry.Reference<DimensionOptions>> getEntry(int i) {
                return Optional.empty();
            }

            @Override
            public Optional<RegistryEntry.Reference<DimensionOptions>> getEntry(RegistryKey<DimensionOptions> registryKey) {
                return Optional.empty();
            }

            @Override
            public RegistryEntry<DimensionOptions> getEntry(DimensionOptions object) {
                return null;
            }

            @Override
            public Stream<RegistryEntry.Reference<DimensionOptions>> streamEntries() {
                return Stream.empty();
            }

            @Override
            public Optional<RegistryEntryList.Named<DimensionOptions>> getEntryList(TagKey<DimensionOptions> tagKey) {
                return Optional.empty();
            }

            @Override
            public RegistryEntryList.Named<DimensionOptions> getOrCreateEntryList(TagKey<DimensionOptions> tagKey) {
                return null;
            }

            @Override
            public Stream<Pair<TagKey<DimensionOptions>, RegistryEntryList.Named<DimensionOptions>>> streamTagsAndEntries() {
                return Stream.empty();
            }

            @Override
            public Stream<TagKey<DimensionOptions>> streamTags() {
                return Stream.empty();
            }

            @Override
            public void clearTags() {

            }

            @Override
            public void populateTags(Map<TagKey<DimensionOptions>, List<RegistryEntry<DimensionOptions>>> map) {

            }

            @Override
            public RegistryEntryOwner<DimensionOptions> getEntryOwner() {
                return null;
            }

            @Override
            public RegistryWrapper.Impl<DimensionOptions> getReadOnlyWrapper() {
                return null;
            }

            @Nullable
            @Override
            public DimensionOptions get(int i) {
                return null;
            }

            @Override
            public int size() {
                return 0;
            }

            @NotNull
            @Override
            public Iterator<DimensionOptions> iterator() {
                return null;
            }
        };
    }
}
