package ar.zotta.literazotta.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import ar.zotta.literazotta.models.ApiResults;
import ar.zotta.literazotta.models.BookData;
import ar.zotta.literazotta.utils.ZUtils;

public class QueryApi {

  public BookData query(String endpoint) {

    final String BASE_URL = "https://gutendex.com/books/";
    // if (url == null || url.isEmpty()) {
    // return null;
    // }

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + endpoint))
        .build();

    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      var conv = ZUtils.dataProcess(response.body(), ApiResults.class);
      BookData book = conv.results().get(0);

      return book;

    } catch (Exception e) {
      System.out.println("Error perro: " + e);
      return null;
      // throw new RuntimeException(e);
    }
  }
}
