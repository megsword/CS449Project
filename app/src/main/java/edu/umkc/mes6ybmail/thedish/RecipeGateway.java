package edu.umkc.mes6ybmail.thedish;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class RecipeGateway {
    private Context context;

    public RecipeGateway(Context context) {
        this.context = context;
    }

    public ArrayList<Recipe> findForCourse(Category c) {
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
            recipes.add(new Recipe(recipe_name));
        }

        dbHelper.close();
        return recipes;
    }

    public long insert(long courseID, // foreign key
                       String assignmentName) {
        // insert record into DB and get back primary
        // key for new record.
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_CATEGORY, courseID);
        values.put(DatabaseHelper.FIELD_RECIPE_NAME, assignmentName);
        long cs490_id = db.insertOrThrow(DatabaseHelper.RECIPE_TABLE, null, values);

        // Query for the value of the autoincrement field
        String[] from = { DatabaseHelper.FIELD_ID,
                DatabaseHelper.FIELD_CATEGORY,
                DatabaseHelper.FIELD_RECIPE_NAME};

        Cursor cursor = db.query(DatabaseHelper.RECIPE_TABLE,
                from,
                DatabaseHelper.FIELD_CATEGORY + "=" + courseID + " AND " +
                        DatabaseHelper.FIELD_RECIPE_NAME + "= '" + assignmentName + "'",
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
    public void update(int id, int categoryID,
                       String recipeName, int percent,
                       int pointsPossible,
                       int pointsEarned) {

    }

    public void delete(int id) { // id is primary key for record

    }
}

