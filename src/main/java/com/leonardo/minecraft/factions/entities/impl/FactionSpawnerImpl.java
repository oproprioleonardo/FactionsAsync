package com.leonardo.minecraft.factions.entities.impl;

import com.leonardo.minecraft.factions.entities.FactionSpawner;
import lombok.Data;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

@Data
public class FactionSpawnerImpl implements FactionSpawner {

    private Long id;
    private long factionId;
    private EntityType entityType;
    private int amount = 0;

    @Override
    public void giveSpawner(Player player, int amount) {

    }
}
