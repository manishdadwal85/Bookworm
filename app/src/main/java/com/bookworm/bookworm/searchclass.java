package com.bookworm.bookworm;

import java.util.HashMap;

public class searchclass {

    public String author;
    public String category;
    public String condition;
    public String description;
    public String format;
    public String language;
    public String name;
    public String price;
    public String rent;
    public String tenure;
    public String back_image;
    public String front_image;
    public String bookid;

    public searchclass(HashMap<String,String> mached, String bookid) {
        this.author = mached.get("author");
        this.category = mached.get("category");
        this.condition = mached.get("condition");
        this.description = mached.get("description");
        this.format = mached.get("format");
        this.language = mached.get("language");
        this.name = mached.get("name");
        this.price = mached.get("price");
        this.rent = mached.get("rent");
        this.tenure = mached.get("tenure");
        this.front_image = mached.get("front_image");
        this.back_image = mached.get("back_image");
        this.bookid = bookid;
    }
}
