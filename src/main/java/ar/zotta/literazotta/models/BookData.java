package ar.zotta.literazotta.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
        // List<String> sujects,
        // List<PersonData> translators,
        @JsonAlias("id") Integer bookId,
        String title,
        List<PersonData> authors,
        List<String> languages,
        Boolean copyright,
        @JsonAlias("download_count") Integer downloadCount) {

    public BookData {
        if (authors.size() == 0) {
            authors.add(new PersonData("0", "0", "Unknown"));
        }
        if (languages.size() == 0) {
            languages = List.of("Unknown");
        }
    }

}
