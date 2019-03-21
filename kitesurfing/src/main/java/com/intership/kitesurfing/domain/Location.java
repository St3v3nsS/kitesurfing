package com.intership.kitesurfing.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Document(collection = "locations")
public class Location {

    @Id
    private String _id;
    private String name;
    private Double longitude;
    private Double latitude;
    private Integer windProbability;
    @Field(value = "country")
    private String country;
    @NotNull
    private String whenToGo;

    public Location(String _id, String name, Double longitude, Double latitude, Integer windProbability, String country, String whenToGo) {
        this._id = _id;
        this.name = name;
        this.longitude = longitude;
        this.latitude = latitude;
        this.windProbability = windProbability;
        this.country = country;
        this.whenToGo = whenToGo;
    }

    public Location() {
    }

    public String get_id() {
        return _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getWindProbability() {
        return windProbability;
    }

    public void setWindProbability(Integer windProbability) {
        this.windProbability = windProbability;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getWhenToGo() {
        return whenToGo;
    }

    public void setWhenToGo(String whenToGo) {
        this.whenToGo = whenToGo;
    }

    @Override
    public String toString() {
        return "Name: "+ name + ", country: " + country;
    }
}
