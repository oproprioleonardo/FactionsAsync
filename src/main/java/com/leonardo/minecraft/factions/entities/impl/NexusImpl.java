package com.leonardo.minecraft.factions.entities.impl;

import com.leonardo.minecraft.factions.entities.Nexus;
import lombok.Data;
import org.bukkit.Location;

@Data
public class NexusImpl implements Nexus {

    private Long id;
    private long factionId;
    private double life;
    private double maxLife;
    private double experience;
    private Location location;

}
