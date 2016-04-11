package edu.umkc.mes6ybmail.thedish;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import junit.framework.Assert;

import java.util.ArrayList;

public class RecipeInfoGateway {
    private Context context;

    public RecipeInfoGateway(Context context) {
        this.context = context;
    }

    public ArrayList<RecipeInfo> findForRecipe(Recipe r) {
        ArrayList<RecipeInfo> recipeInfos;
        recipeInfos = new ArrayList<RecipeInfo>();

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] from = { DatabaseHelper.FIELD_ID,
                DatabaseHelper.FIELD_RECIPE,
                DatabaseHelper.FIELD_RECIPE_INFO_NAME};

        Cursor cursor = db.query(DatabaseHelper.RECIPE_INFO_TABLE,
                from,
                DatabaseHelper.FIELD_RECIPE + "=" + r.recipeID(),
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            // Note, we are skipping over the foreign key for
            // course.
            String recipe_info_name = cursor.getString(2);
            recipeInfos.add(new RecipeInfo(recipe_info_name));
        }

        dbHelper.close();
        return recipeInfos;
    }

    public long insert(long recipeID, // foreign key
                       String recipeInfoName) {
        // insert record into DB and get back primary
        // key for new record.
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_RECIPE, recipeID);
        values.put(DatabaseHelper.FIELD_RECIPE_INFO_NAME, recipeInfoName);
        long dish_id = db.insertOrThrow(DatabaseHelper.RECIPE_INFO_TABLE, null, values);

        // Query for the value of the autoincrement field
        String[] from = { DatabaseHelper.FIELD_ID,
                DatabaseHelper.FIELD_RECIPE,
                DatabaseHelper.FIELD_RECIPE_INFO_NAME};

        Cursor cursor = db.query(DatabaseHelper.RECIPE_INFO_TABLE,
                from,
                DatabaseHelper.FIELD_RECIPE + "=" + recipeID + " AND " +
                        DatabaseHelper.FIELD_RECIPE_INFO_NAME + "= '" + recipeInfoName + "'",
                null,
                null,
                null,
                null);

        cursor.moveToNext();
        long value_of_autoincrement_field = cursor.getLong(0);
        dbHelper.close();
        return value_of_autoincrement_field; // return the primary key created by the DB
    }

    // id is primary key for record to update
    public void update(long id,
                       String recipeInfoName) throws Exception{
        // Note, this method doesn't actually update the DB, but
        //   it does demonstrate exceptions and assertions.
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] from = { DatabaseHelper.FIELD_ID};
        Cursor cursor = db.query(DatabaseHelper.RECIPE_INFO_TABLE,
                from,
                DatabaseHelper.FIELD_ID + "= " + id + "",
                null,
                null,
                null,
                null);
        // If there isn't a record for this ID, Throw an exception
        if (!cursor.moveToNext()){
            Exception e = new Exception("id " + id + " invalid for update");
            throw e;
        }
        if (AssertSettings.PRIORITY1_ASSERTIONS)
            // There are many methods on Assert that can be called
            Assert.assertNotNull("recipeInfoName is null", recipeInfoName);

        // Note, Java also has builtin support for assertion checking.
        // Assertion checking in Java is off by default.
        // To turn on assertion checking, execute the following
        // from the command line (before installing your app):
        // adb shell setprop debug.assert 1
        // To turn it off:
        // adb shell setprop debug.assert 0
        // Note, adb is a program in:
        // C:\Program Files (x86)\Android\android-sdk\platform-tools

        // Builtin Java support for assertion
        int x = 1;
        assert x == 1;
    }

    public boolean delete(int id) { // id is primary key for record
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.delete(DatabaseHelper.RECIPE_INFO_TABLE, DatabaseHelper.FIELD_ID + "=" + id, null) > 0;
    }
}
