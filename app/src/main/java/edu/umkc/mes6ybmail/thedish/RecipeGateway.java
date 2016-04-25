package edu.umkc.mes6ybmail.thedish;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import junit.framework.Assert;

public class RecipeGateway {
    private Context context;

    public RecipeGateway(Context context) {
        this.context = context;
    }

    public ArrayList<Recipe> findForCategory(Category c) {
        ArrayList<Recipe> recipes;
        recipes = new ArrayList<Recipe>();

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] from = { DatabaseHelper.FIELD_ID,
                DatabaseHelper.FIELD_CATEGORY,
                DatabaseHelper.FIELD_RECIPE_NAME};
        Cursor cursor = db.query(DatabaseHelper.RECIPE_TABLE,
                from,
                DatabaseHelper.FIELD_CATEGORY + "=" + c.categoryID(),
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            // Note, we are skipping over the foreign key for
            // course.
            String recipe_name = cursor.getString(2);
            recipes.add(new Recipe(recipe_name, id));
        }

        dbHelper.close();
        return recipes;
    }

    public long insert(long categoryID, // foreign key
                       String recipeName) {
        // insert record into DB and get back primary
        // key for new record.
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_CATEGORY, categoryID);
        values.put(DatabaseHelper.FIELD_RECIPE_NAME, recipeName);
        long dish_id = db.insertOrThrow(DatabaseHelper.RECIPE_TABLE, null, values);

        // Query for the value of the autoincrement field
        String[] from = { DatabaseHelper.FIELD_ID,
                DatabaseHelper.FIELD_CATEGORY,
                DatabaseHelper.FIELD_RECIPE_NAME};

        Cursor cursor = db.query(DatabaseHelper.RECIPE_TABLE,
                from,
                DatabaseHelper.FIELD_CATEGORY + "=" + categoryID + " AND " +
                        DatabaseHelper.FIELD_RECIPE_NAME + "= '" + recipeName + "'",
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
                       String recipeName) throws Exception{
        // Note, this method doesn't actually update the DB, but
        //   it does demonstrate exceptions and assertions.
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] from = { DatabaseHelper.FIELD_ID};
        Cursor cursor = db.query(DatabaseHelper.RECIPE_TABLE,
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
            Assert.assertNotNull("recipeName is null", recipeName);

        int x = 1;
        assert x == 1;
    }

    public boolean delete(long id) { // id is primary key for record
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.delete(DatabaseHelper.RECIPE_TABLE, DatabaseHelper.FIELD_ID + "=" + id, null) > 0;
    }
}

