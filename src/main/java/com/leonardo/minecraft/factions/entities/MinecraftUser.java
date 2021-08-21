package com.leonardo.minecraft.factions.entities;

import com.leonardo.minecraft.factions.UserRole;
import com.leonardo.minecraft.factions.database.Entity;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

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
        return this.getFactionId() != 0;
    }

    default Player getPlayer() {
        return Bukkit.getPlayer(this.getUsername());
    }

    default OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(this.getUsername());
    }

}
