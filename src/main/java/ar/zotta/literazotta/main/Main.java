package ar.zotta.literazotta.main;

import java.util.List;
import java.util.stream.Collectors;

import ar.zotta.literazotta.models.Author;
import ar.zotta.literazotta.models.Book;
import ar.zotta.literazotta.models.BookData;
import ar.zotta.literazotta.repository.LibraryRepository;
import ar.zotta.literazotta.services.QueryApi;
import ar.zotta.literazotta.utils.ZUtils;

public class Main {
  private LibraryRepository libraryRepository;

  public Main(LibraryRepository libraryRepository) {
    this.libraryRepository = libraryRepository;
  }

  public void main() {

    QueryApi queryApi = new QueryApi();

    System.out.print("Nombre del libro: ");
    var name = System.console().readLine();

    String encodeText = ZUtils.encodeText(name);
    BookData res = queryApi.query("?search=" + encodeText);
    System.out.println("""

        ***** Libros Encontrado *****
        Book ID: %s
        Titulo: %s
        Autor%s: %s
        Downloads : %d
        *****************************

          """.formatted(res.bookId(), res.title(), res.authors().size() > 1 ? "es" : "", res.authors().get(0).name(),
        res.downloadCount()));

    Book book = new Book(res);
    List<Author> authors = res.authors().stream().map(a -> new Author(a)).collect(Collectors.toList());

    book.setAuthors(authors);
    libraryRepository.save(book);

  }

}
