package by.bsu.guglya.library.model.beans;

public class Book {

    /**
     * Possible places for reading
     */
    public enum TypeOfBook{
        LIBRARY_CARD, READING_ROOM
    }

    /**
     * Book's ID
     */
    private int id;
    /**
     * Book's title
     */
    private String title;
    /**
     * Book's author
     */
    private String author;
    /**
     * Book's year of publishing
     */
    private int year;
    /**
     * Book's possible places for reading
     */
    private TypeOfBook type;

    public Book(int id, String title, String author, int year, TypeOfBook type) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.year = year;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
