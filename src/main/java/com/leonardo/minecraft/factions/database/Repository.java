package com.leonardo.minecraft.factions.database;

import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

import java.io.Serializable;

public interface Repository<O extends Entity> {

    Uni<Void> close();

    Uni<Void> deleteById(Long id);

    Uni<Boolean> existsId(Long id);

    Uni<O> delete(O id);

    Uni<O> readById(Long id);

    Uni<O> create(O obj);

    Uni<O> update(O obj);

    Uni<O> createOrUpdate(O obj);

    Uni<Void> createTable();
}
