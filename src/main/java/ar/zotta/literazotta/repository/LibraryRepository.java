package ar.zotta.literazotta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ar.zotta.literazotta.models.Author;
import ar.zotta.literazotta.models.Book;

public interface LibraryRepository extends JpaRepository<Author, Long> {
  @Query("SELECT a FROM Author a WHERE a.name ILIKE %:name%")
  List<Author> searchAuthor(String name);
  
  @Query("SELECT b FROM Book b WHERE b.bookId = :bookId")
  Book searchBookByBookId(Integer bookId);
}
