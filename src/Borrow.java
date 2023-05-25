package controllers;

public class Borrow {
    private String borrowId;
    private String name ;
    private String schoolId ;
    private String borrowDate;
    private String returnDate;
    private String book;
    public String getBorrowId() {
        return borrowId;
    }
    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSchoolId() {
        return schoolId;
    }
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }
    public String getBorrowDate() {
        return borrowDate;
    }
    public void setBorrowDate(String borrowDate) {
        this.borrowDate = borrowDate;
    }
    public String getReturnDate() {
        return returnDate;
    }
    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
    public String getBook() {
        return book;
    }
    public void setBook(String book) {
        this.book = book;
    }
    public Borrow(String borrowId, String name, String schoolId, String borrowDate, String returnDate, String book) {
        this.borrowId = borrowId;
        this.name = name;
        this.schoolId = schoolId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.book = book;
    }
}
