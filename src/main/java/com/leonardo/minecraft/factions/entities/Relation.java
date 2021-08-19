package com.leonardo.minecraft.factions.entities;

import com.leonardo.minecraft.factions.database.Entity;
import com.leonardo.minecraft.factions.RelationType;

public interface Relation extends Entity {

    long getFactionId1();

    void setFactionId1(long id);

    long getFactionId2();

    void setFactionId2(long id);

    RelationType getRelationType();

    void setRelationType(RelationType relationType);

}
