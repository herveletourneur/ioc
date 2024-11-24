package com.herve.example.rightside;

import com.herve.example.DataSource;

@Dao
public class DaoImpl implements HelloWorlDao {
    private final DataSource dataSource;

    public DaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public String retrieveHowToSayHello() {
        return "HelloWorld from my own DI";
    }
}
