package com.leonardo.minecraft.factions.database;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public abstract class QueryPatterns<O extends Entity> implements VertxAdapters<O> {

    private String table;
    private String insertQuery;
    private String updateQuery;
    private String existsQuery = "SELECT id FROM %s WHERE id = ?";
    private String insertOrUpdateQuery;
    private String deleteQuery = "DELETE FROM %s WHERE id = ?";
    private String selectQuery = "SELECT * FROM %s WHERE id = ?";

    public QueryPatterns(String table, String insertQuery, String updateQuery, String insertOrUpdateQuery) {
        this.table = table;
        this.insertQuery = insertQuery;
        this.updateQuery = updateQuery;
        this.insertOrUpdateQuery = insertOrUpdateQuery;
    }

    public QueryPatterns(String table, String insertQuery, String updateQuery, String insertOrUpdateQuery,
                         String deleteQuery, String selectQuery) {
        this.table = table;
        this.insertQuery = insertQuery;
        this.updateQuery = updateQuery;
        this.insertOrUpdateQuery = insertOrUpdateQuery;
        this.deleteQuery = deleteQuery;
        this.selectQuery = selectQuery;
    }
}
