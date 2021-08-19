package com.leonardo.minecraft.factions.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.leonardo.minecraft.factions.entities.Faction;
import com.leonardo.minecraft.factions.managers.Manager;
import com.leonardo.minecraft.factions.repositories.FactionRepository;
import com.leonardo.minecraft.factions.services.FactionService;
import io.smallrye.mutiny.Uni;
import lombok.Getter;
import org.ehcache.Cache;

@Singleton
public class FactionServiceImpl implements FactionService {

    @Inject
    @Getter
    private FactionRepository repository;

    @Override
    public Uni<Void> deleteById(Long id) {
        return this.repository.deleteById(id);
    }

    @Override
    public Uni<Faction> delete(Faction obj) {
        return this.repository.delete(obj);
    }

    @Override
    public Uni<Faction> readById(Long id) {
        return this.repository.readById(id);
    }

    @Override
    public Uni<Boolean> existsId(Long id) {
        return this.repository.existsId(id);
    }

    @Override
    public Uni<Boolean> existsTag(String tag) {
        return this.repository.existsTag(tag);
    }

    @Override
    public Uni<Faction> update(Faction obj, Manager<Faction> manager) {
        final Cache<String, Faction> cache = manager.getCache();
        if (cache.containsKey(obj.getTag())) {
            cache.replace(obj.getTag(), obj);
        }
        return this.repository.update(obj);
    }

    @Override
    public Uni<Faction> create(Faction obj) {
        return this.repository.create(obj);
    }

    @Override
    public Uni<Faction> update(Faction obj) {
        return this.repository.update(obj);
    }

    @Override
    public Uni<Faction> createOrUpdate(Faction obj) {
        return this.repository.createOrUpdate(obj);
    }

    @Override
    public Uni<Void> close() {
        return this.repository.close();
    }

    @Override
    public Uni<Void> createTable() {
        return this.repository.createTable();
    }

    @Override
    public Uni<Faction> readByTag(String tag) {
        return this.repository.readByTag(tag);
    }
}
