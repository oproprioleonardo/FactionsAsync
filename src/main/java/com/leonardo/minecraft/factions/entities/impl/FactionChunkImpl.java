package com.leonardo.minecraft.factions.entities.impl;

import com.leonardo.minecraft.factions.entities.FactionChunk;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

@Data
public class FactionChunkImpl implements FactionChunk {

    private Long id;
    private long factionId;
    private String worldName;
    private int x;
    private int z;

    @Override
    public Chunk getChunk() {
        return Bukkit.getWorld(this.worldName).getChunkAt(this.x, this.z);
    }
}
