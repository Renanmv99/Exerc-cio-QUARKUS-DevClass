package br.com.syonet;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;

@Entity
public class Book extends PanacheEntity {
    public String title;
    public String author;
    public int year;

    public Book(){}

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    @Override
    public String toString() {
        return "Book [title=" + title + ", author=" + author + ", year=" + year + "]";
    }

    
}
