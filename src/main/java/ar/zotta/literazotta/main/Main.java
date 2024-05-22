package ar.zotta.literazotta.main;

import java.util.List;
import java.util.Optional;
// import java.util.Scanner;
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
      System.out.print("Opcion: ");
      option = Integer.parseInt(userInput());

      switch (option) {
        case 1:
          searchAndSave();
          break;
        case 2:
          ListAllBooks();
          break;

        default:
          break;
      }
    }
  }

  private void ListAllBooks() {
    List<Author> authors = libraryRepository.findAll();
    System.out.println(authors.size());
    authors.stream().forEach(a -> {
      System.out.println("");
      System.out.println("========== " + a.getName() + " (" + a.getBirthYear() + "-" + a.getDeathYear() + ")");
      a.getBooks().forEach(b -> System.out.println(b.getTitle()));
    });
  }

  private String userInput() {
    var name = System.console().readLine();
    // Scanner input = new Scanner(System.in);
    // var name = input.next();
    // input.close();
    return name;
  }

  private void saveBookToDB(BookData bookData) {
    Optional<Book> BookInDB = libraryRepository.searchBookByBookId(bookData.bookId());

    if (BookInDB.isPresent()) {
      System.out.println("");
      System.out.println("Libro existente en la DB. Puedes realizar otra busqueda.");
      System.out.println("Titulo: " + BookInDB.get().getTitle());
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
    try {
      libraryRepository.save(author);
      System.out.println("Guardado " + book.getTitle() + " en la base de datos. :)");

    } catch (Exception e) {
      System.out.println("ERROR!: " + e);
    }
  }

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
      System.out.print("Seleccona un libro: ");
      int option = Integer.parseInt(userInput());

      if (option <= 0 || option > response.size()) {
        System.out.println("");
        System.out.println("Opcion incorrecta, excoge otra");
      } else {
        saveBookToDB(response.get(option - 1));
        break;
      }
    }

  }

}
