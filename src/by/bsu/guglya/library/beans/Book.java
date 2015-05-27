package by.bsu.guglya.library.beans;

import by.bsu.guglya.library.managers.ConfigurationManager;

import java.util.ResourceBundle;

public class Book {

    public enum TypeOfBook{
        HOME {
           /* @Override
            public String toString() {
                return ResourceBundle.getBundle("by.bsu.guglya.library.resources.gui").getString("home");
            }*/
        },
        READING_ROOM{
           /* @Override
            public String toString() {
                return ResourceBundle.getBundle("by.bsu.guglya.library.resources.gui").getString("reading_room");
            }*/
        }
    }

    private String title;
    private String author;
    private int year;
    private TypeOfBook type;

    public Book(String title, String author, int year, TypeOfBook type) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public TypeOfBook getType() {
        return type;
    }

    public void setType(TypeOfBook type) {
        this.type = type;
    }
}
