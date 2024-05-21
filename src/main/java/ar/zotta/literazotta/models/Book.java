package ar.zotta.literazotta.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    Integer bookId;

    String title;
    Boolean copyright;
    Integer downloadCount;

    // @Transient
    @ManyToOne
    Author author;

    public Book() {
    }

    public Book(BookData bookData) {
        this.bookId = bookData.bookId();
        this.title = bookData.title();
        this.copyright = bookData.copyright();
        this.downloadCount = bookData.downloadCount();
    }

    public void setAuthor(Author author) {
        author.setBook(this);
        this.author = author;
    }

    public Author getAuthor() {
        return author;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getCopyright() {
        return copyright;
    }

    public void setCopyright(Boolean copyright) {
        this.copyright = copyright;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Titulo: " + title + " autor: " + author.getName();
    }
}
