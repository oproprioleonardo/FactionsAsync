package com.leonardo.minecraft.factions.entities.impl;

import com.leonardo.minecraft.factions.entities.Faction;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FactionImpl implements Faction {

    private Long id;
    private String tag = "";
    private String name = "";
    private String leaderUsername = "";
    private String description = "";
    private String motd = "";
    private boolean nexusSpawned = false;
    private long createdAtMillis = System.currentTimeMillis();
    private transient boolean allyFire = false;
    private transient boolean inAttack = false;

}
