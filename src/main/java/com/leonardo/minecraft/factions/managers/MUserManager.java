package com.leonardo.minecraft.factions.managers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.leonardo.minecraft.factions.entities.MinecraftUser;
import com.leonardo.minecraft.factions.services.MUserService;
import io.smallrye.mutiny.Uni;
import lombok.Getter;
import org.ehcache.Cache;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.time.Duration;

@Getter
@Singleton
public class MUserManager implements Manager<MinecraftUser> {

    private final Cache<String, MinecraftUser> cache =
            CacheManagerBuilder.newCacheManagerBuilder().build()
                               .createCache("minecraft_users",
                                            CacheConfigurationBuilder
                                                    .newCacheConfigurationBuilder(String.class, MinecraftUser.class,
                                                                                  ResourcePoolsBuilder.heap(2000))
                                                    .withExpiry(
                                                            ExpiryPolicyBuilder.timeToIdleExpiration(
                                                                    Duration.ofSeconds(300)
                                                            )
                                                    ));
    @Inject
    private MUserService service;

    public Uni<MinecraftUser> require(String username) {
        return getCache().containsKey(username) ? Uni.createFrom().item(getCache().get(username)) :
               getService().readByUsername(username);
    }

    @Override
    public Uni<MinecraftUser> load(Uni<MinecraftUser> uni) {
        return uni.onItem().invoke(user -> getCache().putIfAbsent(user.getUsername(), user));
    }

}
