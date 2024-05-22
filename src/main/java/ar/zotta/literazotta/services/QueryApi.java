package ar.zotta.literazotta.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import ar.zotta.literazotta.models.ApiResults;
import ar.zotta.literazotta.models.BookData;
import ar.zotta.literazotta.utils.ZUtils;

public class QueryApi {

  public List<BookData> query(String searString) {

    final String BASE_URL = "https://gutendex.com/books/?search=";

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + searString))
        .build();

    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      var dataConverted = ZUtils.dataProcess(response.body(), ApiResults.class);

      if (dataConverted.results().isEmpty()) {
        System.out.println("No se encontraron datos :(, intenta otra vez");
        return List.of();
      }

      List<BookData> books = dataConverted.results();
      return books;

    } catch (Exception e) {
      System.out.println("Error perro: " + e);
      return List.of();
      // throw new RuntimeException(e);
    }
  }
}
