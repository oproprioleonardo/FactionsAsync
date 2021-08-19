package com.leonardo.minecraft.factions.database;

import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

public interface VertxAdapters<O extends Entity> {

    Tuple getTupleToInsert(O obj);

    Tuple getTupleToUpdate(O obj);

    Tuple getTupleToInsertOrUpdate(O obj);

    O buildObj(Row row);

}
