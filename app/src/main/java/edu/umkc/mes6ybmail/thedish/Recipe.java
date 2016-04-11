package edu.umkc.mes6ybmail.thedish;

import java.io.Serializable;
import java.util.Random;

public class Recipe implements Serializable {
    private String RecipeName;

    // private key. id will be determined by DB
    // when the record for this course is inserted
    // into the Assignment table
    private long id;

    public Recipe(String RecipeName, long id) {
        this.RecipeName = RecipeName;
        this.id = id;
    }

    public String toString() {
        return RecipeName;
    }

    public String recipeName() {
        return RecipeName;
    }

    public long recipeID() {
        return id;
    }
}
