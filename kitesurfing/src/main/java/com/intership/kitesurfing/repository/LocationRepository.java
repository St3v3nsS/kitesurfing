package com.intership.kitesurfing.repository;

import com.intership.kitesurfing.domain.Location;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;


public interface LocationRepository extends MongoRepository<Location, String>{
    Location findBy_id(String id);

    List<Location> findByCountryAndWindProbability(String country, Integer windProbability, Pageable pageable);

    List<Location> findByCountry(String country, Pageable pageable);

    List<Location> findByWindProbability(Integer windProbability, Pageable pageable);

    Location findByName(String name);

}
