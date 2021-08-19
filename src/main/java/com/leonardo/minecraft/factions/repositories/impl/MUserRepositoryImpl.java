package com.leonardo.minecraft.factions.repositories.impl;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import com.leonardo.minecraft.factions.database.VertxRepository;
import com.leonardo.minecraft.factions.database.patterns.MUserQueries;
import com.leonardo.minecraft.factions.entities.MinecraftUser;
import com.leonardo.minecraft.factions.repositories.MUserRepository;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Tuple;

@Singleton
public class MUserRepositoryImpl extends VertxRepository<MinecraftUser> implements MUserRepository {

    public MUserRepositoryImpl() {
        super(MinecraftUser.class, new MUserQueries());
    }

    @Override
    public Uni<MinecraftUser> readByUsername(String username) {
        final String query = String.format("SELECT * FROM %s WHERE username = ?", getQueryPatterns().getTable());
        return this.getClient()
                   .withTransaction(sqlConnection -> sqlConnection.preparedQuery(query)
                                                                  .execute(Tuple.from(Lists.newArrayList(username))))
                   .chain(rows -> Uni.createFrom().item(this.getQueryPatterns().buildObj(rows.iterator().next())));
    }

    @Override
    public Uni<Void> createTable() {
        final MySQLPool pool = getClient();
        return pool.query("CREATE TABLE IF NOT EXISTS m_users(" +
                          "id BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT, " +
                          "username VARCHAR(16) NOT NULL, " +
                          "user_role TINYINT NOT NULL" +
                          "faction_id BIGINT(20) UNSIGNED, " +
                          "kills INT UNSIGNED NOT NULL, " +
                          "deaths INT UNSIGNED NOT NULL, " +
                          "last_activity_millis BIGINT(20), " +
                          "power DOUBLE UNSIGNED NOT NULL, " +
                          "power_max DOUBLE UNSIGNED NOT NULL, " +
                          "PRIMARY KEY (id), " +
                          "UNIQUE (tag)" +
                          ")").execute().replaceWithVoid();
    }
}
