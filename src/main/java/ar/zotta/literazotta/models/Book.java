package ar.zotta.literazotta.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Book(
    Integer id,
    List<String> sujects,
    String title,
    List<Person> authors,
    List<Person> translators,
    Boolean copyright,
    @JsonAlias("download_count") Integer downloadCount) {

}
