package ar.zotta.literazotta.utils;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ZUtils {
  public static String encodeText(String str) {
    return URLEncoder.encode(str, StandardCharsets.UTF_8);
  }

  public static <T> T dataProcess(String json, Class<T> type) {
    try {
      return new ObjectMapper().readValue(json, type);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  public static String nameFormat(String name) {
    String[] names = name.split(", ");
    return names[1] + " " + names[0];
  }

}
