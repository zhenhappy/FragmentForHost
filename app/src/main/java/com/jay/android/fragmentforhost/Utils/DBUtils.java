package com.jay.android.fragmentforhost.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBUtils extends SQLiteOpenHelper {
	private final static String DATABASE_NAME = "RECORD.db";
	private final static int DATABASE_VERSION = 1;
	private final static String TABLE_NAME = "record_table";
	public final static String RECORD_ID = "record_id";
	public final static String TYPE = "type";//类型（1床体 2大小便 3上肢 4下肢）
	public final static String OPERATION = "operation";//操作
	public final static String OPERATOR = "operator";//操作者
	public final static String TIME = "time";//时间

	public DBUtils(Context context) {
		// TODO Auto-generated constructor stub
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// 创建table
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + TABLE_NAME + " (" + RECORD_ID
				+ " INTEGER primary key autoincrement, , " + TYPE + " text" + OPERATION
				+ " text, " + OPERATOR + " text, " + TIME + " text);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}
//查找
	public Cursor select(String[] type) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db
				.query(TABLE_NAME, null, "TYPE=?", type, null, null, null);
		return cursor;
	}

	// 增加操作
	public long insert(String type, String operation, String operator, String time) {
		SQLiteDatabase db = this.getWritableDatabase();
		/* ContentValues */
		ContentValues cv = new ContentValues();
		cv.put(TYPE, type);
		cv.put(OPERATION, operation);
		cv.put(OPERATOR, operator);
		cv.put(OPERATOR, time);
		long row = db.insert(TABLE_NAME, null, cv);
		return row;
	}

	// 删除操作
	public void delete(int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = RECORD_ID + " = ?";
		String[] whereValue = { Integer.toString(id) };
		db.delete(TABLE_NAME, where, whereValue);
	}

	// 修改操作
	public void update(int id, String bookname, String author) {
		SQLiteDatabase db = this.getWritableDatabase();
		String where = RECORD_ID + " = ?";
		String[] whereValue = { Integer.toString(id) };

		ContentValues cv = new ContentValues();
		cv.put(OPERATION, bookname);
		cv.put(OPERATOR, author);
		db.update(TABLE_NAME, cv, where, whereValue);
	}
}