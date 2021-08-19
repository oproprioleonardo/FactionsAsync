package com.leonardo.minecraft.factions.entities;

import com.leonardo.minecraft.factions.database.Entity;
import org.bukkit.entity.EntityType;

public interface FactionSpawner extends Entity, SpawnerGive {

    long getFactionId();

    void setFactionId(long id);

    int getAmount();

    void setAmount(int amount);

    EntityType getEntityType();

    void setEntityType(EntityType entityType);

}
