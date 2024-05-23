package ar.zotta.literazotta.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.zotta.literazotta.models.Author;
import ar.zotta.literazotta.models.Book;

public interface LibraryRepository extends JpaRepository<Author, Long> {
  @Query("SELECT a FROM Author a WHERE a.name ILIKE %:name%")
  List<Author> searchAuthor(String name);

  @Query("SELECT b FROM Book b WHERE b.bookId = :bookId")
  Optional<Book> searchBookByBookId(Integer bookId);

  @Query("SELECT a FROM Author a WHERE a.birthYear >= :birthYear ")
  List<Author> listAllBooks(Integer birthYear);

  @Query("SELECT DISTINCT b.language FROM Book b")
  List<String> avalableLanguages();

  @Query("SELECT a FROM Author a INNER JOIN a.books b WHERE b.language = :language")
  List<Author> listBooksByLanguage(String language);
}
