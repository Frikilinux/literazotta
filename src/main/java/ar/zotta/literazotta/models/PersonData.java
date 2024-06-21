package ar.zotta.literazotta.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record PersonData(
        @JsonAlias("birth_year") String birthYear,
        @JsonAlias("death_year") String deathYear,
        @JsonAlias("name") String name) {

    public PersonData {
        birthYear = birthYear == null ? "0" : birthYear;
        deathYear = deathYear == null ? "0" : deathYear;
        name = name == null ? "Unknown" : name;
    }
}
