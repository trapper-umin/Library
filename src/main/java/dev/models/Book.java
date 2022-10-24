package dev.models;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Book {

    private int book_id;
    private int owner;
    @NotEmpty(message = "Book name should be not empty")
   // @UniqueElements(message = "Book name should be unique")
    @Size(min=3,max=300, message = "Book name size should be between 3 and 300")
    private String name;
    @NotEmpty(message = "Author should be not empty")
    @Size(min=3,max=100, message = "Author name size should be between 3 and 100")
    private String author;
    @NotEmpty(message = "Year should be not empty")
    private String year;

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getYear() {
        return year;
    }

    public int getBook_id() {
        return book_id;
    }

    public int getOwner() {
        return owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
}
