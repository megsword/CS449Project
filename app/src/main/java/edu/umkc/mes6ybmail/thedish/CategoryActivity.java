package edu.umkc.mes6ybmail.thedish;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {
    public final static String EXTRA_DATA = "edu.umkc.mes6ybmail.thedish.RECIPEDATA";
    static final private String TAG = "The Dish";
    //private ArrayAdapter<String> adapter;
    //private ArrayList<String> listItems = new ArrayList<String>();
    static Category selectedCategory;

    private ArrayList<Category> categories;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);


        Intent intent = getIntent();
        String message = intent.getStringExtra(RecipeActivity.EXTRA_DATA);
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        model mdel = model.instance(getApplicationContext());

        ListView categoriesListView =
                (ListView)this.findViewById(R.id.listOfCategories);

        categories = mdel.getCategories();

        // If no courses, add some
        if (categories.size() == 0) {
            long SE1_category_id = mdel.insertCategory("Appetizers");
            long SE2_category_id = mdel.insertCategory("Beef");
            long SE3_category_id = mdel.insertCategory("Breads");
            long SE4_category_id = mdel.insertCategory("Breakfast");
            long SE5_category_id = mdel.insertCategory("Chicken");
            long SE6_category_id = mdel.insertCategory("Desserts");
            long SE7_category_id = mdel.insertCategory("Pasta");
            long SE8_category_id = mdel.insertCategory("Pork");
            long SE9_category_id = mdel.insertCategory("Salads");
            long SE10_category_id = mdel.insertCategory("Sandwiches");
            long SE11_category_id = mdel.insertCategory("Seafood");
            long SE12_category_id = mdel.insertCategory("Side Dishes");
            long SE13_category_id = mdel.insertCategory("Slow Cookers");
            long SE14_category_id = mdel.insertCategory("Snacks");
            long SE15_category_id = mdel.insertCategory("Soups");

            categories = mdel.getCategories();
        }

        ArrayAdapter<Category> arrayAdapter =
                new ArrayAdapter<Category>(this, R.layout.listitem , categories);
        categoriesListView.setAdapter(arrayAdapter);

        categoriesListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {
        selectedCategory = categories.get(position);

        model mdel = model.instance(this);
        //ArrayList<Recipe> recipes = mdel.getRecipes(selectedCategory);

       // Toast.makeText(this, "Recipes: " + recipes.toString(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(EXTRA_DATA, "You may add your recipes to this page.");
        startActivity(intent);
    }

    //public static long getCategoryID()
   // {
        //return CategoryActivity.selectedCategory.categoryID();
   // }

    public void updateCategory(int categoryID, String categoryName) {
        model mdel = model.instance(this);
        try {
            mdel.updateCategory(categoryID, categoryName);
        } catch (Exception e) {
            Toast.makeText(this,"***Error. Course " + categoryID + " doesn't exist.",Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home){
            //go to home page
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.nav_recipes) {
        }

        if (id == R.id.nav_shop){
            Intent intent = new Intent(this, ShoppingListActivity.class);
            intent.putExtra(EXTRA_DATA, "Add your grocery items to this page.");
            startActivity(intent);
            return true;
        }
        if (id == R.id.nav_meals){
            Intent intent = new Intent(this, MealPlan.class);
            intent.putExtra(EXTRA_DATA, "Add your meals to this page.");
            startActivity(intent);
            return true;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
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

