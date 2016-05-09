package edu.umkc.mes6ybmail.thedish;


import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class MenuActivity extends AppCompatActivity {
    public final static String EXTRA_DATA = "edu.umkc.mes6ybmail.thedish";
    public static Context menuContext = MainActivity.getAppContext();

    public static Context getAppContext() {
        return menuContext;
    }

    public static Intent performMenuSelection(int id) {
        if (id == R.id.nav_home) {
            Intent intent = new Intent(getAppContext(), MainActivity.class);
            return intent;
        }

        if (id == R.id.nav_recipes) {
            //go to recipes page
            Intent intent = new Intent(getAppContext(), CategoryActivity.class);
            intent.putExtra(EXTRA_DATA, "Here are your recipe categories.");
            return intent;
        }
        if (id == R.id.nav_favs) {
            Intent intent = new Intent(getAppContext(), FavoriteActivity.class);
            intent.putExtra(EXTRA_DATA, "Add your favorite recipes to this page.");
            return intent;
        }
        if (id == R.id.nav_shop) {
            Intent intent = new Intent(getAppContext(), ShoppingListActivity.class);
            intent.putExtra(EXTRA_DATA, "Add your grocery items to this page.");
            return intent;
        }

        if (id == R.id.nav_meals) {
            Intent intent = new Intent(getAppContext(), MealPlanActivity.class);
            intent.putExtra(EXTRA_DATA, "Add your meals to this page.");
            return intent;
        }
        Intent intent = new Intent(getAppContext(), MainActivity.class);
        return intent;
    }

}

