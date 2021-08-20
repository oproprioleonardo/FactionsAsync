package com.leonardo.minecraft.factions.services;

import com.leonardo.minecraft.factions.database.Service;
import com.leonardo.minecraft.factions.entities.MinecraftUser;
import com.leonardo.minecraft.factions.managers.Manager;
import io.smallrye.mutiny.Uni;

public interface MUserService extends Service<MinecraftUser> {

    Uni<MinecraftUser> readByUsername(String username);

    Uni<MinecraftUser> readByUsername(String username, Manager<MinecraftUser> manager);

}
