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
	public static final String KEY_SENDER= "sMemId";
	public static final String KEY_MESSAGE = "msg";
	public static final String KEY_TIMESTAMP = "timeStamp";
	
	// TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_SENDER = 1;
	public static final int COL_MESSAGE = 2;
	public static final int COL_TIMESTAMP = 3;

	
	public static final String[] ALL_KEYS = new String[] {KEY_ROWID, KEY_SENDER, KEY_MESSAGE, KEY_TIMESTAMP};
	
	// DB info: it's name, and the table we are using (just one).
	public static final String DATABASE_NAME = "talky";
	public static String DATABASE_TABLE = "";
	// Track DB version if a new version of your app changes the format.
	public static final int DATABASE_VERSION = 1;
	
	private static String DATABASE_CREATE_SQL ="";
//			"create table if not exist" + DATABASE_TABLE
//			+ " (" + KEY_ROWID + " integer primary key autoincrement, "
//
//			/*
//			 * CHANGE 2:
//			 */
//			// TODO: Place your fields here!
//			// + KEY_{...} + " {type} not null"
//			//	- Key is the column name you created above.
//			//	- {type} is one of: text, integer, real, blob
//			//		(http://www.sqlite.org/datatype3.html)
//			//  - "not null" means it is a required field (must be given a value).
//			// NOTE: All must be comma separated (end of line!) Last one must have NO comma!!
//			+ KEY_SENDER + " integer not null, "
//			+ KEY_MESSAGE+ " varchar not null, "
//			+ KEY_TIMESTAMP + " varchar not null"
//
//			// Rest  of creation:
//			+ ");";
//
	// Context of application who uses us.
	private final Context context;
	
	private DatabaseHelper myDBHelper;
	private SQLiteDatabase db;

	/////////////////////////////////////////////////////////////////////
	//	Public methods:
	/////////////////////////////////////////////////////////////////////

	public void createUserTable(int user){
		DATABASE_TABLE = "chat_"+user;
		DATABASE_CREATE_SQL =
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
						+ KEY_SENDER + " integer not null, "
						+ KEY_MESSAGE+ " varchar not null, "
						+ KEY_TIMESTAMP + " varchar not null"

						// Rest  of creation:
						+ ");";
		db.execSQL(DATABASE_CREATE_SQL);
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
	public long insertRow(String table,int sMemId, String msg, String timestamp) {
		/*
		 * CHANGE 3:
		 */		
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_SENDER, sMemId);
		initialValues.put(KEY_MESSAGE, msg);
		initialValues.put(KEY_TIMESTAMP, timestamp+"");
		
		// Insert it into the database.
		return db.insert(table, null, initialValues);
	}
	
	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRow(String table,long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(table, where, null) != 0;
	}

	public void deleteAll(String table) {
		Cursor c = getAllRows(table);
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRow(table,c.getLong((int) rowId));
			} while (c.moveToNext());
		}
		c.close();
	}
	
	// Return all data in the database.
	public Cursor getAllRows(String table) {
		String where = null;
		Cursor c = 	db.query(true, table, ALL_KEYS,
				where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRow(String table,long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, table, ALL_KEYS,
						where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Change an existing row to be equal to new data.
	public boolean updateRow(String table,long rowId, int sMemId, String msg, String timestamp) {
		String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_SENDER, sMemId);
		newValues.put(KEY_MESSAGE, msg);
		newValues.put(KEY_TIMESTAMP, timestamp+"");

		// Insert it into the database.
		return db.update(table, newValues, where, null) != 0;
	}

	// DB Fields
//	public static final String KEY_ROWID = "_id";
//	public static final int COL_ROWID = 0;
	/*
	 * CHANGE 1:
	 */
	// TODO: Setup your fields here:
	public static final String KEY_USER1= "user1";
	public static final String KEY_NAME= "name";
	public static final String KEY_UNREAD= "unRead";

	// TODO: Setup your field numbers here (0 = KEY_ROWID, 1=...)
	public static final int COL_USER1 = 1;
	public static final int COL_NAME = 2;
	public static final int COL_UNREAD = 3;

	public static final String[] ALL_KEYS_CONN = new String[] {KEY_ROWID, KEY_USER1, KEY_NAME, KEY_UNREAD};

	// DB info: it's name, and the table we are using (just one).
	public static String DATABASE_TABLE_CONN = "connections";

	private static String DATABASE_CREATE_SQL_CONN =
				"create table " + DATABASE_TABLE_CONN
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
			+ KEY_USER1+ " integer not null, "
			+ KEY_NAME+ " string not null, "
			+ KEY_UNREAD+ " integer not null DEFAULT 0, "
			// Rest  of creation:
			+"CONSTRAINT name_unique UNIQUE ("+KEY_USER1+")"
			+ ");";

	public DBAdapter(Context ctx) {
			this.context = ctx;
			myDBHelper = new DatabaseHelper(context);
		}

	// Add a new set of values to the database.
	public long insertRowConn(int user1,String name,int num) {
		/*
		 * CHANGE 3:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_USER1, user1);
		initialValues.put(KEY_NAME, name);
		initialValues.put(KEY_UNREAD, num);

		// Insert it into the database.
		return db.insert(DATABASE_TABLE_CONN, null, initialValues);
	}

	// Delete a row from the database, by rowId (primary key)
	public boolean deleteRowConn(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		return db.delete(DATABASE_TABLE_CONN, where, null) != 0;
	}

	public boolean searchRowConn(int memId) {
//		String where = KEY_USER1 + "=" + memId;
//		Cursor c = db.query(true, DATABASE_TABLE_CONN, ALL_KEYS_CONN,
//				where, null, null, null, null, null);
//
		String query="SELECT * FROM "+ DATABASE_TABLE_CONN +" WHERE "+KEY_USER1+"=" + memId;
		Log.i("query", query);
		Cursor c = db.rawQuery(query, null);
		if(c.getCount() != 0){
			c.close();
			return true;
		}
		else{
			c.close();
			return false;
		}
//		return db.delete(DATABASE_TABLE_CONN, where, null) != 0;
	}

	public Cursor searchRowConn2(int memId) {
//		String where = KEY_USER1 + "=" + memId;
//		Cursor c = db.query(true, DATABASE_TABLE_CONN, ALL_KEYS_CONN,
//				where, null, null, null, null, null);
//
		String query="SELECT * FROM "+ DATABASE_TABLE_CONN +" WHERE "+KEY_USER1+"=" + memId;
		Log.i("query", query);
		Cursor c = db.rawQuery(query, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
//		return db.delete(DATABASE_TABLE_CONN, where, null) != 0;
	}


	public void deleteAllConn() {
		Cursor c = getAllRowsConn();
		long rowId = c.getColumnIndexOrThrow(KEY_ROWID);
		if (c.moveToFirst()) {
			do {
				deleteRowConn(c.getLong((int) rowId));
			} while (c.moveToNext());
		}
		c.close();
	}

	// Return all data in the database.
	public Cursor getAllRowsConn() {
		String where = null;
		Cursor c = 	db.query(true, DATABASE_TABLE_CONN, ALL_KEYS_CONN,
				where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Get a specific row (by rowId)
	public Cursor getRowConn(long rowId) {
		String where = KEY_ROWID + "=" + rowId;
		Cursor c = 	db.query(true, DATABASE_TABLE_CONN, ALL_KEYS_CONN,
				where, null, null, null, null, null);
		if (c != null) {
			c.moveToFirst();
		}
		return c;
	}

	// Change an existing row to be equal to new data.
	public boolean updateRowConn(long rowId, int num) {
		String where = KEY_ROWID + "=" + rowId;

		/*
		 * CHANGE 4:
		 */
		// TODO: Update data in the row with new fields.
		// TODO: Also change the function's arguments to be what you need!
		// Create row's data:
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_UNREAD, num);

		// Insert it into the database.
		return db.update(DATABASE_TABLE_CONN, newValues, where, null) != 0;
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
		Context context;
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			this.context = context;
		}

		@Override
		public void onCreate(SQLiteDatabase _db) {
//			_db.execSQL(DATABASE_CREATE_SQL);
			_db.execSQL(DATABASE_CREATE_SQL_CONN);
		}


		@Override
		public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading application's database from version " + oldVersion
					+ " to " + newVersion + ", which will destroy all old data!");
			
			// Destroy old database:
//			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);

			Cursor c = new DBAdapter(context).getAllRowsConn();
			if (c.moveToFirst()) {
				do {
						_db.execSQL("DROP TABLE IF EXISTS chat_" + c.getInt(COL_USER1));
					} while (c.moveToNext());
			}
			c.close();

			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_CONN);

			// Recreate new database:
			onCreate(_db);
		}



	}
}

