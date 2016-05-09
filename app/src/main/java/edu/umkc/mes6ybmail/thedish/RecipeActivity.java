package edu.umkc.mes6ybmail.thedish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import junit.framework.Assert;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener {
    public final static String EXTRA_DATA = "edu.umkc.mes6ybmail.thedish";
    static final private String TAG = "The Dish";
    private ArrayAdapter<Recipe> recipeArrayAdapter;
    static Recipe selectedRecipe;
    private ArrayList<Recipe> recipes;
    static long SE_recipe_id;
    public TextView recipeText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        String recipeMessage = intent.getStringExtra(RecipeActivity.EXTRA_DATA);
        Context recipeContext = getApplicationContext();
        CharSequence recipeText = recipeMessage;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(recipeContext, recipeText, duration);
        toast.show();

        View addRecipeButton = findViewById(R.id.addRecipe);
        addRecipeButton.setOnClickListener(this);

        model mdel = model.instance(getApplicationContext());

        ListView recipeListView =
                (ListView)this.findViewById(R.id.listOfRecipes);

        recipes = mdel.getRecipes(CategoryActivity.selectedCategory);

        recipeArrayAdapter = new ArrayAdapter<Recipe>(this, R.layout.listitem, recipes);
        recipeListView.setAdapter(recipeArrayAdapter);
        recipeListView.setOnItemClickListener(this);
    }

    // This is for button clicks
    @Override
    public void onClick(View arg0) {
        Assert.assertNotNull(arg0);
        // Get string entered
        recipeText = (TextView) findViewById(R.id.editText);
        // Add string to underlying data structure
        // Notify adapter that underlying data structure changed
        try {
            insertItem(recipeText.getText().toString());
        }
        catch (RecipeException ex) {
            System.out.println("Please enter a recipe");
        }
        recipeText.setText("");
    }

    public void insertItem(String rec) throws RecipeException{
        if (rec == "" || rec.trim().equals("") || rec == null) {
            RecipeException errorRecipe = new RecipeException("Recipe title is empty.");
            throw errorRecipe;
        }
        else {
            model mdel = model.instance(this);
            SE_recipe_id = mdel.insertRecipe(CategoryActivity.selectedCategory.categoryID(), rec);
            recipes.clear();
            recipes.addAll(mdel.getRecipes(CategoryActivity.selectedCategory));
            recipeArrayAdapter.notifyDataSetChanged();
        }
    }

    // This is for selecting an item from the list
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        selectedRecipe = recipes.get(position);
        model mdel = model.instance(this);
        Intent intent = new Intent(this, RecipeInfoActivity.class);
        intent.putExtra(EXTRA_DATA, "Enter recipe details here.");
        startActivity(intent);
    }

    public void updateRecipe (int categoryID, String recipeName) {
        model mdel = model.instance(this);
        try {
            mdel.updateRecipe(categoryID, recipeName);
        } catch (Exception e) {
            Toast.makeText(this,"***Error. Recipe " + recipeName + " doesn't exist.",Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int menuId = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        Intent intent = MenuActivity.performMenuSelection(menuId);
        startActivity(intent);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    class RecipeException extends Exception {
        public RecipeException() {}
        public RecipeException(String msg) {
            super(msg);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(TAG, "onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle icicle) {
        super.onSaveInstanceState(icicle);
        Log.i(TAG, "onSaveInstanceState()");
    }

    @Override
    protected void onRestoreInstanceState(Bundle icicle) {
        super.onRestoreInstanceState(icicle);
        Log.i(TAG, "onRestoreInstanceState()");
    }
}

