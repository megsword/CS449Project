package edu.umkc.mes6ybmail.thedish;

public class RecipeInfo {
    private String RecipeInfoName;

    // private key. id will be determined by DB
    // when the record for this course is inserted
    // into the Assignment table
    private long id;

    public RecipeInfo(String RecipeInfoName) {
        this.RecipeInfoName = RecipeInfoName;
    }

    public String toString() {
        return RecipeInfoName;
    }
}
