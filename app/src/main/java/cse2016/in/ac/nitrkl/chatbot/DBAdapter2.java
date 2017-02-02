package cse2016.in.ac.nitrkl.chatbot;

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
    public static final String KEY_AREA = "area";
    public static final String KEY_LEVEL = "level";
    public static final String KEY_LOCK = "lock";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_FINALANS = "finalans";
    public static final String KEY_SOLVED = "solved";
    public static final String KEY_STORY = "story";
    public static final String KEY_TIME = "time";

    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_AREA = 1;
    public static final int COL_LEVEL = 2;
    public static final int COL_LOCK = 3;
    public static final int COL_QUESTION = 4;
    public static final int COL_FINALANS = 5;
    public static final int COL_SOLVED = 6;
    public static final int COL_STORY = 7;
    public static final int COL_TIME = 8;

    public static final String[] ALL_KEYS = new String[]{KEY_ROWID, KEY_AREA, KEY_LEVEL, KEY_LOCK,KEY_QUESTION,KEY_FINALANS,KEY_SOLVED,KEY_STORY,KEY_TIME};

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_NAME = "MyDb2";
    public static final String DATABASE_TABLE = "AreaTable";
    // Track DB version if a new version of your app changes the format.
    public static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE
                    + " (" + KEY_ROWID + " integer primary key, "
                    + KEY_AREA + " varchar not null, "
                    + KEY_LEVEL + " integer not null default 1, "
                    + KEY_LOCK + " integer not null default 0, "
                    + KEY_QUESTION + " varchar not null, "
                    + KEY_FINALANS + " varchar not null, "
                    + KEY_SOLVED + " integer not null default 0,"
                    + KEY_STORY + " varchar not null, "
                    + KEY_TIME + " varchar"
                    + ");";


    // DB Fields
    public static final String KEY_ROWID2 = "_id";
    public static final int COL_ROWID2 = 0;
    /*
     * CHANGE 1:
     */
    // TODO: Setup your fields here:
    public static final String KEY_AREA2 = "area";
    public static final String KEY_LEVEL2 = "level";
    public static final String KEY_QUESTION2 = "question";
    public static final String KEY_ANS2 = "ans";
    public static final String KEY_CORRECT2 = "correct";
    public static final String KEY_BLNO2 = "blno";
    public static final String KEY_FINALANS2 = "finalans";
    public static final String KEY_SOLVED2 = "solved";
    public static final String KEY_TIME2 = "time";

    // TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
    public static final int COL_AREA2 = 1;
    public static final int COL_LEVEL2 = 2;
    public static final int COL_QUESTION2 = 3;
    public static final int COL_ANS2 = 4;
    public static final int COL_CORRECT2 = 5;
    public static final int COL_BLNO2 = 6;
    public static final int COL_FINALANS2 = 7;
    public static final int COL_SOLVED2 = 8;
    public static final int COL_TIME2 = 9;

    public static final String[] ALL_KEYS2 = new String[]{KEY_ROWID2, KEY_AREA2, KEY_LEVEL2, KEY_QUESTION2, KEY_ANS2, KEY_CORRECT2, KEY_BLNO2, KEY_FINALANS2, KEY_SOLVED2, KEY_TIME2};

    // DB info: it's name, and the table we are using (just one).
    public static final String DATABASE_TABLE2 = "QuestionTable";
    // Track DB version if a new version of your app changes the format.

    private static final String DATABASE_CREATE_SQL2 =
            "create table " + DATABASE_TABLE2
                    + " (" + KEY_ROWID2 + " integer primary key autoincrement, "
                    + KEY_AREA2 + " varchar not null, "
                    + KEY_LEVEL2 + " integer not null, "
                    + KEY_QUESTION2 + " varcher not null, "
                    + KEY_ANS2 + " varchar not null, "
                    + KEY_CORRECT2 + " integer not null DEFAULT 0, "
                    + KEY_BLNO2 + " varcher not null, "
                    + KEY_FINALANS2 + " varchar not null, "
                    + KEY_SOLVED2 + " integer not null DEFAULT 0, "
                    + KEY_TIME2 + " varcher not null "
                    + ");";


    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper2 myDBHelper;
    private SQLiteDatabase db;

    public DBAdapter2(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper2(context);
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

    public long insertRow(int i,String area, String ques, String ans, String story) {
		/*
		 * CHANGE 3:
		 */
        // TODO: Update data in the row with new fields.
        // TODO: Also change the function's arguments to be what you need!
        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROWID, i);
        initialValues.put(KEY_AREA, area);
        initialValues.put(KEY_QUESTION, ques);
        initialValues.put(KEY_FINALANS, ans);
        initialValues.put(KEY_STORY, story);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    // Get a specific row (by rowId)
    public Cursor getRow(String area) {
        String where = KEY_AREA + "='" + area +"'";
        Cursor c = db.query(true, DATABASE_TABLE, ALL_KEYS,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateLock(String area, int lock) {
        String where = KEY_AREA + "='" + area +"'";
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_LOCK, lock);
        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }

    public boolean updateLevel(String area, int level) {
        String where = KEY_AREA + "='" + area +"'";
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_LEVEL, level);
        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }

    public boolean updateSolved(String area, int solved, String time) {
        String where = KEY_AREA + "='" + area +"'";
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_SOLVED, solved);
        newValues.put(KEY_TIME, time);
        // Insert it into the database.
        return db.update(DATABASE_TABLE, newValues, where, null) != 0;
    }


    //    --------------------------------------------------------------------------
//TABLE 2 CODE
// Get a specific row (by rowId)
//    public long insertRow2(String area, String ques, String ans, String story) {
//		/*
//		 * CHANGE 3:
//		 */
//        // TODO: Update data in the row with new fields.
//        // TODO: Also change the function's arguments to be what you need!
//        // Create row's data:
//        ContentValues initialValues = new ContentValues();
//        initialValues.put(KEY_AREA, area);
//        initialValues.put(KEY_QUESTION, ques);
//        initialValues.put(KEY_FINALANS, ans);
//        initialValues.put(KEY_STORY, story);
//
//        // Insert it into the database.
//        return db.insert(DATABASE_TABLE, null, initialValues);
//    }

    public Cursor getRow2(String area) {
        String where = KEY_AREA2 + "='" + area +"'";
        Cursor c = db.query(true, DATABASE_TABLE2, ALL_KEYS2,
                where, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateCorrect2(String area, int level, int correct) {
        String where = KEY_AREA2 + "='" + area + "' and " + KEY_LEVEL2 + "=" + level;
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_CORRECT2, correct);
        // Insert it into the database.
        return db.update(DATABASE_TABLE2, newValues, where, null) != 0;
    }

    public boolean updateSolved2(String area, int level, int solved, String time) {
        String where = KEY_AREA2 + "='" + area + "' and " + KEY_LEVEL2 + "=" + level;
        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_SOLVED2, solved);
        newValues.put(KEY_TIME2, time);
        // Insert it into the database.
        return db.update(DATABASE_TABLE2, newValues, where, null) != 0;
    }

//-----------------------------------------------------------------------------------------


    /////////////////////////////////////////////////////////////////////
    //	Private Helper Classes:
    /////////////////////////////////////////////////////////////////////

    /**
     * Private class which handles database creation and upgrading.
     * Used to handle low-level database access.
     */
    private static class DatabaseHelper2 extends SQLiteOpenHelper {
        DatabaseHelper2(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
            _db.execSQL(DATABASE_CREATE_SQL2);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE2);

            // Recreate new database:
            onCreate(_db);
        }
    }
}
