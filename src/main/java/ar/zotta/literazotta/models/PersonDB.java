package ar.zotta.literazotta.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "person")
public class PersonDB {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  String name;

  String birthYear;
  String deathYear;

  // @ManyToMany(mappedBy = personDB, cascade = CascadeType.ALL, fetch =
  // FetcType.EAGER)
  @Transient
  List<Book> books;

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
