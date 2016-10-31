package com.springs.springs.model;


public class DetailedImage {

    private int icon;
    private String title;
    private String author;
    private String description;

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor(){
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DetailedImage(String title, String author, String description, int icon)
    {
        this.icon = icon;
        this.title = title;
        this.description = description;
        this.author = author;

    }
}
