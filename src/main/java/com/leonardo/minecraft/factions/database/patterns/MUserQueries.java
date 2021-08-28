package com.leonardo.minecraft.factions.database.patterns;

import com.google.common.collect.Lists;
import com.leonardo.minecraft.factions.UserRole;
import com.leonardo.minecraft.factions.database.QueryPatterns;
import com.leonardo.minecraft.factions.entities.MinecraftUser;
import com.leonardo.minecraft.factions.entities.impl.MinecraftUserImpl;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

public class MUserQueries extends QueryPatterns<MinecraftUser> {

    public MUserQueries() {
        super("m_users",
              "INSERT INTO m_users " +
              "(username, faction_id, user_role, kills, deaths, last_activity_millis, power, power_max) " +
              "VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
              "UPDATE m_users " +
              "set faction_id = ?, user_role = ?, kills = ?, deaths = ?, last_activity_millis = ?, power = ?, power_max = ? " +
              "WHERE id = ?",
              "INSERT INTO m_users (id, username, faction_id, user_role, kills, deaths, last_activity_millis, power, power_max) " +
              "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) " +
              "ON DUPLICATE KEY UPDATE " +
              "set faction_id = ?, user_role = ?, kills = ?, deaths = ?, last_activity_millis = ?, power = ?, power_max = ?");
    }

    @Override
    public Tuple getTupleToInsert(MinecraftUser obj) {
        return Tuple.from(Lists.newArrayList(
                obj.getUsername(),
                obj.getFactionId(),
                obj.getUserRole().getId(),
                obj.getKills(),
                obj.getDeaths(),
                obj.getLastActivityMillis(),
                obj.getPower(),
                obj.getPowerMax()
        ));
    }

    @Override
    public Tuple getTupleToUpdate(MinecraftUser obj) {
        return Tuple.from(Lists.newArrayList(
                obj.getFactionId(),
                obj.getUserRole().getId(),
                obj.getKills(),
                obj.getDeaths(),
                obj.getLastActivityMillis(),
                obj.getPower(),
                obj.getPowerMax(),
                obj.getId()
        ));
    }

    @Override
    public Tuple getTupleToInsertOrUpdate(MinecraftUser obj) {
        return Tuple.from(Lists.newArrayList(
                obj.getId(),
                obj.getUsername(),
                obj.getFactionId(),
                obj.getUserRole().getId(),
                obj.getKills(),
                obj.getDeaths(),
                obj.getLastActivityMillis(),
                obj.getPower(),
                obj.getPowerMax(),
                obj.getFactionId(),
                obj.getUserRole().getId(),
                obj.getKills(),
                obj.getDeaths(),
                obj.getLastActivityMillis(),
                obj.getPower(),
                obj.getPowerMax()
        ));
    }

    @Override
    public MinecraftUser buildObj(Row row) {
        final MinecraftUserImpl user = new MinecraftUserImpl();
        user.setId(row.getLong("id"));
        user.setUsername(row.getString("username"));
        user.setFactionId(row.getLong("faction_id"));
        user.setKills(row.getInteger("kills"));
        user.setDeaths(row.getInteger("deaths"));
        user.setLastActivityMillis(row.getLong("last_activity_millis"));
        user.setPower(row.getDouble("power"));
        user.setUserRole(UserRole.fromId(row.getInteger("user_role")));
        user.setPowerMax(row.getDouble("power_max"));
        return user;
    }
}
