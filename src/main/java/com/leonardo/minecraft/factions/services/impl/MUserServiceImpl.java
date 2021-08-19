package com.leonardo.minecraft.factions.services.impl;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.leonardo.minecraft.factions.database.Repository;
import com.leonardo.minecraft.factions.entities.MinecraftUser;
import com.leonardo.minecraft.factions.repositories.MUserRepository;
import com.leonardo.minecraft.factions.services.MUserService;
import io.smallrye.mutiny.Uni;

@Singleton
public class MUserServiceImpl implements MUserService {

    @Inject
    private MUserRepository repository;

    @Override
    public Uni<Void> deleteById(Long id) {
        return this.repository.deleteById(id);
    }

    @Override
    public Uni<MinecraftUser> delete(MinecraftUser obj) {
        return this.repository.delete(obj);
    }

    @Override
    public Uni<MinecraftUser> readByUsername(String username) {
        return this.repository.readByUsername(username);
    }

    @Override
    public Uni<MinecraftUser> readById(Long id) {
        return this.repository.readById(id);
    }

    @Override
    public Uni<Boolean> existsId(Long id) {
        return this.repository.existsId(id);
    }

    @Override
    public Uni<MinecraftUser> create(MinecraftUser obj) {
        return this.repository.create(obj);
    }

    @Override
    public Uni<MinecraftUser> update(MinecraftUser obj) {
        return this.repository.update(obj);
    }

    @Override
    public Uni<MinecraftUser> createOrUpdate(MinecraftUser obj) {
        return this.repository.createOrUpdate(obj);
    }

    @Override
    public Repository<MinecraftUser> getRepository() {
        return this.repository;
    }

    @Override
    public Uni<Void> close() {
        return this.repository.close();
    }

    @Override
    public Uni<Void> createTable() {
        return this.repository.createTable();
    }
}
