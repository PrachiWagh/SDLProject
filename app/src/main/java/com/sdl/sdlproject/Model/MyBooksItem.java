package com.sdl.sdlproject.Model;

public class MyBooksItem {
    String title,issueDate,returnDate;

    public MyBooksItem(String title, String issueDate, String returnDate) {
        this.title = title;
        this.issueDate = issueDate;
        this.returnDate = returnDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }
}
