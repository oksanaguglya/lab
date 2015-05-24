package by.bsu.guglya.library.bean;

public class Book {

    public enum TypeOfBook{
        HOME, READING_ROOM
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
