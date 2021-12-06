package com.canmogol.webflux;


import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.spi.Row;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Repository
public class CityRepository {

    private final ConnectionPool connectionPool;

    public Flux<City> findAll() {
        return connectionPool.create()
                .flatMapMany(connection ->
                        Flux.from(connection.createStatement("select * from city").execute())
                                .flatMap(result -> result.map((row, rowMetadata) -> getCity(row)))
                                .doFinally(st -> Mono.from(connection.close()).subscribe())
                );
    }

    private City getCity(Row row) {
        City city = new City();
        city.setId(row.get("id", Long.class));
        city.setName(row.get("name", String.class));
        city.setCountrycode(row.get("countrycode", String.class));
        city.setDistrict(row.get("district", String.class));
        city.setPopulation(row.get("population", Long.class));
        return city;
    }
}
