package com.leonardo.minecraft.factions.entities;

import com.leonardo.minecraft.factions.database.Entity;

public interface FactionMember extends Entity {

    long getFactionId();

    void setFactionId(long id);

    long getUserId();

    void setUserId(long id);

}
