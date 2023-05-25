package controllers;

public class Book {
    private String bookId;
    private String title;
    private String author;
    private String year;
    private String category;
    private String status;
    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
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
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public Book(String bookId, String title, String author, String year, String category, String status) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
        this.category = category;
        this.status = status;
    }
}
