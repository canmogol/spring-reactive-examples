package com.canmogol.webflux;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static io.r2dbc.spi.ConnectionFactoryOptions.DATABASE;
import static io.r2dbc.spi.ConnectionFactoryOptions.DRIVER;
import static io.r2dbc.spi.ConnectionFactoryOptions.HOST;
import static io.r2dbc.spi.ConnectionFactoryOptions.PASSWORD;
import static io.r2dbc.spi.ConnectionFactoryOptions.PORT;
import static io.r2dbc.spi.ConnectionFactoryOptions.PROTOCOL;
import static io.r2dbc.spi.ConnectionFactoryOptions.USER;

@Configuration
public class R2DBCConfig {

    @Value("${r2dbc.postgresql.host:localhost}")
    private String host;

    @Value("${r2dbc.postgresql.port:5432}")
    private Integer port;

    @Value("${r2dbc.postgresql.database:postgres}")
    private String database;

    @Value("${r2dbc.postgresql.schema:public}")
    private String schema;

    @Value("${r2dbc.postgresql.username:postgres}")
    private String username;

    @Value("${r2dbc.postgresql.password:postgres}")
    private String password;

    @Bean
    public ConnectionPool connectionFactory() {
        final ConnectionFactory connectionFactory = ConnectionFactories.get(ConnectionFactoryOptions.builder()
                .option(DRIVER, "pool")
                .option(PROTOCOL, "postgresql") // driver identifier, PROTOCOL is delegated as DRIVER by the pool.
                .option(HOST, host)
                .option(PORT, port)
                .option(USER, username)
                .option(PASSWORD, password)
                .option(DATABASE, database)
                .build());

        final ConnectionPoolConfiguration configuration = ConnectionPoolConfiguration.builder(connectionFactory)
                .build();

        return new ConnectionPool(configuration);
    }

}