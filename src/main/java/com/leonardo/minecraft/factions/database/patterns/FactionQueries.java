package com.leonardo.minecraft.factions.database.patterns;

import com.google.common.collect.Lists;
import com.leonardo.minecraft.factions.database.QueryPatterns;
import com.leonardo.minecraft.factions.entities.Faction;
import com.leonardo.minecraft.factions.entities.impl.FactionImpl;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

public class FactionQueries extends QueryPatterns<Faction> {

    public FactionQueries() {
        super("factions",
              "INSERT INTO factions (tag, name, leader_username, description, motd, nexus_spawned, created_at_millis) " +
                          "VALUES (?, ?, ?, ?, ?, ?, ?)",
              "UPDATE factions set leader_username = ?, description = ?, motd = ?, nexus_spawned = ? WHERE id = ?",
              "INSERT INTO factions (id, tag, name, leader_username, description, motd, nexus_spawned, created_at_millis) " +
              "VALUES (?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE leader_username = ?, description = ?, " +
              "motd = ?, nexus_spawned = ?");
    }

    @Override
    public Tuple getTupleToInsert(Faction obj) {
        return Tuple.from(Lists.newArrayList(
                obj.getTag(),
                obj.getName(),
                obj.getLeaderUsername(),
                obj.getDescription(),
                obj.getMotd(),
                obj.isNexusSpawned(),
                obj.getCreatedAtMillis()
        ));
    }

    @Override
    public Tuple getTupleToUpdate(Faction obj) {
        return Tuple.from(Lists.newArrayList(
                obj.getLeaderUsername(),
                obj.getDescription(),
                obj.getMotd(),
                obj.isNexusSpawned(),
                obj.getId()
        ));
    }

    @Override
    public Tuple getTupleToInsertOrUpdate(Faction obj) {
        return Tuple.from(Lists.newArrayList(
                obj.getId(),
                obj.getTag(),
                obj.getName(),
                obj.getLeaderUsername(),
                obj.getDescription(),
                obj.getMotd(),
                obj.isNexusSpawned(),
                obj.getCreatedAtMillis(),
                obj.getLeaderUsername(),
                obj.getDescription(),
                obj.getMotd(),
                obj.isNexusSpawned()
        ));
    }

    @Override
    public Faction buildObj(Row row) {
        final FactionImpl factionImpl = new FactionImpl();
        factionImpl.setId(row.getLong("id"));
        factionImpl.setTag(row.getString("tag"));
        factionImpl.setName(row.getString("name"));
        factionImpl.setCreatedAtMillis(row.getLong("created_at_millis"));
        factionImpl.setDescription(row.getString("description"));
        factionImpl.setMotd(row.getString("motd"));
        factionImpl.setNexusSpawned(row.getBoolean("nexus_spawned"));
        factionImpl.setLeaderUsername(row.getString("leader_username"));
        return factionImpl;
    }
}
