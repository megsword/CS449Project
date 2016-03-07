package edu.umkc.mes6ybmail.thedish;

import java.io.Serializable;
import java.util.Random;

public class Category implements Serializable {
    private String categoryName;

    // private key. id will be set by the DB when the record
    // for this course is inserted into the Course table
    private long id;


    public Category(String categoryName, long id) {
        this.categoryName = categoryName;
        this.id = id;
    }

    public String toString() {
        return categoryName;
    }

    public String categoryName() {
        return categoryName;
    }

    public long categoryID() {
        return id;
    }
}
