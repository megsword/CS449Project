package edu.umkc.mes6ybmail.thedish;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Assert;

import java.util.ArrayList;

public class MealPlan extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener {
    public final static String EXTRA_DATA = "edu.umkc.mes6ybmail.thedish.RECIPEDATA";
    static final private String TAG = "The Dish";
    private static final String PREFS_NAME = "PrefsFile";
    private static final String CHECK_KEY = "CheckKey";
    static ShoppingListActivity selectedGroceryList;
    private ArrayAdapter<String> adapter1;
    private ArrayList<String> listItems = new ArrayList<String>();
    private String meals = "meals";
    static public CheckBox cb;
    public TextView tv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout3);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view3);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState != null) {
            listItems = savedInstanceState.getStringArrayList("meals");
        }
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        for (int i = 0; ; ++i) {
            final String str = settings.getString(String.valueOf(i), "");
            if (!str.equals("")) {
                adapter1.add(str);
            } else {
                break;
            }
            adapter1.notifyDataSetChanged();
        }

        Intent intent = getIntent();
        String message = intent.getStringExtra(ShoppingListActivity.EXTRA_DATA);
        Context context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        View addButton = findViewById(R.id.button1);
        addButton.setOnClickListener(this);
        View deleteButton = findViewById(R.id.buttonD);
        deleteButton.setOnClickListener(this);

        ListView shopListView =
                (ListView)this.findViewById(R.id.listOfGroceries);

        adapter1 = new ArrayAdapter<String>(context, R.layout.listitemcheck, R.id.textCheck , listItems);
        shopListView.setAdapter(adapter1);
        shopListView.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View arg0) {
        Assert.assertNotNull(arg0);
        // Get string entered
        tv = (TextView) findViewById(R.id.editText1);
        // Add string to underlying data structure
        // Notify adapter that underlying data structure changed
        try {
            insertItem(tv.getText().toString());
        }
        catch (MealException ex) {
            System.out.println("Please enter a recipe");
        }
        tv.setText("");
    }

    public void insertItem(String rec) throws MealException{
        if (rec == "" || rec.trim().equals("") || rec == null) {
            MealException errorRecipe = new MealException("Recipe title is empty.");
            throw errorRecipe;
        }
        else {
            listItems.add(rec);
            // Notify adapter that underlying data structure change
            cb = (CheckBox) findViewById(R.id.checkBox1);
            adapter1.notifyDataSetChanged();
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(meals, rec);
            editor.commit();
        }
    }

    public void addGrocery(String grocery)
    {
        listItems.add(grocery);
        // Notify adapter that underlying data structure changed
        adapter1.notifyDataSetChanged();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(meals, grocery);
        editor.commit();
    }



    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {

        Toast.makeText(this, "Meals: " + listItems.toString(), Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(this, CategoryActivity.class);
            intent.putExtra(EXTRA_DATA, "Here are your recipe categories.");
            startActivity(intent);
            return true;
        }

        if (id == R.id.nav_shop){
            Intent intent = new Intent(this, CategoryActivity.class);
            intent.putExtra(EXTRA_DATA, "Add your grocery items to this page.");
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

    class MealException extends Exception {
        public MealException() {}
        public MealException(String msg) {
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
        //icicle.getStringArrayList("groceries");
        icicle.putStringArrayList("groceries", listItems);

        super.onSaveInstanceState(icicle);

    }

    @Override
    protected void onRestoreInstanceState(Bundle icicle) {
        super.onRestoreInstanceState(icicle);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        listItems = icicle.getStringArrayList("groceries");
        super.onRestoreInstanceState(icicle);
        Log.i(TAG, "onRestoreInstanceState()");
    }
}

