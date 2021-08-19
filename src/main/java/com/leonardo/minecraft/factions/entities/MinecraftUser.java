package com.leonardo.minecraft.factions.entities;

import com.leonardo.minecraft.factions.database.Entity;
import com.leonardo.minecraft.factions.UserRole;

import java.util.Set;

public interface MinecraftUser extends Entity {

    String getUsername();

    void setUsername(String username);

    long getFactionId();

    void setFactionId(long id);

    UserRole getUserRole();

    void setUserRole(UserRole userRole);

    int getKills();

    void setKills(int kills);

    int getDeaths();

    void setDeaths(int deaths);

    long getLastActivityMillis();

    void setLastActivityMillis(long lastActivityMillis);

    double getPowerMax();

    void setPowerMax(double powerMax);

    double getPower();

    void setPower(double power);

    Set<String> getInvitations();

    void setInvitations(Set<String> invitations);

    boolean isSeeingChunk();

    void setSeeingChunk(boolean seeingChunk);

    boolean isAdminMode();

    void setAdminMode(boolean adminMode);

    boolean isMapAutoUpdating();

    void setMapAutoUpdating(boolean mapAutoUpdating);

    default boolean hasFaction() {
        return getFactionId() != 0;
    }

}
