package com.leonardo.minecraft.factions.managers;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.leonardo.minecraft.factions.entities.Faction;
import com.leonardo.minecraft.factions.services.FactionService;
import io.smallrye.mutiny.Uni;
import lombok.Getter;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Singleton
@Getter
public class FactionManager implements Manager<Faction> {

    private AsyncCache<String, Faction> cache;
    @Inject
    private FactionService service;

    @Inject
    private void loadCache(FactionService service) {
        this.cache = Caffeine.newBuilder()
                             .expireAfterAccess(300, TimeUnit.SECONDS)
                             .removalListener(
                                     (String id, Faction faction, RemovalCause removalCause) -> service.update(faction)
                                                                                                       .await()
                                                                                                       .indefinitely())
                             .maximumSize(2_000)
                             .buildAsync();
    }

    public Uni<Faction> require(String tag) {
        return Uni.createFrom().completionStage(this.cache.getIfPresent(tag)).onFailure()
                  .recoverWithUni(this.service.readByTag(tag));
    }

    @Override
    public void load(Faction faction) {
        this.cache.put(faction.getTag(), CompletableFuture.completedFuture(faction));
    }
}
