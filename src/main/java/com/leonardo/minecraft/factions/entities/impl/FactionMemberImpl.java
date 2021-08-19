package com.leonardo.minecraft.factions.entities.impl;

import com.leonardo.minecraft.factions.entities.FactionMember;
import lombok.Data;

@Data
public class FactionMemberImpl implements FactionMember {

    private Long id;
    private long factionId;
    private long userId;

}
