package com.leonardo.minecraft.factions.entities;

import com.leonardo.minecraft.factions.database.Entity;
import org.bukkit.Location;

public interface Nexus extends Entity {

    long getFactionId();

    void setFactionId(long id);

    double getLife();

    void setLife(double life);

    double getMaxLife();

    void setMaxLife(double maxLife);

    double getExperience();

    void setExperience(double experience);

    Location getLocation();

    void setLocation(Location location);

}
