package com.springs.springs.model;


public class DetailedImage {

    private int icon;
    private String title;
    private String iconurl;
    private String author;
    private String description;
    private int id;
    private String date;
    private boolean isFeatured;
    private boolean isInLibrary;

    public boolean isInLibrary() {
        return isInLibrary;
    }

    public void setInLibrary(boolean inLibrary) {
        isInLibrary = inLibrary;
    }

    public boolean isFeatured() {
        return isFeatured;
    }

    public void setFeatured(boolean featured) {
        isFeatured = featured;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIconurl() {
        return iconurl;
    }

    public void setIconurl(String iconurl) {
        this.iconurl = iconurl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
        this.isFeatured = true;
        this.isInLibrary = false;

    }

    public DetailedImage(){
        this.iconurl = "";
        this.isFeatured = true;
        this.isInLibrary = false;
    }
}
