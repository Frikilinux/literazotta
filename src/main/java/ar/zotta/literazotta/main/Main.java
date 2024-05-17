package ar.zotta.literazotta.main;

import ar.zotta.literazotta.models.Book;
import ar.zotta.literazotta.services.QueryApi;
import ar.zotta.literazotta.utils.ZUtils;

public class Main {

  public void main() {
    QueryApi queryApi = new QueryApi();

    String encodeText = ZUtils.encodeText("moby dick");

    Book res = queryApi.query("?search=" + encodeText);

    System.out.println(res.title());
  }

}
