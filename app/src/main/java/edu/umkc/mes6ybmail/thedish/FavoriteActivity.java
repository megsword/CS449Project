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

public class FavoriteActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, AdapterView.OnItemClickListener{
    public final static String EXTRA_DATA = "edu.umkc.mes6ybmail.thedish";
    static final private String TAG = "The Dish";
    private static final String PREFS_NAME = "FavoritesPrefs";
    private static final String CHECK_KEY = "FavoritesCheckKey";
    private ArrayAdapter<String> favoritesArrayAdapter;
    private ArrayList<String> favoriteListItems;
    static public CheckBox cb;
    public TextView favoritesText;
    int listNum = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extras);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View addButton = findViewById(R.id.addExtra);
        addButton.setOnClickListener(this);
        View deleteButton = findViewById(R.id.deleteExtra);
        deleteButton.setOnClickListener(this);

        ListView favListView =
                (ListView) this.findViewById(R.id.listOf);

        if (savedInstanceState != null)
            favoriteListItems = savedInstanceState.getStringArrayList("favorites");
        else
            favoriteListItems = new ArrayList<String>();
        favoritesArrayAdapter = new ArrayAdapter<String>(this, R.layout.listitemcheck, R.id.textCheck , favoriteListItems);

        favListView.setAdapter(favoritesArrayAdapter);
        favListView.setOnItemClickListener(this);
        favoritesArrayAdapter.notifyDataSetChanged();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        listNum = settings.getInt("listNumber", 0);
        for (int i = 0; i < listNum; i++) {
            String item;
            item = settings.getString(String.valueOf(i), "");
            favoriteListItems.add(item);
        }
        favoritesArrayAdapter.notifyDataSetChanged();

        Intent intent = getIntent();
        String favoriteMessage = intent.getStringExtra(FavoriteActivity.EXTRA_DATA);
        Context favoriteContext = getApplicationContext();
        CharSequence favoriteText = favoriteMessage;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(favoriteContext, favoriteText, duration);
        toast.show();
    }

    @Override
    public void onClick(View arg0) {
        Assert.assertNotNull(arg0);
        // Get string entered
        favoritesText = (TextView) findViewById(R.id.editText);
        // Add string to underlying data structure
        // Notify adapter that underlying data structure changed
        if (arg0.getId() == R.id.addExtra) {
            try {
                insertItem(favoritesText.getText().toString());
            } catch (FavException ex) {
                System.out.println("Please enter a recipe");
            }
            favoritesText.setText("");
        }
        if (arg0.getId() == R.id.deleteExtra)
        {
            //SparseBooleanArray checkedItemPositions = shopListView.getCheckedItemPositions();
            //int itemCount = getView().getCount();

            //for(int i=itemCount-1; i >= 0; i--){
            //   if(checkedItemPositions.get(i)){
            //    adapter1.remove(favoriteListItems.get(i));
            //}
            //}
            // checkedItemPositions.clear();
            // adapter1.notifyDataSetChanged();
        }
    }

    public void insertItem(String rec) throws FavException{
        if (rec == "" || rec.trim().equals("") || rec == null) {
            FavException errorRecipe = new FavException("Recipe title is empty.");
            throw errorRecipe;
        }
        else {
            favoriteListItems.add(rec);
            // Notify adapter that underlying data structure change
            cb = (CheckBox) findViewById(R.id.checkBox);
            favoritesArrayAdapter.notifyDataSetChanged();
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();

            editor.putString(String.valueOf(listNum), rec);
            ++listNum;
            editor.putInt("listNumber", listNum);
            editor.commit();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View v,
                            int position, long id) {

        Toast.makeText(this, "Favorites: " + favoriteListItems.toString(), Toast.LENGTH_SHORT).show();
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

    class FavException extends Exception {
        public FavException() {}
        public FavException(String msg) {
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
        icicle.putStringArrayList("favorites", favoriteListItems);
        super.onSaveInstanceState(icicle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle icicle) {
        super.onRestoreInstanceState(icicle);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        Log.i(TAG, "onRestoreInstanceState()");
    }
}

