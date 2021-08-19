package com.leonardo.minecraft.factions;

import lombok.Getter;

import java.util.Arrays;

public enum UserRole {

    ANYONE("Nenhum", 4, 0),
    RECRUIT("Recruta", 3, 1),
    MEMBER("Membro", 2, 2),
    OFFICER("Oficial", 1, 3),
    LEADER("Líder", 0, 4);

    @Getter
    private final String label;
    @Getter
    private final int position;
    @Getter
    private final int id;

    UserRole(String label, int position, int id) {
        this.label = label;
        this.position = position;
        this.id = id;
    }

    public static UserRole fromId(int id) {
        return Arrays.stream(UserRole.values()).filter(userRole -> userRole.getId() == id).findFirst().orElse(ANYONE);
    }

}
