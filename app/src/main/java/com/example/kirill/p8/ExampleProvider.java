package com.example.kirill.p8;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class ExampleProvider extends ContentProvider {
	private static final int DATABASE_VERSION = 1;
	private static HashMap<String, String> sTypeInfoProjectionMap;
	private static HashMap<String, String> sTypesProjectionMap;
	private static HashMap<String, String> sNotesProjectionMap;

	private static final int TYPEINFO = 1;
	private static final int TYPEINFO_ID = 2;
	private static final int TYPES = 3;
	private static final int TYPES_ID = 4;
	private static final int NOTES = 5;
	private static final int NOTES_ID = 6;

	private static final UriMatcher sUriMatcher;
	private DatabaseHelper dbHelper;
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(ContractClass.AUTHORITY, "typeinfo", TYPEINFO);
		sUriMatcher.addURI(ContractClass.AUTHORITY, "typeinfo/#", TYPEINFO_ID);
		sUriMatcher.addURI(ContractClass.AUTHORITY, "types", TYPES);
		sUriMatcher.addURI(ContractClass.AUTHORITY, "types/#", TYPES_ID);
		sUriMatcher.addURI(ContractClass.AUTHORITY, "notes", NOTES);
		sUriMatcher.addURI(ContractClass.AUTHORITY, "notes/#", NOTES_ID);
		sTypeInfoProjectionMap = new HashMap<String, String>();
		for(int i=0; i < ContractClass.TypeInfo.DEFAULT_PROJECTION.length; i++) {
			sTypeInfoProjectionMap.put(
				ContractClass.TypeInfo.DEFAULT_PROJECTION[i],
				ContractClass.TypeInfo.DEFAULT_PROJECTION[i]);
		}
		sTypesProjectionMap = new HashMap<String, String>();
		for(int i=0; i < ContractClass.Types.DEFAULT_PROJECTION.length; i++) {
			sTypesProjectionMap.put(
				ContractClass.Types.DEFAULT_PROJECTION[i],
				ContractClass.Types.DEFAULT_PROJECTION[i]);
		}

		sNotesProjectionMap = new HashMap<String, String>();
		for(int i=0; i < ContractClass.Notes.DEFAULT_PROJECTION.length; i++) {
			sNotesProjectionMap.put(
					ContractClass.Notes.DEFAULT_PROJECTION[i],
					ContractClass.Notes.DEFAULT_PROJECTION[i]);
		}
	}
	private static class DatabaseHelper extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "sortament";
		public static final String DATABASE_TABLE_TYPEINFO = ContractClass.TypeInfo.TABLE_NAME;
		public static final String DATABASE_TABLE_TYPES = ContractClass.Types.TABLE_NAME;
		public static final String DATABASE_TABLE_NOTES = ContractClass.Notes.TABLE_NAME;
		public static final String KEY_ROWID  = "_id";
		//TypeInfo KEYS
		public static final String KEY_NUMSORT   = ContractClass.TypeInfo.COLUMN_NAME_NUMSORT;
		public static final String KEY_ITEM_NAME   = ContractClass.TypeInfo.COLUMN_ITEM_NAME;
		public static final String KEY_MASS_PER_ITEM   = ContractClass.TypeInfo.COLUMN_NAME_MASS_PER_ITEM;
		public static final String KEY_TYPE_ID   = ContractClass.TypeInfo.COLUMN_NAME_TYPE_ID;
		//Types KEYS
		public static final String KEY_NAME   = ContractClass.Types.COLUMN_NAME_NAME;
		public static final String KEY_GOST   = ContractClass.Types.COLUMN_NAME_GOST;
		//Notes KEYS
		public static final String KEY_TITLE   = ContractClass.Notes.COLUMN_TITLE;
		public static final String KEY_NOTE   = ContractClass.Notes.COLUMN_NOTE;
		public static final String CREATED_DATE   = ContractClass.Notes.COLUMN_CREATED_DATE;
		public static final String KEY_MODIFIED_DATE   = ContractClass.Notes.COLUMN_MODIFIED_DATE;

		private static String DB_PATH = "/data/data/com.example.kirill.p8/databases/";

		private Context ctx;
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			ctx = context;
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}

		public void create_db(){
			InputStream myInput = null;
			OutputStream myOutput = null;
			try {
				File file = new File(DB_PATH + DATABASE_NAME);
				if (!file.exists()) {
					this.getReadableDatabase();
					//получаем локальную бд как поток
					myInput = ctx.getAssets().open(DATABASE_NAME+".db");
					// Путь к новой бд
					String outFileName = DB_PATH + DATABASE_NAME;
					// Открываем пустую бд
					myOutput = new FileOutputStream(outFileName);
					// побайтово копируем данные
					byte[] buffer = new byte[1024];
					int length;
					while ((length = myInput.read(buffer)) > 0) {
						myOutput.write(buffer, 0, length);
					}
					myOutput.flush();
					myOutput.close();
					myInput.close();
				}
			}
			catch(IOException ex){
				String strEx1=ex.getMessage();
			}
		}

        public void open() throws SQLException {
//            String path = DB_PATH + DATABASE_NAME;
//            database = SQLiteDatabase.openDatabase(path, null,
//                    SQLiteDatabase.OPEN_READWRITE);

        }

        @Override
        public synchronized void close() {
//            if (database != null) {
//                database.close();
//            }
            super.close();
        }

	}
	@Override
	public int delete(Uri uri, String where, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		String finalWhere;
		int count;
		switch (sUriMatcher.match(uri)) {
			case TYPEINFO:
				count = db.delete(ContractClass.TypeInfo.TABLE_NAME,where,whereArgs);
				break;
			case TYPEINFO_ID:
				finalWhere = ContractClass.TypeInfo._ID + " = " + uri.getPathSegments().get(ContractClass.TypeInfo.TYPEINFO_ID_PATH_POSITION);
				if (where != null) {
					finalWhere = finalWhere + " AND " + where;
				}
				count = db.delete(ContractClass.TypeInfo.TABLE_NAME,finalWhere,whereArgs);
				break;
			case TYPES:
				count = db.delete(ContractClass.Types.TABLE_NAME,where,whereArgs);
				break;
			case TYPES_ID:
				finalWhere = ContractClass.Types._ID + " = " + uri.getPathSegments().get(ContractClass.Types.TYPES_ID_PATH_POSITION);
				if (where != null) {
					finalWhere = finalWhere + " AND " + where;
				}
				count = db.delete(ContractClass.Types.TABLE_NAME,finalWhere,whereArgs);
				break;
			case NOTES:
				count = db.delete(ContractClass.Notes.TABLE_NAME,where,whereArgs);
				break;
			case NOTES_ID:
				finalWhere = ContractClass.Notes._ID + " = " + uri.getPathSegments().get(ContractClass.Notes.NOTES_ID_PATH_POSITION);
				if (where != null) {
					finalWhere = finalWhere + " AND " + where;
				}
				count = db.delete(ContractClass.Notes.TABLE_NAME,finalWhere,whereArgs);
				break;
			default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
	@Override
	public String getType(Uri uri) {
		switch (sUriMatcher.match(uri)) {
			case TYPEINFO:
				return ContractClass.TypeInfo.CONTENT_TYPE;
			case TYPEINFO_ID:
				return ContractClass.TypeInfo.CONTENT_ITEM_TYPE;
			case TYPES:
				return ContractClass.Types.CONTENT_TYPE;
			case TYPES_ID:
				return ContractClass.Types.CONTENT_ITEM_TYPE;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues initialValues) {
		if (
			sUriMatcher.match(uri) != TYPEINFO &&
			sUriMatcher.match(uri) != TYPES &&
			sUriMatcher.match(uri) != NOTES
		) {
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		ContentValues values;
		if (initialValues != null) {
			values = new ContentValues(initialValues);
		}
		else {
			values = new ContentValues();
		}
		long rowId = -1;
		Uri rowUri = Uri.EMPTY;
		switch (sUriMatcher.match(uri)) {
			case TYPEINFO:
				if (values.containsKey(ContractClass.TypeInfo.COLUMN_NAME_NUMSORT) == false) {
					values.put(ContractClass.TypeInfo.COLUMN_NAME_NUMSORT, "");
				}
				if (values.containsKey(ContractClass.TypeInfo.COLUMN_ITEM_NAME) == false) {
					values.put(ContractClass.TypeInfo.COLUMN_ITEM_NAME, "");
				}
				if (values.containsKey(ContractClass.TypeInfo.COLUMN_NAME_MASS_PER_ITEM) == false) {
					values.put(ContractClass.TypeInfo.COLUMN_NAME_MASS_PER_ITEM, 0.0);
				}
				if (values.containsKey(ContractClass.TypeInfo.COLUMN_NAME_TYPE_ID) == false) {
					values.put(ContractClass.TypeInfo.COLUMN_NAME_TYPE_ID, 0);
				}
			rowId = db.insert(ContractClass.TypeInfo.TABLE_NAME,
				ContractClass.TypeInfo.COLUMN_NAME_NUMSORT,
				values);
			if (rowId > 0) {
				rowUri = ContentUris.withAppendedId(ContractClass.TypeInfo.CONTENT_ID_URI_BASE, rowId);
				getContext().getContentResolver().notifyChange(rowUri, null);
			}
			break;
			case TYPES:
				if (values.containsKey(ContractClass.Types.COLUMN_NAME_NAME) == false) {
					values.put(ContractClass.Types.COLUMN_NAME_NAME, "");
				}
				if (values.containsKey(ContractClass.Types.COLUMN_NAME_GOST) == false) {
					values.put(ContractClass.Types.COLUMN_NAME_GOST, "");
				}
			rowId = db.insert(ContractClass.Types.TABLE_NAME,
				ContractClass.Types.COLUMN_NAME_NAME,
				values);
			if (rowId > 0) {
				rowUri = ContentUris.withAppendedId(ContractClass.Types.CONTENT_ID_URI_BASE, rowId);
				getContext().getContentResolver().notifyChange(rowUri, null);
			}
			break;
			case NOTES:
				if (values.containsKey(ContractClass.Notes.COLUMN_TITLE) == false) {
					values.put(ContractClass.Notes.COLUMN_TITLE, "");
				}
				if (values.containsKey(ContractClass.Notes.COLUMN_NOTE) == false) {
					values.put(ContractClass.Notes.COLUMN_NOTE, "");
				}
				if (values.containsKey(ContractClass.Notes.COLUMN_CREATED_DATE) == false) {
					values.put(ContractClass.Notes.COLUMN_CREATED_DATE, "");
					if (values.containsKey(ContractClass.Notes.COLUMN_MODIFIED_DATE) == false) {
						values.put(ContractClass.Notes.COLUMN_MODIFIED_DATE, "");
				}
				rowId = db.insert(ContractClass.Notes.TABLE_NAME,
						ContractClass.Notes.COLUMN_TITLE,
						values);
				if (rowId > 0) {
					rowUri = ContentUris.withAppendedId(ContractClass.Notes.CONTENT_ID_URI_BASE, rowId);
					getContext().getContentResolver().notifyChange(rowUri, null);
				}
				break;
			}
		}
		return rowUri;
	}

	@Override
	public boolean onCreate() {
		dbHelper = new DatabaseHelper(getContext());
        dbHelper.create_db();
		return true;
	}
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		String orderBy = null;
		switch (sUriMatcher.match(uri)) {
			case TYPEINFO:
				qb.setTables(ContractClass.TypeInfo.TABLE_NAME);
				qb.setProjectionMap(sTypeInfoProjectionMap);
				orderBy = ContractClass.TypeInfo.DEFAULT_SORT_ORDER;
				break;
			case TYPEINFO_ID:
				qb.setTables(ContractClass.TypeInfo.TABLE_NAME);
				qb.setProjectionMap(sTypeInfoProjectionMap);
				qb.appendWhere(ContractClass.TypeInfo._ID + "=" + uri.getPathSegments().get(ContractClass.TypeInfo.TYPEINFO_ID_PATH_POSITION));
				orderBy = ContractClass.TypeInfo.DEFAULT_SORT_ORDER;
				break;
			case TYPES:
				qb.setTables(ContractClass.Types.TABLE_NAME);
				qb.setProjectionMap(sTypesProjectionMap);
				orderBy = ContractClass.Types.DEFAULT_SORT_ORDER;
				break;
			case TYPES_ID:
				qb.setTables(ContractClass.Types.TABLE_NAME);
				qb.setProjectionMap(sTypeInfoProjectionMap);
				qb.appendWhere(ContractClass.Types._ID + "=" + uri.getPathSegments().get(ContractClass.Types.TYPES_ID_PATH_POSITION));
				orderBy = ContractClass.Types.DEFAULT_SORT_ORDER;
				break;
			case NOTES:
				qb.setTables(ContractClass.Notes.TABLE_NAME);
				qb.setProjectionMap(sNotesProjectionMap);
				orderBy = ContractClass.Notes.DEFAULT_SORT_ORDER;
				break;
			case NOTES_ID:
				qb.setTables(ContractClass.Notes.TABLE_NAME);
				qb.setProjectionMap(sNotesProjectionMap);
				qb.appendWhere(ContractClass.Notes._ID + "=" + uri.getPathSegments().get(ContractClass.Notes.NOTES_ID_PATH_POSITION));
				orderBy = ContractClass.Notes.DEFAULT_SORT_ORDER;
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		SQLiteDatabase db = dbHelper.getReadableDatabase();
		Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}
	@Override
	public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int count;
		String finalWhere;
		String id;
		switch (sUriMatcher.match(uri)) {
			case TYPEINFO:
				count = db.update(ContractClass.TypeInfo.TABLE_NAME, values, where, whereArgs);
				break;
			case TYPEINFO_ID:
				id = uri.getPathSegments().get(ContractClass.TypeInfo.TYPEINFO_ID_PATH_POSITION);
				finalWhere = ContractClass.TypeInfo._ID + " = " + id;
				if (where !=null) {
					finalWhere = finalWhere + " AND " + where;
				}
				count = db.update(ContractClass.TypeInfo.TABLE_NAME, values, finalWhere, whereArgs);
				break;
			case TYPES:
				count = db.update(ContractClass.Types.TABLE_NAME, values, where, whereArgs);
				break;
			case TYPES_ID:
				id = uri.getPathSegments().get(ContractClass.Types.TYPES_ID_PATH_POSITION);
				finalWhere = ContractClass.Types._ID + " = " + id;
				if (where !=null) {
					finalWhere = finalWhere + " AND " + where;
				}
				count = db.update(ContractClass.Types.TABLE_NAME, values, finalWhere, whereArgs);
				break;
			case NOTES:
				count = db.update(ContractClass.Notes.TABLE_NAME, values, where, whereArgs);
				break;
			case NOTES_ID:
				id = uri.getPathSegments().get(ContractClass.Notes.NOTES_ID_PATH_POSITION);
				finalWhere = ContractClass.Notes._ID + " = " + id;
				if (where !=null) {
					finalWhere = finalWhere + " AND " + where;
				}
				count = db.update(ContractClass.Types.TABLE_NAME, values, finalWhere, whereArgs);
				break;
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}
