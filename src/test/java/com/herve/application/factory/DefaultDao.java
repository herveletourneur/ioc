package com.herve.application.factory;

public class DefaultDao implements Dao, IDefaultDao {
    private final DataSource dataSource;

    public DefaultDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
