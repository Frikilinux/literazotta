package ar.zotta.literazotta.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    Integer bookId;

    String title;
    Boolean copyright;
    Integer downloadCount;
    String language;

    @ManyToOne(cascade = CascadeType.ALL)
    Author author;

    public Book() {
    }

    public Book(BookData bookData) {
        this.bookId = bookData.bookId();
        this.title = bookData.title();
        this.copyright = bookData.copyright();
        this.downloadCount = bookData.downloadCount();
        this.language = String.join(", ", bookData.languages());
    }

    public void setAuthor(Author author) {
        this.author = author;
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

    public String getLanguage() {
        return language;
    }

    @Override
    public String toString() {
    return "BookN: " + title + "LANG: " + language;
    }
}
