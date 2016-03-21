package edu.umkc.mes6ybmail.thedish;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import junit.framework.Assert;

import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener {
    public final static String EXTRA_DATA = "edu.umkc.mes6ybmail.thedish.RECIPEDATA";
    static final private String TAG = "The Dish";
    private ArrayAdapter<String> adapter;
    private ArrayList<String> listItems = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

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

        View prevButton = findViewById(R.id.button1);
        prevButton.setOnClickListener(this);

        ListView listView = (ListView)this.findViewById(R.id.listOfSomething);
        adapter = new ArrayAdapter<String>(this, R.layout.listitem2 , listItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    // This is for button clicks
    @Override
    public void onClick(View arg0) {
        Assert.assertNotNull(arg0);
        // Get string entered
        TextView tv = (TextView) findViewById(R.id.editText1);
        // Add string to underlying data structure
        listItems.add(tv.getText().toString());
        // Notify adapter that underlying data structure changed
        adapter.notifyDataSetChanged();
        tv.setText("");
    }

    // This is for selecting an item from the list
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // Get item from ListView
        //String item = (String) parent.getItemAtPosition(position);
        //item = "@+string/recipe_title";
        //String text = "You selected item " + position +
               //" value = " + item;
        // Use a toast message to show which item selected
       //Toast toast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
        //toast.show();
        Intent intent = new Intent(this, RecipeInfo.class);
        intent.putExtra(EXTRA_DATA, "Enter recipe details here.");
        startActivity(intent);
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

        Intent intent = new Intent(this, CategoryActivity.class);
        intent.putExtra(EXTRA_DATA, "Here are your recipe categories.");
        startActivity(intent);
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

