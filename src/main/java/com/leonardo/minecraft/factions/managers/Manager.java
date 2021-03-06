package com.leonardo.minecraft.factions.managers;

import com.github.benmanes.caffeine.cache.Cache;
import com.leonardo.minecraft.factions.database.Entity;
import com.leonardo.minecraft.factions.database.Service;
import io.smallrye.mutiny.Uni;

public interface Manager<O extends Entity> {

    Cache<String, O> getCache();

    Service<O> getService();

    Uni<O> require(String id);

    void load(O obj);

}
