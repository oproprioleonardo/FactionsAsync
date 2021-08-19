package com.leonardo.minecraft.factions.repositories;

import com.leonardo.minecraft.factions.database.Repository;
import com.leonardo.minecraft.factions.entities.MinecraftUser;
import io.smallrye.mutiny.Uni;

public interface MUserRepository extends Repository<MinecraftUser> {

    Uni<MinecraftUser> readByUsername(String username);

}
