package ar.zotta.literazotta.main;

import ar.zotta.literazotta.models.Book;
import ar.zotta.literazotta.services.QueryApi;
import ar.zotta.literazotta.utils.ZUtils;

public class Main {

  public void main() {
    QueryApi queryApi = new QueryApi();

    System.out.print("Nombre del libro: ");
    var name = System.console().readLine();

    String encodeText = ZUtils.encodeText(name);
    Book res = queryApi.query("?search=" + encodeText);
    System.out.println("""

        ***** Libros Encontrado *****
        Book ID: %s
        Titulo: %s
        Autor%s: %s
        Downloads : %d
        *****************************

          """.formatted(res.id(), res.title(), res.authors().size() > 1 ? "es" : null, res.authors().get(0).name(),
        res.downloadCount()));
  }

}
