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
  QueryApi queryApi = new QueryApi();

  public Main(LibraryRepository libraryRepository) {
    this.libraryRepository = libraryRepository;
  }

  public void mainMenu() {
    var option = -1;

    while (option != 0) {
      String menu = """

          =====================================================
            1 - Buscar libros por titulo o autor y guardarlos
            2 - Lista de libros guardados
          =====================================================

          """;
      System.out.println(menu);

      option = Integer.parseInt(userInput());

      switch (option) {
        case 1:
          searchAndSave();
          break;

        default:
          break;
      }

    }

    // System.out.print("Nombre del libro: ");
    // var searchText = System.console().readLine();

    // BookData res = queryApi.query(ZUtils.encodeText(searchText));

    // Book book = new Book(res);
    // List<Author> authors = res.authors().stream()
    // .map(a -> new Author(a))
    // .collect(Collectors.toList());

    // book.setAuthors(authors);
    // libraryRepository.save(book);

  }

  private String userInput() {
    return System.console().readLine();
  }

  private void printBookList(List<BookData> bookList) {
    for (int i = 0; i < bookList.size(); i++) {
      String authors = bookList.get(i).authors().get(0).name();
      System.out.println(String.format("%d - Titulo: %s por %s", i + 1, bookList.get(i).title(), authors));
    }
  }

  private void saveBookToDB(BookData bookData) {
    Book book = new Book(bookData);

    List<Author> authors = bookData.authors().stream()
    .map(a -> new Author(a))
    .collect(Collectors.toList());

    book.setAuthors(authors);
    libraryRepository.save(book);
  }

  private void searchAndSave() {
    System.out.print("Nombre del libro o autor: ");
    var searchText = userInput();

    List<BookData> res = queryApi.query(ZUtils.encodeText(searchText)).stream()
        .limit(9)
        .collect(Collectors.toList());

    if (res.isEmpty()) {
      return;
    }

    printBookList(res);

    while (true) {
      System.out.println("Seleccona un libro");
      int option = Integer.parseInt(userInput());

      if (option <= 0 || option > res.size()) {
        System.out.println("Opcion incorrecta, excoge otra");
      } else {
        System.out.println("Guardado " + res.get(option -1).title() + " en la base de datos. :)" );
        saveBookToDB(res.get(option - 1));
        break;
      }

    }

    // System.out.println("""
    // ***** Libros Encontrado *****
    // Book ID: %s
    // Titulo: %s
    // Autor%s: %s
    // Downloads : %d
    // *****************************

    // """.formatted(res.get(0).bookId(), res.get(0).title(),
    // res.get(0).authors().size() > 1 ? "es" : "",
    // res.get(0).authors().get(0).name(),
    // res.get(0).downloadCount()));
  }

}
