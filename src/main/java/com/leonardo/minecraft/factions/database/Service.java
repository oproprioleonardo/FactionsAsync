package com.leonardo.minecraft.factions.database;

import io.smallrye.mutiny.Uni;

public interface Service<O extends Entity> {

    Uni<Void> deleteById(Long id);

    Uni<O> delete(O obj);

    Uni<O> readById(Long id);

    Uni<Boolean> existsId(Long id);

    Uni<O> create(O obj);

    Uni<O> update(O obj);

    Uni<O> createOrUpdate(O obj);

    Repository<O> getRepository();

    Uni<Void> close();

    Uni<Void> createTable();

}
