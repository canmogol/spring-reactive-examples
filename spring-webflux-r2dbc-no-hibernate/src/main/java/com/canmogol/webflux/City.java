package com.canmogol.webflux;


import lombok.Data;

@Data
public class City {

    private Long id;
    private String name;
    private String countrycode;
    private String district;
    private Long population;

}