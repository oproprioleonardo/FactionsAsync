package com.leonardo.minecraft.factions.repositories.impl;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import com.leonardo.minecraft.factions.database.VertxRepository;
import com.leonardo.minecraft.factions.database.patterns.FactionQueries;
import com.leonardo.minecraft.factions.entities.Faction;
import com.leonardo.minecraft.factions.repositories.FactionRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Tuple;

@Singleton
public class FactionRepositoryImpl extends VertxRepository<Faction> implements FactionRepository {

    public FactionRepositoryImpl() {
        super(Faction.class, new FactionQueries());
    }

    public Uni<Faction> readByTag(String tag) {
        final String query = String.format("SELECT * FROM %s WHERE tag = ?", getQueryPatterns().getTable());
        return this.getClient()
                   .withTransaction(sqlConnection -> sqlConnection.preparedQuery(query)
                                                                  .execute(Tuple.from(Lists.newArrayList(tag))))
                   .chain(rows -> Uni.createFrom().item(this.getQueryPatterns().buildObj(rows.iterator().next())));
    }

    @Override
    public Uni<Boolean> existsTag(String tag) {
        final String query = String.format("SELECT id FROM %s WHERE tag = ?", getQueryPatterns().getTable());
        return this.getClient()
                   .withTransaction(sqlConnection -> sqlConnection.preparedQuery(query)
                                                                  .execute(Tuple.from(Lists.newArrayList(tag)))
                                                                  .chain(rows -> Uni.createFrom()
                                                                                    .item(rows.iterator().hasNext())));
    }

    @Override
    public Uni<Void> createTable() {
        final MySQLPool pool = getClient();
        return pool.query("CREATE TABLE IF NOT EXISTS factions(" +
                          "id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT, " +
                          "tag VARCHAR(3) NOT NULL, " +
                          "name VARCHAR(16) NOT NULL, " +
                          "leader_username VARCHAR(16) NOT NULL, " +
                          "description VARCHAR(78), " +
                          "motd VARCHAR(78), " +
                          "nexus_spawned TINYINT NOT NULL, " +
                          "created_at_millis BIGINT(20) NOT NULL, " +
                          "PRIMARY KEY (id), " +
                          "UNIQUE (tag)" +
                          ")").execute().replaceWithVoid();
    }
}
