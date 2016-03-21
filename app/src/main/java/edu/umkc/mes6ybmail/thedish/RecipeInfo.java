package edu.umkc.mes6ybmail.thedish;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import junit.framework.Assert;

import java.util.ArrayList;

public class RecipeInfo extends AppCompatActivity{
    public final static String EXTRA_DATA = "edu.umkc.mes6ybmail.thedish.RECIPEDATA";
    static final private String TAG = "The Dish";
    private static Context context;
    public static int position;

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipeinfo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarf);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String message = intent.getStringExtra(RecipeActivity.EXTRA_DATA);
        RecipeInfo.context = getApplicationContext();
        CharSequence text = message;
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        //toast.show();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        model mdel = model.instance(getApplicationContext());

    }

    public static Context getAppContext() {
        return RecipeInfo.context;
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
        private ArrayAdapter<String> adapter1;
        private ArrayList<String> listItems1 = new ArrayList<String>();

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

            View rootView = inflater.inflate(R.layout.fragment_recipeinfo, container, false);
            View Button = rootView.findViewById(R.id.button);
            View ButtonHome = rootView.findViewById(R.id.home);
            View ButtonF = rootView.findViewById(R.id.fab);
            Button.setOnClickListener(this);
            ButtonHome.setOnClickListener(this);
            ButtonF.setOnClickListener(this);

            ListView listView = (ListView)rootView.findViewById(R.id.listOfSomething1);
            adapter1 = new ArrayAdapter<String>(RecipeInfo.getAppContext(), R.layout.listitem2 , listItems1);
            listView.setAdapter(adapter1);
            listView.setOnItemClickListener(this);

            return rootView;
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.button) {
                Assert.assertNotNull(v);
                // Get string entered
                TextView tv = (TextView) getView().findViewById(R.id.editText);
                // Add string to underlying data structure
                listItems1.add(tv.getText().toString());
                // Notify adapter that underlying data structure changed
                adapter1.notifyDataSetChanged();
                tv.setText("");
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
                        "Enter recipe hints on page 3.";
                Toast toast = Toast.makeText(RecipeInfo.getAppContext(), text, Toast.LENGTH_LONG);
                toast.show();
            }

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // Get item from ListView
            String item = (String) parent.getItemAtPosition(position);
            //item = "@+string/recipe_title";
            String text = "You selected item " + position +
                    " value = " + item;
            // Use a toast message to show which item selected
            Toast toast = Toast.makeText(RecipeInfo.getAppContext(), text, Toast.LENGTH_SHORT);
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

