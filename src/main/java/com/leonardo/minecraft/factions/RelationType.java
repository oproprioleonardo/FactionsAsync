package com.leonardo.minecraft.factions;

import lombok.Getter;

public enum RelationType {

    ENEMY("Inimigo"),
    ALLY("Aliado"),
    NEUTRAL("Neutro"),
    TRUCE("Trégua");

    @Getter
    private final String label;

    RelationType(String label) {
        this.label = label;
    }

}
