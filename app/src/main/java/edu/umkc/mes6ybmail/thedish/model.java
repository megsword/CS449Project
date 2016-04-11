package edu.umkc.mes6ybmail.thedish;

import java.util.ArrayList;
import android.content.Context;

public class model {

    // Singleton design pattern
    private static model instance = null;

    private CategoryGateway categoryGateway;
    private RecipeGateway recipeGateway;
    private RecipeInfoGateway recipeInfoGateway;

    private model(Context context) {
        categoryGateway = new CategoryGateway(context);
        recipeGateway = new RecipeGateway(context);
        recipeInfoGateway = new RecipeInfoGateway(context);
    }

    public synchronized static model instance(Context context) {
        if( instance == null ) {
            instance = new model(context);
        }
        return instance;
    }

    public ArrayList<Category> getCategories() {
        return categoryGateway.findAll();
    }

    public long insertCategory(String categoryName) {
        return categoryGateway.insert(categoryName);
    }

    public long insertRecipe(long categoryID, String recipeName) {
        return recipeGateway.insert(categoryID, recipeName);
    }

    public long insertRecipeInfo(long recipeID, String recipeInfoName) {
        return recipeInfoGateway.insert(recipeID, recipeInfoName);
    }

    public void updateCategory(long categoryID, String categoryName) throws Exception {
        categoryGateway.update(categoryID,categoryName);
    }

    public void updateRecipe(long categoryID, String recipeName) throws Exception {
        recipeGateway.update(categoryID,recipeName);
    }

    public ArrayList<Recipe> getRecipes(Category c) {
        return recipeGateway.findForCategory(c);
    }

    public ArrayList<RecipeInfo> getRecipeInfo(Recipe r) {
        return recipeInfoGateway.findForRecipe(r);
    }
}

