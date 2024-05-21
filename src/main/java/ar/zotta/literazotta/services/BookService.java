package ar.zotta.literazotta.services;

import ar.zotta.literazotta.models.Author;
import ar.zotta.literazotta.models.Book;
import ar.zotta.literazotta.models.BookData;
import ar.zotta.literazotta.models.PersonData;
import ar.zotta.literazotta.repository.AuthorRepository;
import ar.zotta.literazotta.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    public void createAndSaveBook(BookData bookData, PersonData personData) {
        Author author;

        Optional<Author> optionalAuthor = authorRepository.findByName(personData.name());

        if (optionalAuthor.isPresent()) {
            author = optionalAuthor.get();
        } else {
            author = new Author(personData);
            authorRepository.save(author);
        }

        Book book = new Book(bookData);
        book.setAuthor(author);
        libraryRepository.save(book);
    }
}