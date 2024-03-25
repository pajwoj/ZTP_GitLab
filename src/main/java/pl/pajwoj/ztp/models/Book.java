package pl.pajwoj.ztp.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "books")
public class Book implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long IDbook;

    @Column(unique = true, nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    public Book() {}

    public Book(String isbn, String name, String author) {
        this.isbn = isbn;
        this.name = name;
        this.author = author;
    }

    public Long getIDbook() {
        return IDbook;
    }

    public void setIDbook(Long IDbook) {
        this.IDbook = IDbook;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

