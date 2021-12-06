package com.canmogol.webflux;

import lombok.Data;

@Data
public class CityDTO {

    private long id;
    private String name;
    private String countrycode;
    private String district;
    private long population;

}