package com.bikeonet.android.periodtracker.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bikeonet.android.periodtracker.entity.PeriodEntity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

public class DataHelper {

	public static final SimpleDateFormat format = new SimpleDateFormat(
			"yyyy-MM-dd");
	
	private static final String DATABASE_NAME = "Period.db";
	private static final String TABLE_NAME = "periodlog";
	private static final String CREATE_TABLE_SQL = "create table " + TABLE_NAME
			+ " (" + "id integer primary key autoincrement,"
			+ "ts_date varchar(10) not null," 
			+ "is_period integer,"
			+ "took_pill integer," + "strength integer,"
			+ "notes varchar(250));";
	private static final String CREATE_INDEX_SQL = "create unique index idx_periodlog_ts_date on periodlog ( ts_date );";

	private Context context;
	private SQLiteDatabase db;

	private SQLiteStatement insertStmt;
	private SQLiteStatement updateStmt;	
	
	private static final String INSERT = "insert into "
			+ TABLE_NAME
			+ "(ts_date, is_period, took_pill, strength, notes) values (?, ?, ?, ?, ?)";

	private static final String UPDATE = "update " + TABLE_NAME + 
										 " SET took_pill=?, strength=?, notes=?" +
										 " WHERE id=?";
	
	public DataHelper(Context context) {
		this.context = context;
		OpenHelper openHelper = new OpenHelper(this.context);
		this.db = openHelper.getWritableDatabase();
		this.insertStmt = this.db.compileStatement(INSERT);
		this.updateStmt = this.db.compileStatement(UPDATE);
	}

	public long insert(Date tsDate, boolean isPeriod)
			throws SQLiteConstraintException {

		this.insertStmt.bindString(1, format.format(tsDate));
		this.insertStmt.bindLong(2, isPeriod ? 1 : 0);
		this.insertStmt.bindNull(3); // took_pill
		this.insertStmt.bindNull(4); // strength
		this.insertStmt.bindNull(5); // notes

		return this.insertStmt.executeInsert();
	}
	
	public void update(long id, boolean took_pill, float strength, String notes) { 
		this.updateStmt.bindLong(1, took_pill?1:0);
		this.updateStmt.bindLong(2, new Float(strength).longValue());
		this.updateStmt.bindString(3, notes);
		this.updateStmt.bindLong(4, id);
		
		this.updateStmt.execute();
	}

	public void deleteAll() {
		this.db.delete(TABLE_NAME, null, null);
	}

	public void delete(Date tsDate) {
		this.db.delete(TABLE_NAME, "ts_date=" + tsDate.getTime(), null);
	}

	public List<PeriodEntity> selectAll(boolean showAll) {
		List<PeriodEntity> list = new ArrayList<PeriodEntity>();
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id",
				"ts_date", "is_period", "took_pill", "strength", "notes" },
				showAll ? "" : "is_period=1", null, null, null, "ts_date DESC");
		if (cursor.moveToFirst()) {
			do {
				PeriodEntity pe = new PeriodEntity();
				pe.setId(cursor.getLong(0));
				try {
					pe.setTs_date(format.parse(cursor.getString(1)));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					pe.setTs_date(null);
				}
				pe.setIs_period(cursor.getInt(2) == 1 ? true : false);
				list.add(pe);
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	public boolean isPeriodDay(Date tsDate) {
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id",
				"ts_date", "is_period", "took_pill", "strength", "notes" },
				"ts_date=?", new String[] { format.format(tsDate) }, null,
				null, null);
		boolean b = cursor.moveToFirst();
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return b;
	}

	public void closeDb() {
		this.db.close();
	}

	private static class OpenHelper extends SQLiteOpenHelper {

		OpenHelper(Context context) {
			super(context, DATABASE_NAME, null, 1);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(CREATE_TABLE_SQL);
			db.execSQL(CREATE_INDEX_SQL);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
		}
	}

	public PeriodEntity selectById(Long id) {
		PeriodEntity pe = new PeriodEntity();
		
		Cursor cursor = this.db.query(TABLE_NAME, new String[] { "id",
				"ts_date", "is_period", "took_pill", "strength", "notes" },
				"id=?", new String[] { id.toString() }, null, null, null);
		
		if (cursor.moveToFirst()) {
			pe.setId(cursor.getLong(0));
			try {
				pe.setTs_date(format.parse(cursor.getString(1)));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				pe.setTs_date(null);
			}
			pe.setIs_period(cursor.getInt(2) == 1 ? true : false);
			pe.setTook_pill(cursor.getInt(3) == 1 ? true : false);
			pe.setStrength(cursor.getInt(4));
			pe.setNotes(cursor.getString(5));
		}
		else { 
			pe = null;
		}

		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		
		return pe;
	}

}
