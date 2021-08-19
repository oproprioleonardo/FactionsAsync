package com.leonardo.minecraft.factions;

import lombok.Getter;

public enum FactionPermissions {

    ACCEPT_TPA(1, "Aceitar pedido de teletransporte"),
    USE_CONTAINERS(2, "Usar contâiners"),
    BREAK_BLOCKS(3, "Quebrar blocos"),
    PUT_BLOCKS(4, "Colocar blocos"),
    RECRUITIMENT(5, "Recrutar membros"),
    PROMOTE_MEMBERS(6, "Promover membros"),
    DEMOTE_MEMBERS(7, "Demover membros"),
    KICK_MEMBERS(8, "Expulsar membros"),
    USE_REDSTONE(9, "Utilzar redstone no território da facção"),
    MAKE_EXPLOSIONS(10, "Fazer explosões no território da facção"),
    BREAK_SPAWNERS(11, "Quebrar spawners da facção"),
    CLAIM_TERRITORIES(12, "Conquistar territórios"),
    UNCLAIM_TERRITORIES(13, "Rejeitar territórios"),
    ATTACK_TERRITORIES(14, "Declarar ataque ao território inimigo"),
    MANAGE_NEXUS(15, "Gerenciar nexus"),
    MANAGE_PERMISSIONS(16, "Gerenciar permissões"),
    SPAWN_NEXUS(17, "Gerar o nexus da facção"),
    SET_HOME(18, "Definir home no território da facção"),
    TP_TO_NEXUS(19, "Teletransportar para o nexus"),
    CHANGE_MOTD_DESC(20, "Mudar a motd e a descrição da facção"),
    MANAGE_SPAWNERS(21, "Gerenciar spawners da facção"),
    VIEW_CLAIM_COORDS(22, "Acessar coordenadas dos territórios da facção"),
    ADMIN(0, "Operador");

    @Getter
    private final int key;
    @Getter
    private final String label;


    FactionPermissions(int key, String label) {
        this.key = key;
        this.label = label;
    }
}
