package com.leonardo.minecraft.factions.entities.impl;

import com.leonardo.minecraft.factions.FactionFlagType;
import com.leonardo.minecraft.factions.entities.FactionFlag;
import lombok.Data;

@Data
public class FactionFlagImpl implements FactionFlag {

    private Long id;
    private long factionId;
    private FactionFlagType factionFlagType;
    private Object value;

    public <O> O getValue(Class<O> aClass) {
        return aClass.cast(value);
    }

}
