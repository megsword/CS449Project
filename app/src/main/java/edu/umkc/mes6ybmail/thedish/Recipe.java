package edu.umkc.mes6ybmail.thedish;

public class Recipe {
    private String RecipeName;

    // private key. id will be determined by DB
    // when the record for this course is inserted
    // into the Assignment table
    private int id;

    public Recipe(String RecipeName) {
        this.RecipeName = RecipeName;
    }

    public String toString() {
        return RecipeName;
    }
}
