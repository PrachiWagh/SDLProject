package com.sdl.sdlproject.Model;

public class Books {
    private String title,author,category,publication,shelf;
    private  int totalCopy,availableCopy;

    public Books() {
    }

    public Books(String title, String author, String category, String publication, String shelf, int noOfCopies, int availableCopies) {
        this.title = title;
        this.author = author;
        this.category = category;
        this.publication = publication;
        this.shelf = shelf;
        this.totalCopy = noOfCopies;
        this.availableCopy = availableCopies;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPublication() {
        return publication;
    }

    public void setPublication(String publication) {
        this.publication = publication;
    }

    public String getShelf() {
        return shelf;
    }

    public void setShelf(String shelf) {
        this.shelf = shelf;
    }

    public int getTotalCopy() {
        return totalCopy;
    }

    public void setTotalCopy(int noOfCopies) {
        this.totalCopy = noOfCopies;
    }

    public int getAvailableCopy() {
        return availableCopy;
    }

    public void setAvailableCopy(int availableCopies) {
        this.availableCopy = availableCopies;
    }

}
