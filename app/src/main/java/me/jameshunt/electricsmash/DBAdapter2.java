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
public class DBAdapter2 {

    /////////////////////////////////////////////////////////////////////
    //	Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter2";

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

    public static final String KEY_SCORE = "score";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_HIGHSCORE = "highscore";
    public static final String KEY_HIGHMULTI = "highMulti";
    public static final String KEY_PROGRESS = "progress";
    public static final String KEY_CONNECTED = "connected";
    public static final String KEY_SHUFFLE = "shuffle";


    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_SCORE = 1;
    public static final int COL_LEVEL = 2;
    public static final int COL_HIGHSCORE = 3;
    public static final int COL_PROGRESS = 4;
    public static final int COL_HIGHMULTI = 5;
    public static final int COL_CONNECTED = 6;
    public static final int COL_SHUFFLE = 7;

    public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_SCORE, KEY_LEVEL, KEY_HIGHSCORE,KEY_PROGRESS,KEY_HIGHMULTI,KEY_CONNECTED,KEY_SHUFFLE};
    //public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_SCORE, KEY_LEVEL, KEY_HIGHSCORE,KEY_PROGRESS,KEY_HIGHMULTI,KEY_CONNECTED};

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "MyDb2";
    public static final String DATABASE_TABLE = "ScoreTable";
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
                    + KEY_SCORE + " integer not null, "
                    + KEY_LEVEL + " integer not null, "
                    + KEY_HIGHSCORE + " integer not null, "
                    + KEY_PROGRESS + " integer not null,"
                    + KEY_HIGHMULTI + " integer not null,"
                    + KEY_CONNECTED + " integer not null,"
                    + KEY_SHUFFLE + " integer not null"
                    // Rest  of creation:
                    + ");";

    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;

    /////////////////////////////////////////////////////////////////////
    //	Public methods:
    /////////////////////////////////////////////////////////////////////

    public DBAdapter2(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper(context);
    }

    // Open the database connection.
    public DBAdapter2 open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database.
    public long insertRow(int score, int level, int highscore, int progress,int multi,int connected,int shuffle) {
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

        initialValues.put(KEY_SCORE, score);
        initialValues.put(KEY_LEVEL, level);
        initialValues.put(KEY_HIGHSCORE, highscore);
        initialValues.put(KEY_PROGRESS, progress);
        initialValues.put(KEY_HIGHMULTI, multi);
        initialValues.put(KEY_CONNECTED, connected);
        initialValues.put(KEY_SHUFFLE, shuffle);


        // Insert it into the database.
        deleteAll();
        return db.insert(DATABASE_TABLE, null, initialValues);
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

    //SELECT * FROM tablename ORDER BY column DESC LIMIT 1;

    public Cursor getRow() {
        //String where = KEY_ROWID + "=" + rowId;
        String order = 1+" DESC";
        String limit ="1";

        Cursor c = 	db.query(true, DATABASE_TABLE, ALL_KEYS,
                null,null,null,null,order,limit);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    // Change an existing row to be equal to new data.
    public boolean updateRow(long rowId, int score, int level, int highscore, int progress) {
        String where = KEY_ROWID + "=" + rowId;

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

        newValues.put(KEY_SCORE, score);
        newValues.put(KEY_LEVEL, level);
        newValues.put(KEY_HIGHSCORE, highscore);
        newValues.put(KEY_PROGRESS, progress);

        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
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

