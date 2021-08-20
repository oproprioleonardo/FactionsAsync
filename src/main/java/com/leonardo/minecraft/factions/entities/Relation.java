package com.leonardo.minecraft.factions.entities;

import com.leonardo.minecraft.factions.RelationType;
import com.leonardo.minecraft.factions.database.Entity;

public interface Relation extends Entity {

    long getFactionId1();

    void setFactionId1(long id);

    long getFactionId2();

    void setFactionId2(long id);

    RelationType getRelationType();

    void setRelationType(RelationType relationType);

}
