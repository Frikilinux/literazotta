package ar.zotta.literazotta.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import ar.zotta.literazotta.models.IsoLanguages;
import ar.zotta.literazotta.utils.ZUtils;

public class QueryLanguage {

  public static String query(String searString) {

    final String BASE_URL = "https://iso-langs.vercel.app/langs/";

    HttpClient client = HttpClient.newHttpClient();
    HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(BASE_URL + searString))
        .build();

    try {
      HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

      var dataConverted = ZUtils.dataProcess(response.body(), IsoLanguages.class);

      if (!dataConverted.getSuccess()) {
        System.out.println("No se encontraron el idioma.");
        return null;
      }

      return dataConverted.getData().getLanguage();

    } catch (Exception e) {
      System.out.println("Error perro: " + e);
      return null;
      // throw new RuntimeException(e);
    }
  }
}
