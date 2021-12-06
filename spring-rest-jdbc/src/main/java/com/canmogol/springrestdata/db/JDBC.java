package com.canmogol.springrestdata.db;


import org.postgresql.Driver;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JDBC implements DB {

    private static final int defaultNumberOfConnections = 100;
    private final List<Connection> connections = new ArrayList<>();
    private final Integer connectionPoolSize;

    private int cursor = 0;

    public JDBC(String jdbcConnectionURL) throws SQLException {
        final Driver driver = new Driver();
        final int majorVersion = driver.getMajorVersion();
        log("postgresql driver version " + majorVersion);
        final Map<String, String> parameters = getParameters(jdbcConnectionURL);
        connectionPoolSize = Integer.valueOf(
                parameters.getOrDefault("connections", String.valueOf(defaultNumberOfConnections)));
        for (int i = 0; i < connectionPoolSize; i++) {
            this.connections.add(DriverManager.getConnection(jdbcConnectionURL));
        }
        log(this.connections.size() + " connections created.");
    }

    private Map<String, String> getParameters(String jdbcConnectionURL) {
        if (jdbcConnectionURL.split("\\?").length == 2) {
            final String[] keyValues = jdbcConnectionURL.split("\\?")[1].split("&");
            return Stream.of(keyValues)
                    .map(kv -> kv.split("="))
                    .collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));
        } else {
            return Collections.emptyMap();
        }
    }

    @Override
    public <T> List<T> findAll(Class<T> typeClass) {
        List<T> objects = new ArrayList<>();
        Statement statement = null;
        try {
            statement = getNextConnection().createStatement();
            final String tableName = getTableName(typeClass);
            final String query = String.format("select * from %s", tableName);
            final ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                final Constructor<T> constructor = typeClass.getConstructor();
                final T entity = constructor.newInstance();
                ((JdbcEntity) entity).read(resultSet);
                objects.add(entity);
            }
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException |
                SQLException | NoSuchMethodException e) {
            throw new RuntimeException("Exception occurred at database operation.", e);
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    log("could not close the statement.");
                }
            }
        }
        return objects;
    }

    private Connection getNextConnection() {
        return connections.get((cursor++) % connectionPoolSize);
    }

    private <T> String getTableName(Class<T> typeClass)
            throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        final Constructor<T> jdbcEntityConstructor = typeClass.getConstructor();
        final JdbcEntity jdbcEntity = (JdbcEntity) jdbcEntityConstructor.newInstance();
        return jdbcEntity.getTableName();
    }

    private void log(String log) {
        System.out.println(log);
    }

}
