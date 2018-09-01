package me.jameshunt.electricsmash;

/**
 * Created by James on 10/7/2014.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


// TO USE:
// Change the package (at top) to match your project.
// Search for "TODO", and make the appropriate changes.
public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";

    // DB Fields
    public static final String KEY_ROWID = "_id";
    public static final int COL_ROWID = 0;
    /*
     * CHANGE 1:
     */
    // TODO: Setup your fields here:
    /*public static final String KEY_NAME = "name";
    public static final String KEY_STUDENTNUM = "studentnum";
    public static final String KEY_FAVCOLOUR = "favcolour";*/

    public static final String KEY_X = "x";
    public static final String KEY_Y = "y";
    public static final String KEY_IMAGELOCATION = "imageLocation";
    public static final String KEY_HOWKILLED = "howKilled";


    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_X = 1;
    public static final int COL_Y = 2;
    public static final int COL_IMAGELOCATION = 3;
    public static final int COL_HOWKILLED = 4;

    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_X, KEY_Y, KEY_IMAGELOCATION, KEY_HOWKILLED};

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "MyDb";
    public static final String DATABASE_TABLE = "mainTable";
    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + KEY_ROWID + " integer primary key autoincrement, "

			/*
			 * CHANGE 2:
			 */
                    // TODO: Place your fields here!
                    // + KEY_{...} + " {type} not null"
                    //	- Key is the column name you created above.
                    //	- {type} is one of: text, integer, real, blob
                    //		(http://www.sqlite.org/datatype3.html)
                    //  - "not null" means it is a required field (must be given a value).
                    // NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
                    + KEY_X + " integer not null, "
                    + KEY_Y + " integer not null, "
                    + KEY_IMAGELOCATION + " integer not null,"
                    + KEY_HOWKILLED + " integer not null"
                    // Rest  of creation:
                    + ");";

    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database.
    public long insertRow(int x, int y, int imageLocation, int howKilled) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        /*initialValues.put(KEY_NAME, name);
        initialValues.put(KEY_STUDENTNUM, studentNum);
        initialValues.put(KEY_FAVCOLOUR, favColour);*/

        initialValues.put(KEY_X, x);
        initialValues.put(KEY_Y, y);
        initialValues.put(KEY_IMAGELOCATION, imageLocation);
        initialValues.put(KEY_HOWKILLED, howKilled);

        // Insert it into the database.
        if(db!=null)
        return db.insert(DATABASE_TABLE, null, initialValues);

        return 5L;
    }

    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        return db.delete(DATABASE_TABLE, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
        if (c.moveToFirst()) {
            do {
                deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    public boolean checkOpen(){
        return db.isOpen();
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Get a specific row (by rowId)
    public Cursor getRow(long rowId) {
        String where = KEY_ROWID + "=" + rowId;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    public Cursor getRow(int x, int y) {
        String where = KEY_X + "=" + x +" AND "+KEY_Y+"="+y;
        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    // Change an existing row to be equal to new data.
    public boolean updateRow( int x, int y, int imageLocation, int howKilled) {
        //String where = KEY_ROWID + "=" + rowId;
        String where = KEY_X + "=" + x +" AND "+KEY_Y+"="+y;
		/*
		 * CHANGE 4:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues newValues = new ContentValues();
        /*newValues.put(KEY_NAME, name);
        newValues.put(KEY_STUDENTNUM, studentNum);
        newValues.put(KEY_FAVCOLOUR, favColour);*/

        newValues.put(KEY_X, x);
        newValues.put(KEY_Y, y);
        newValues.put(KEY_IMAGELOCATION, imageLocation);
        newValues.put(KEY_HOWKILLED, howKilled);

        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }

    public void deleteDB()
    {
        db.delete(DATABASE_TABLE,null,null);
    }



    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

            // Recreate new database:
            onCreate(_db);
        }
    }
}

