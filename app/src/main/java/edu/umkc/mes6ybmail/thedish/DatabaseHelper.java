package edu.umkc.mes6ybmail.thedish;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "cookbook.db" ;
    // version can be any number. A change in version number
    // give the program the opportunity to recreate or
    // update the db.
    private static final int DATABASE_VERSION = 1;

    public static final String CATEGORY_TABLE = "Categories";
    public static final String RECIPE_TABLE = "Recipes";

    // Both tables have an _id field.
    // By convention, the first column is always called _id.
    public static final String FIELD_ID = "_id";

    // Fields defined for table Courses
    public static final String FIELD_CATEGORY_NAME = "category_name";

    // Fields defined for table Assignments
    // course_id is a foreign key
    public static final String FIELD_CATEGORY = "category_id";
    public static final String FIELD_RECIPE_NAME = "recipe_name";

    // SQL Statement to create a new database tables
    private static final String DATABASE_CREATE_CATEGORIES = "create table " +
            CATEGORY_TABLE + " (" + FIELD_ID + " integer primary key autoincrement, " +
            FIELD_CATEGORY_NAME + " text not null);";

    private static final String DATABASE_CREATE_RECIPES = "create table " +
            RECIPE_TABLE + " (" + FIELD_ID + " integer primary key autoincrement, " +
            FIELD_CATEGORY + " integer, " +
            FIELD_RECIPE_NAME + " text not null);";


    public DatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_CATEGORIES);
        database.execSQL(DATABASE_CREATE_RECIPES);
    }

    // Method is called during an upgrade of the database, e.g. if you increase
    // the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + CATEGORY_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + RECIPE_TABLE);
        onCreate(database);
    }

}

