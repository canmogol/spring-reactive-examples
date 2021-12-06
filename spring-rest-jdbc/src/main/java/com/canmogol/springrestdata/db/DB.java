package com.canmogol.springrestdata.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DB {

    <T> List<T> findAll(Class<T> typeClass);

    interface JdbcEntity {

        String getTableName();

        void read(ResultSet resultSet) throws SQLException;
    }

}
