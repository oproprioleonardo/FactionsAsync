package com.leonardo.minecraft.factions;

import lombok.Getter;

@Getter
public enum FactionFlagType {

    MAX_MEMBERS("Máximo de membros");

    private final String label;

    FactionFlagType(String label) {
        this.label = label;
    }
}
