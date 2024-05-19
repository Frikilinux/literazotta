package ar.zotta.literazotta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.zotta.literazotta.models.Book;

public interface LibraryRepository extends JpaRepository<Book, Long> {

}
