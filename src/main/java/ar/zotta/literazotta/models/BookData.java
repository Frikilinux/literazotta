package ar.zotta.literazotta.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record BookData(
        @JsonAlias("id") Integer bookId,
        // List<String> sujects,
        String title,
        List<PersonData> authors,
        // List<PersonData> translators,
        Boolean copyright,
        @JsonAlias("download_count") Integer downloadCount) {

    public BookData {
        if (authors.size() == 0) {
            authors.add(new PersonData("1980", "1980", "Unknown"));
        }
    }

}
