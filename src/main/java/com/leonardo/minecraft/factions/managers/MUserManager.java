package com.leonardo.minecraft.factions.managers;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.leonardo.minecraft.factions.entities.MinecraftUser;
import com.leonardo.minecraft.factions.services.MUserService;
import io.smallrye.mutiny.Uni;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
@Singleton
public class MUserManager implements Manager<MinecraftUser> {

    private Cache<String, MinecraftUser> cache;
    @Inject
    private MUserService service;

    @Inject
    private void loadCache(MUserService service) {
        this.cache = Caffeine.newBuilder()
                             .expireAfterAccess(300, TimeUnit.SECONDS)
                             .removalListener(
                                     (String id, MinecraftUser user, RemovalCause removalCause) -> service.update(user)
                                                                                                          .await()
                                                                                                          .indefinitely())
                             .maximumSize(2_000)
                             .build();
    }

    public Uni<MinecraftUser> require(String username) {
        return Uni.createFrom().item(() -> this.cache.getIfPresent(username))
                  .onItem()
                  .ifNull()
                  .switchTo(this.service.readByUsername(username, this));
    }

    @Override
    public void load(MinecraftUser user) {
        this.cache.put(user.getUsername(), user);
    }

}
