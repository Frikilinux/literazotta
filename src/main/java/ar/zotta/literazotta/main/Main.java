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

  public void main() {

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
  }

  // System.out.print("Nombre del libro: ");
  // var name = System.console().readLine();

  // String encodeText = ZUtils.encodeText(name);
  // BookData res = queryApi.query("?search=" + encodeText);
  // System.out.println("""

  // ***** Libros Encontrado *****
  // Book ID: %s
  // Titulo: %s
  // Autor%s: %s
  // Downloads : %d
  // *****************************

  // """.formatted(res.bookId(), res.title(), res.authors().size() > 1 ? "es" :
  // "", res.authors().get(0).name(),
  // res.downloadCount()));

  // Author author = new Author(res.authors().get(0));
  // Book book = new Book(res);
  // author.setBook(book);
  // // List<Author> authors = res.authors().stream().map(a -> new
  // Author(a)).collect(Collectors.toList());

  // libraryRepository.save(author);

  //////////////////////////////////////
  private String userInput() {
    var name = System.console().readLine();
    return name;
  }

  private void saveBookToDB(BookData bookData) {
    Book BookInDB = libraryRepository.searchBookByBookId(bookData.bookId());

    if (BookInDB != null) {
      System.out.println("Libro existente en la DB");
      System.out.println("Titulo: " + BookInDB.getTitle());
      return;
    }

    List<Author> authorInDB = libraryRepository.searchAuthor(bookData.authors().get(0).name());
    Book book = new Book(bookData);
    Author author;

    if (authorInDB.isEmpty()) {
      author = new Author(bookData.authors().get(0));
    } else {
      author = authorInDB.get(0);
    }

    // System.out.println("AUTOR ENCONTRADO EN DB" + books);
    author.setBook(book);
    libraryRepository.save(author);
    System.out.println("Guardado " + book.getTitle() + " en la base de datos. :)");
  }
  // Syste

  private void printBookList(List<BookData> bookList) {
    for (int i = 0; i < bookList.size(); i++) {
      String author = bookList.get(i).authors().get(0).name();
      System.out.println(String.format("%d - Titulo: %s por %s", i + 1, bookList.get(i).title(), author));
    }
  }

  private void searchAndSave() {
    System.out.print("Nombre del libro o autor: ");
    var searchText = userInput();

    List<BookData> response = queryApi.query(ZUtils.encodeText(searchText)).stream()
        .limit(9)
        .collect(Collectors.toList());

    if (response.isEmpty()) {
      return;
    }

    printBookList(response);

    while (true) {
      System.out.println("Seleccona un libro");
      int option = Integer.parseInt(userInput());

      if (option <= 0 || option > response.size()) {
        System.out.println("Opcion incorrecta, excoge otra");
      } else {
        
        saveBookToDB(response.get(option - 1));
        break;
      }
    }

  }

}
