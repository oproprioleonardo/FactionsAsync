package com.leonardo.minecraft.factions.repositories;

import com.leonardo.minecraft.factions.database.Repository;
import com.leonardo.minecraft.factions.entities.Faction;
import com.leonardo.minecraft.factions.entities.impl.FactionImpl;
import io.smallrye.mutiny.Uni;

public interface FactionRepository extends Repository<Faction> {

    Uni<Faction> readByTag(String tag);

    Uni<Boolean> existsTag(String tag);

}
