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

    int option = -1;

    String menu = """

        =====================================================
          1 - Buscar libros por titulo o autor y guardarlos
          2 - Lista de libros guardados
          3 - Lista autores desde un año
          4 - Listar Libros por idioma

          0 - Salir
        =====================================================

        """;

    while (option != 0) {

      System.out.println(menu);
      System.out.print("Opcion: ");

      try {
        option = Integer.parseInt(userInput());
      } catch (NumberFormatException e) {
        System.out.println("\n Introduce sólo numeros");
        option = -1;
      } catch (Exception e) {
        System.out.println("Error desconocido" + e);
      }

      switch (option) {
        case 1:
          searchAndSave();
          break;
        case 2:
          listAllBooks();
          break;
        case 3:
          listAuthorsFromYear();
          break;
        case 4:
          listBooksByLanguage();
          break;
        case 0:
          break;

        default:
          System.out.println("");
          System.out.println("Opción no válida, intentalo otra vez.");
          break;
      }
    }
    System.out.println("\nAdiós y recuerda:");
    System.out.println(
        "“Las personas libres jamás podrán concebir lo que los libros significan para quienes vivimos encerrados.”");
    System.out.println("        —  Ana Frank\n");
  }

  private void listBooksByLanguage() {
    List<String> availableLanguages = libraryRepository.avalableLanguages();
    String languages = String.join(" - ", availableLanguages);

    while (true) {
      System.out.println("\nIdiomas disponibles: " + languages + " Selecciona uno ");
      var option = userInput();

      if (availableLanguages.contains(option)) {
        List<Author> authors = libraryRepository.listBooksByLanguage(option);

        authors.stream().forEach(a -> {
          System.out.println("");
          System.out.println("Lista de autores y sus libros en el idioma " + option);
          System.out.println("========== " + a.getName() + " (" + a.getBirthYear() + " - " + a.getDeathYear() + ")");
          a.getBooks().forEach(b -> {
            if (b.getLanguage().equals(option)) {
              System.out
                  .println(b.getTitle() + " - idioma: " + b.getLanguage() + " - Descargas: " + b.getDownloadCount());
            }
          });
        });
        System.out.println("\n========================= ɸ =========================");

        break;

      } else {
        System.out.println("Idioma no disponible :(, selecciona otro!");
      }
    }
  }

  private void listAuthorsFromYear() {
    while (true) {
      System.out.print("Introduce un año: ");
      var year = Integer.valueOf(userInput());
      List<Author> authors;
      try {
        authors = libraryRepository.listAllBooks(year);
      } catch (Exception e) {
        System.out.println("ERROR" + e);
        authors = List.of();
      }

      if (authors.isEmpty()) {
        System.out.println("No se encontraron autores en ese año, ingresa otro");
        continue;
      }

      System.out.println("=========== Autores encontrados desde el año " + year);
      authors.stream()
          .forEach(a -> System.out
              .println("Nombre: " + a.getName() + " (" + a.getBirthYear() + " - " + a.getDeathYear() + ")"));
      System.out.println("\n========================= ɸ =========================");
      break;

    }
  }

  private void printListOfAuthors(List<Author> authors) {
    authors.stream().forEach(a -> {
      System.out.println("");
      System.out.println("========== " + a.getName() + " (" + a.getBirthYear() + "-" + a.getDeathYear() + ")");
      a.getBooks().forEach(b -> System.out.println(b.getTitle()));
    });
    System.out.println("\n========================= ɸ =========================");
  }

  private void listAllBooks() {
    List<Author> authors = libraryRepository.findAll();
    printListOfAuthors(authors);

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
      System.out.println("\nGuardado " + book.getTitle() + " en la base de datos. :)");

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
