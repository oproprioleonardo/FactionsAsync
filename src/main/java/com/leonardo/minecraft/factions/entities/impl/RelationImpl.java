package com.leonardo.minecraft.factions.entities.impl;

import com.leonardo.minecraft.factions.RelationType;
import com.leonardo.minecraft.factions.entities.Relation;
import lombok.Data;

@Data
public class RelationImpl implements Relation {

    private Long id;
    private long factionId1;
    private long factionId2;
    private RelationType relationType = RelationType.NEUTRAL;

}
