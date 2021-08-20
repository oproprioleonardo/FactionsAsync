package com.leonardo.minecraft.factions.entities;

import com.leonardo.minecraft.factions.database.Entity;
import org.bukkit.Chunk;

public interface FactionChunk extends Entity {

    long getFactionId();

    void setFactionId(long factionId);

    int getX();

    void setX(int x);

    int getZ();

    void setZ(int z);

    String getWorldName();

    void setWorldName(String worldName);

    Chunk getChunk();


}
