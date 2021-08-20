package com.leonardo.minecraft.factions.managers;

import com.github.benmanes.caffeine.cache.AsyncCache;
import com.leonardo.minecraft.factions.database.Entity;
import com.leonardo.minecraft.factions.database.Service;
import io.smallrye.mutiny.Uni;

public interface Manager<O extends Entity> {

    AsyncCache<String, O> getCache();

    Service<O> getService();

    Uni<O> require(String id);

    void load(O obj);

}
