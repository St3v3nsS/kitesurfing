package com.intership.kitesurfing.controller;

import com.intership.kitesurfing.repository.LocationRepository;
import com.intership.kitesurfing.domain.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LocationController {

    @Autowired
    private LocationRepository repository;


    @RequestMapping(value = "/spots/{spotId}", method = RequestMethod.GET)
    public Location getLocationById(@PathVariable("spotId")String spotId){
        return repository.findBy_id(spotId);
    }


    @GetMapping (value = "/spots")
    public List<Location> getLocationByCountry(@RequestParam(value = "country", required = false) String country,
                                               @RequestParam(value = "windProbability", required = false) Integer windProbability, Pageable pageable){
        if(windProbability != null && windProbability <=100 && windProbability >= 0){
            if(country != null && !country.isEmpty()){
                return repository.findByCountryAndWindProbability(country, windProbability, pageable);
            }
            else{
                return repository.findByWindProbability(windProbability, pageable);
            }
        }
        else if(country != null && !country.isEmpty())
                return repository.findByCountry(country, pageable);
        return repository.findAll(pageable).getContent();
    }

    @RequestMapping(value = "/spots/countries", method = RequestMethod.GET)
    public List<String> getCountries(){

        List<Location> locations = repository.findAll();

        List<String> list = new ArrayList<>();
        locations.stream()
                .forEach(e -> list.add(e.getCountry()));
        return list.stream()
                .distinct()
                .collect(Collectors.toList());
    }
}
