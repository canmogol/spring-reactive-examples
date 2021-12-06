package com.canmogol.webflux;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/city")
public class CityController {

    private final CityRepository cityRepository;

    @GetMapping("/entity")
    public Flux<City> getAllEntities() {
        return cityRepository.findAll();
    }

    @GetMapping("/dto")
    public Flux<CityDTO> getAllDTOs() {
        return cityRepository.findAll().map(this::getCityDTO);
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
