package com.android.bookapp;

public class Book
{
    public String id;
    public String title;
    public String subTitle;
    public String[] authors;
    public String publisher;
    public String publishDate;

    public Book(String id, String title , String subTitle , String[] authors , String publisher , String publishDate)
    {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.authors = authors;
        this.publisher = publisher;
        this.publishDate = publishDate;
    }
}
