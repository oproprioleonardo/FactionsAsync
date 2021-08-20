package com.leonardo.minecraft.factions.services;

import com.leonardo.minecraft.factions.database.Service;
import com.leonardo.minecraft.factions.entities.Faction;
import com.leonardo.minecraft.factions.managers.Manager;
import io.smallrye.mutiny.Uni;

public interface FactionService extends Service<Faction> {

    Uni<Faction> readByTag(String tag);

    Uni<Boolean> existsTag(String tag);

    Uni<Faction> readByTag(String tag, Manager<Faction> manager);
}
