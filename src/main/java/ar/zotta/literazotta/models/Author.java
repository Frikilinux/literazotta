package ar.zotta.literazotta.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "author")
public class Author {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  String name;

  String birthYear;
  String deathYear;

  // @ManyToMany(mappedBy = personDB, cascade = CascadeType.ALL, fetch =
  // FetcType.EAGER)
  @OneToMany(mappedBy = "author", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
  // List<Book> book = new ArrayList<>();
  List<Book> books;

  public Author() {
  }

  public Author(PersonData personData) {
    this.name = personData.name();
    this.birthYear = personData.birthYear();
    this.deathYear = personData.deathYear();
  }

  public void setBook(Book book) {
    book.setAuthor(this);
    // List<Book> books = new ArrayList<>();
    // this.book = new ArrayList<Book>();
    List<Book> books = new ArrayList<>();
    books.add(book);
    this.books = books;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getBirthYear() {
    return birthYear;
  }

  public void setBirthYear(String birthYear) {
    this.birthYear = birthYear;
  }

  public String getDeathYear() {
    return deathYear;
  }

  public void setDeathYear(String deathYear) {
    this.deathYear = deathYear;
  }

}
