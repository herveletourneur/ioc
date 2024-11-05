package com.herve.application.ok;


import com.herve.application.composition.configuration.DataSource;
import com.herve.application.composition.dao.Dao;

@Dao
public class DefaultDao implements IDefaultDao {
    private final DataSource dataSource;

    public DefaultDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
