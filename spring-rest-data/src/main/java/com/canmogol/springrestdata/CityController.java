package com.canmogol.springrestdata;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/city")
public class CityController {

    private final CityRepository cityRepository;

    @GetMapping("/entity")
    public Iterable<City> getAllEntities() {
        return cityRepository.findAll();
    }

    @GetMapping("/dto")
    public List<CityDTO> getAllDTOs() {
        return StreamSupport.stream(cityRepository.findAll().spliterator(), false)
                .map(this::getCityDTO)
                .collect(Collectors.toList());
    }

    private CityDTO getCityDTO(City city) {
        CityDTO dto = new CityDTO();
        dto.setCountrycode(city.getCountrycode());
        dto.setDistrict(city.getDistrict());
        dto.setId(city.getId());
        dto.setPopulation(city.getPopulation());
        dto.setName(city.getName());
        return dto;
    }

}