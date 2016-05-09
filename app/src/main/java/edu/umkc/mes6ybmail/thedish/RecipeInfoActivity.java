package edu.umkc.mes6ybmail.thedish;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Assert;

import java.util.ArrayList;

public class RecipeInfoActivity extends AppCompatActivity{
    public final static String EXTRA_DATA = "edu.umkc.mes6ybmail.thedish";
    static final private String TAG = "The Dish";
    private static final String PREFS_NAME = "PrefsFile1";
    private static Context recipeInfoCcontext;
    public static int position;
    private static final int NUMBER_OF_TABS = 3;
    private Fragment[] tabList = new Fragment[NUMBER_OF_TABS];

    private SectionsPagerAdapter mSectionsPagerAdapter;

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeinfo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String recipeInfoMessage = intent.getStringExtra(RecipeInfoActivity.EXTRA_DATA);
        RecipeInfoActivity.recipeInfoCcontext = getApplicationContext();
        CharSequence recipeInfoText = recipeInfoMessage;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(recipeInfoCcontext, recipeInfoText, duration);
        toast.show();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
    }

    public static Context getAppContext() {
        return RecipeInfoActivity.recipeInfoCcontext;
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener{
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private ArrayAdapter<String> recipeInfoArrayAdapter;
        private ArrayList<String> recipeInfoListItems = new ArrayList<String>();
        public static TextView recipeInfoText;
        int listNum = 0;

        public PlaceholderFragment() {
        }
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            position = sectionNumber;
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.content_recipeinfo, container, false);
            View recipeButton = rootView.findViewById(R.id.addDetail);
            View homeButton = rootView.findViewById(R.id.home);
            View fabButton = rootView.findViewById(R.id.fab);
            recipeButton.setOnClickListener(this);
            homeButton.setOnClickListener(this);
            fabButton.setOnClickListener(this);

            ListView listView = (ListView)rootView.findViewById(R.id.listOfDetails);
            if (savedInstanceState != null)
                recipeInfoListItems = savedInstanceState.getStringArrayList("details");
            else
                recipeInfoListItems = new ArrayList<String>();
            recipeInfoArrayAdapter = new ArrayAdapter<String>(RecipeInfoActivity.getAppContext(), R.layout.listitem , recipeInfoListItems);

            listView.setAdapter(recipeInfoArrayAdapter);
            listView.setOnItemClickListener(this);
            recipeInfoArrayAdapter.notifyDataSetChanged();

            SharedPreferences settings = RecipeInfoActivity.getAppContext().getSharedPreferences(PREFS_NAME, 0);
            listNum = settings.getInt("listNumber", 0);
            for (int i = 0; i < listNum; i++) {
                String item;
                item = settings.getString(String.valueOf(i), "");
                recipeInfoListItems.add(item);
            }
            recipeInfoArrayAdapter.notifyDataSetChanged();

            return rootView;
        }

        @Override
        public void onClick(View v) {
            recipeInfoText = (TextView) getView().findViewById(R.id.editText);
            if (v.getId() == R.id.addDetail) {
                Assert.assertNotNull(v);
                // Get string entered
                // Add string to underlying data structure
                try {
                    insertItem(recipeInfoText.getText().toString());
                }
                catch (RecipeException ex) {
                    System.out.println("Please enter a recipe");
                }
                recipeInfoText.setText("");
            }
            if (v.getId() == R.id.home)
            {
                Intent intent = new Intent(MainActivity.getAppContext(), MainActivity.class);
                startActivity(intent);
            }
            if (v.getId() == R.id.fab)
            {
                String text = "Enter recipe ingredients on page 1.\n" +
                        "Enter recipe instructions on page 2.\n" +
                        "Enter recipe hints on page 3.\n";
                Toast toast = Toast.makeText(RecipeInfoActivity.getAppContext(), text, Toast.LENGTH_LONG);
                toast.show();
            }
        }

        public void insertItem(String rec) throws RecipeException{
            if (rec == "" || rec.trim().equals("") || rec == null) {
                RecipeException errorRecipe = new RecipeException("Recipe title is empty.");
                throw errorRecipe;
            }
            else {
                // Notify adapter that underlying data structure changed
               // recipeInfoListItems.add(rec);
                //recipeInfoArrayAdapter.notifyDataSetChanged();
                recipeInfoListItems.add(rec);
                recipeInfoText.setText("");
                // Notify adapter that underlying data structure change
                recipeInfoArrayAdapter.notifyDataSetChanged();
                SharedPreferences settings = RecipeInfoActivity.getAppContext().getSharedPreferences(PREFS_NAME, 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(String.valueOf(listNum), rec);
                ++listNum;
                editor.putInt("listNumber", listNum);
                editor.commit();
            }
        }

        class RecipeException extends Exception {
            public RecipeException() {}
            public RecipeException(String msg) {
                super(msg);
            }
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Get item from ListView
            RecipeInfo item = (RecipeInfo) parent.getItemAtPosition(position);
            String text = "You selected item " + position +
                    " value = " + item;
            // Use a toast message to show which item selected
            Toast toast = Toast.makeText(RecipeInfoActivity.getAppContext(), text, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public static class SectionsPagerAdapter extends FragmentPagerAdapter {

        public static int location;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0: {
                    return "1";
                }
                case 1: {
                    return "2";
                }
                case 2: {
                    return "3";
                }
            }
            return null;
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
        super.onSaveInstanceState(icicle);
    }

    @Override
    protected void onRestoreInstanceState(Bundle icicle) {
        super.onRestoreInstanceState(icicle);
        Log.i(TAG, "onRestoreInstanceState()");
    }
}

