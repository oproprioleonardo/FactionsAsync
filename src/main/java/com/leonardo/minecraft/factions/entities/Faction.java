package com.leonardo.minecraft.factions.entities;

import com.leonardo.minecraft.factions.database.Entity;

public interface Faction extends Entity {

    String getTag();

    void setTag(String tag);

    String getName();

    void setName(String name);

    String getLeaderUsername();

    void setLeaderUsername(String leaderUsername);

    String getDescription();

    void setDescription(String description);

    String getMotd();

    void setMotd(String motd);

    boolean isNexusSpawned();

    void setNexusSpawned(boolean nexusSpawned);

    long getCreatedAtMillis();

    void setCreatedAtMillis(long createdAtMillis);

    boolean isInAttack();

    void setInAttack(boolean inAttack);
}
