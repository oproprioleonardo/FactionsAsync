package com.leonardo.minecraft.factions.database;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.mysqlclient.MySQLClient;
import io.vertx.mutiny.mysqlclient.MySQLPool;
import io.vertx.mutiny.sqlclient.Tuple;
import lombok.Getter;

public abstract class VertxRepository<O extends Entity> implements Repository<O> {

    @Getter
    private final Class<O> target;
    @Getter
    private final QueryPatterns<O> queryPatterns;
    @Inject
    @Getter
    private MySQLPool client;

    public VertxRepository(Class<O> target, QueryPatterns<O> queryPatterns) {
        this.target = target;
        this.queryPatterns = queryPatterns;
    }

    @Override
    public Uni<O> create(O obj) {
        return this.client.withTransaction(
                sqlConnection -> sqlConnection.preparedQuery(this.queryPatterns.getInsertQuery())
                                              .execute(this.queryPatterns.getTupleToInsert(obj)))
                          .chain(rows -> {
                              final Long id = rows.property(MySQLClient.LAST_INSERTED_ID);
                              obj.setId(id);
                              return Uni.createFrom().item(obj);
                          });
    }

    @Override
    public Uni<O> update(O obj) {
        return this.client
                .withTransaction(sqlConnection -> sqlConnection.preparedQuery(this.queryPatterns.getUpdateQuery())
                                                               .execute(this.queryPatterns.getTupleToUpdate(obj)))
                .chain(rows -> Uni.createFrom().item(obj));
    }

    public Uni<O> createOrUpdate(O obj) {
        return this.client
                .withTransaction(
                        sqlConnection -> sqlConnection.preparedQuery(this.queryPatterns.getInsertOrUpdateQuery())
                                                      .execute(this.queryPatterns.getTupleToInsertOrUpdate(obj)))
                .chain(rows -> {
                    if (obj.getId() == null || obj.getId() == 0) {
                        final Long id = rows.property(MySQLClient.LAST_INSERTED_ID);
                        obj.setId(id);
                    }
                    return Uni.createFrom().item(obj);
                });
    }

    @Override
    public Uni<O> readById(Long id) {
        final String formatted = String.format(this.queryPatterns.getSelectQuery(), this.queryPatterns.getTable());
        System.out.println(formatted);
        return this.client
                .withTransaction(sqlConnection -> sqlConnection.preparedQuery(formatted)
                                                               .execute(Tuple.from(Lists.newArrayList(id)))
                                                               .chain(rows -> Uni.createFrom().item(this.queryPatterns
                                                                                                            .buildObj(
                                                                                                                    rows.iterator()
                                                                                                                        .next()))));
    }

    @Override
    public Uni<O> delete(O obj) {
        final String formatted = String.format(this.queryPatterns.getDeleteQuery(), this.queryPatterns.getTable());
        return this.client.withTransaction(sqlConnection -> sqlConnection.preparedQuery(formatted)
                                                                         .execute(Tuple.from(
                                                                                 Lists.newArrayList(obj.getId())))
                                                                         .chain(rows -> Uni.createFrom().item(obj)));
    }

    @Override
    public Uni<Void> deleteById(Long id) {
        final String formatted = String.format(this.queryPatterns.getDeleteQuery(), this.queryPatterns.getTable());
        return this.client.withTransaction(sqlConnection -> sqlConnection.preparedQuery(formatted)
                                                                         .execute(Tuple.from(Lists.newArrayList(id)))
                                                                         .chain(rows -> Uni.createFrom().voidItem()));
    }

    @Override
    public Uni<Boolean> existsId(Long id) {
        final String formatted = String.format(this.queryPatterns.getExistsQuery(), this.queryPatterns.getTable());
        return this.client.withTransaction(
                sqlConnection -> sqlConnection.preparedQuery(formatted).execute(Tuple.from(Lists.newArrayList(id)))
                                              .chain(rows -> Uni.createFrom()
                                                                .item(rows.iterator().hasNext())));
    }

    @Override
    public Uni<Void> close() {
        return this.client.close();
    }
}
