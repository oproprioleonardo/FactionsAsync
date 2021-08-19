package com.leonardo.minecraft.factions.entities;

import com.leonardo.minecraft.factions.FactionFlagType;
import com.leonardo.minecraft.factions.database.Entity;

public interface FactionFlag extends Entity {

    long getFactionId();

    void setFactionId(long id);

    FactionFlagType getFactionFlagType();

    void setFactionFlagType(FactionFlagType flagType);

    Object getValue();

    void setValue(Object value);

    <O> O getValue(Class<O> aClass);

}
