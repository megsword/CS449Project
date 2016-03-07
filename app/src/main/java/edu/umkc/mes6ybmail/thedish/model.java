package edu.umkc.mes6ybmail.thedish;

import java.util.ArrayList;
import android.content.Context;

public class model {

    // Singleton design pattern
    private static model instance = null;

    private CategoryGateway categoryGateway;
    private RecipeGateway recipeGateway;

    private model(Context context) {
        categoryGateway = new CategoryGateway(context);
        recipeGateway = new RecipeGateway(context);
    }

    public synchronized static model instance(Context context) {
        if( instance == null ) {
            instance = new model(context);
        }
        return instance;
    }

    public ArrayList<Category> getCategory() {
        return categoryGateway.findAll();
    }

    public long insertCategory(String categoryName) {
        return categoryGateway.insert(categoryName);
    }

    public long insertRecipe(long categoryID, String recipeName) {
        return recipeGateway.insert(categoryID, recipeName);
    }

    public void updateCategory(long categoryID, String categoryName) throws Exception {
        categoryGateway.update(categoryID,categoryName);
    }

    public ArrayList<Recipe> getRecipes(Category c) {
        return recipeGateway.findForCourse(c);
    }
}

