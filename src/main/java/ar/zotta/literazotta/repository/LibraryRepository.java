package ar.zotta.literazotta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.zotta.literazotta.models.Author;
import ar.zotta.literazotta.models.Book;

public interface LibraryRepository extends JpaRepository<Book, Long> {

  @Query("SELECT a FROM Book b JOIN b.author a WHERE a.name ILIKE %:name%")
  List<Author> searchAuthor(String name);

}
