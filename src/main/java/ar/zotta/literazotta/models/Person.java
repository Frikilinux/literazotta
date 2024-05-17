package ar.zotta.literazotta.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Person(
    @JsonAlias("birth_year") String birthYear,
    @JsonAlias("death_year") String deathYear,
    @JsonAlias("name") String name) {
}
