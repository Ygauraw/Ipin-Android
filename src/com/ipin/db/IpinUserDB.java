package com.ipin.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class IpinUserDB extends SQLiteOpenHelper {

	private final static String USER_DATABASENAME = "ipin_db";
	private final static int USER_DATABSEVERSION = 1;
	private final static String USER_DATABASENAME_USERTABLE = "user";
//	private final static String[] TABLELIST =  {};
//	private final static String[][] TABLE_COLOMN = {};

	public IpinUserDB(Context context) {
		super(context, USER_DATABASENAME, null, USER_DATABSEVERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE "
				+ USER_DATABASENAME_USERTABLE
				+ " (userId INTEGER primary key, userAccount text, userPassword text";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String sql = "DROP TABLE IF EXISTS " + USER_DATABASENAME_USERTABLE;
		db.execSQL(sql);
		onCreate(db);
	}

	public Cursor select() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(USER_DATABASENAME_USERTABLE, null, null, null,
				null, null, null);
		return cursor;
	}

	public long insert(int userId, String userAccount, String userPassword) {
//		SQLiteDatabase db = this.getWritableDatabase();
//		ContentValues cv = new ContentValues();
//		cv.put("userId", userId);
//		cv.put("userAccount", userAccount);
//		cv.put("userPassword", userPassword);
//		long row = db.insert(USER_DATABASENAME_USERTABLE, null, cv);
//		return row;
		return 0;
	}

	public void delete(String userAccount) {
		SQLiteDatabase db = this.getWritableDatabase();
		String target = "userAccount = ? ";
		String[] targetValues = {userAccount};
		db.delete(USER_DATABASENAME_USERTABLE, target, targetValues);
	}

	public void update(int userId, String userAccount, String userPassword) {
		SQLiteDatabase db = this.getWritableDatabase();
		String target = "userAccount = ? ";
		String[] targetValues = {userAccount};
		ContentValues cv = new ContentValues();
		cv.put("userId", userId);
		cv.put("userAccount", userAccount);
		cv.put("userPassword", userPassword);
		db.update(USER_DATABASENAME_USERTABLE, cv, target, targetValues);
	}
}
