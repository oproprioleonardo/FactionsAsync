package com.leonardo.minecraft.factions.entities.impl;

import com.google.common.collect.Sets;
import com.leonardo.minecraft.factions.UserRole;
import com.leonardo.minecraft.factions.entities.MinecraftUser;
import lombok.Data;

import java.util.Set;

@Data
public class MinecraftUserImpl implements MinecraftUser {

    private Long id;
    private String username = "";
    private long factionId = 0;
    private UserRole userRole = UserRole.ANYONE;
    private int kills = 0;
    private int deaths = 0;
    private long lastActivityMillis = 0;
    private double power = 0.0;
    private double powerMax = 0.0;
    private transient Set<String> invitations = Sets.newHashSet();
    private transient boolean seeingChunk = false;
    private transient boolean adminMode = false;
    private transient boolean mapAutoUpdating = false;

}
