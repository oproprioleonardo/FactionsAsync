package com.leonardo.minecraft.factions.managers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.leonardo.minecraft.factions.entities.Faction;
import com.leonardo.minecraft.factions.services.FactionService;
import io.smallrye.mutiny.Uni;
import lombok.Getter;
import org.ehcache.Cache;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

import java.time.Duration;

@Singleton
@Getter
public class FactionManager implements Manager<Faction> {

    private final Cache<String, Faction> cache =
            CacheManagerBuilder.newCacheManagerBuilder().build()
                               .createCache("factions",
                                            CacheConfigurationBuilder
                                                    .newCacheConfigurationBuilder(
                                                            String.class,
                                                            Faction.class,
                                                            ResourcePoolsBuilder
                                                                    .heap(2000))
                                                    .withExpiry(
                                                            ExpiryPolicyBuilder
                                                                    .timeToIdleExpiration(
                                                                            Duration.ofSeconds(
                                                                                    300)
                                                                    )
                                                    ));
    @Inject
    private FactionService service;

    public Uni<Faction> require(String tag) {
        return getCache().containsKey(tag) ? Uni.createFrom().item(getCache().get(tag)) :
               getService().readByTag(tag);
    }

    @Override
    public Uni<Faction> load(Uni<Faction> uni) {
        return uni.onItem().invoke(faction -> getCache().putIfAbsent(faction.getTag(), faction));
    }
}
