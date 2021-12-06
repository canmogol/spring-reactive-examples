package com.canmogol.springrestdata;

import com.canmogol.springrestdata.db.DB;
import com.canmogol.springrestdata.db.JDBC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/city")
public class CityController {

    @Value("${database.url}")
    private String databaseUrl;

    private DB db;

    @PostConstruct
    public void createDatabaseConnections() throws Exception {
        final String connectionUrl = Optional.ofNullable(System.getenv("DATABASE")).orElse(databaseUrl);
        db = new JDBC(connectionUrl);
    }

    @GetMapping
    public List<City> getAll() {
        return db.findAll(City.class);
    }

}