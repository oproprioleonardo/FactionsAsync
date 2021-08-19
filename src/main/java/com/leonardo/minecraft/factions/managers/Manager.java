package com.leonardo.minecraft.factions.managers;

import com.leonardo.minecraft.factions.database.Entity;
import com.leonardo.minecraft.factions.database.Service;
import io.smallrye.mutiny.Uni;
import org.ehcache.Cache;

public interface Manager<O extends Entity> {

    Cache<String, O> getCache();

    Service<O> getService();

    Uni<O> require(String id);

    Uni<O> load(Uni<O> obj);

}
