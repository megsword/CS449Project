package edu.umkc.mes6ybmail.thedish;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import junit.framework.Assert;

public class CategoryGateway {

    private Context context;

    public CategoryGateway(Context context) {
        this.context = context;
    }

    public ArrayList<Category> findAll() {
        ArrayList<Category> categories = new ArrayList<Category>();

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] from = { DatabaseHelper.FIELD_ID, DatabaseHelper.FIELD_CATEGORY_NAME};
        Cursor cursor = db.query(DatabaseHelper.CATEGORY_TABLE, from, null, null, null,
                null, null);

        while (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            String category_name = cursor.getString(1);
            categories.add(new Category(category_name,id));
        }
        dbHelper.close();
        return categories;
    }

    public long insert(String categoryName) {
        // insert record into DB and get back primary
        // key for new record.
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.FIELD_CATEGORY_NAME, categoryName);
        long cs490_id = db.insertOrThrow(DatabaseHelper.CATEGORY_TABLE, null, values);

        // Query for the value of the autoincrement field
        String[] from = { DatabaseHelper.FIELD_ID,
                DatabaseHelper.FIELD_CATEGORY_NAME };

        Cursor cursor = db.query(DatabaseHelper.CATEGORY_TABLE,
                from,
                DatabaseHelper.FIELD_CATEGORY_NAME + "= '" + categoryName + "'",
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
    // courseName is an alpha numeric value including blank but can not be null
    // Throws:
    //   Exception - if there is no record for given id
    public void update(long id, String categoryName) throws Exception {
        // Note, this method doesn't actually update the DB, but
        //   it does demonstrate exceptions and assertions.
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] from = { DatabaseHelper.FIELD_ID};
        Cursor cursor = db.query(DatabaseHelper.CATEGORY_TABLE,
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
            Assert.assertNotNull("categoryName is null",categoryName);

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

    // returns true if delete successful
    public boolean delete(long id) { // id is primary key for record
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        return db.delete(DatabaseHelper.CATEGORY_TABLE, DatabaseHelper.FIELD_ID + "=" + id, null) > 0;
    }
}


