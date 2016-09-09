package com.example.kirill.p8;

import java.util.HashMap;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

public class ExampleProvider extends ContentProvider {
	private static final int DATABASE_VERSION = 1;
	private static HashMap<String, String> sTypeInfoProjectionMap;
	private static HashMap<String, String> sTypesProjectionMap;
	private static final int TYPEINFO = 1;
	private static final int TYPEINFO_ID = 2;
	private static final int TYPES = 3;
	private static final int TYPES_ID = 4;
	private static final UriMatcher sUriMatcher;
	private DatabaseHelper dbHelper;
	static {
		sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		sUriMatcher.addURI(ContractClass.AUTHORITY, "typeinfo", TYPEINFO);
		sUriMatcher.addURI(ContractClass.AUTHORITY, "typeinfo/#", TYPEINFO_ID);
		sUriMatcher.addURI(ContractClass.AUTHORITY, "types", TYPES);
		sUriMatcher.addURI(ContractClass.AUTHORITY, "types/#", TYPES_ID);
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
	}
	private static class DatabaseHelper extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "ContractClassDB";
		public static final String DATABASE_TABLE_TYPEINFO = ContractClass.TypeInfo.TABLE_NAME;
		public static final String DATABASE_TABLE_TYPES = ContractClass.Types.TABLE_NAME;
		public static final String KEY_ROWID  = "_id";
		public static final String KEY_NUMSORT   = ContractClass.TypeInfo.COLUMN_NAME_NUMSORT;
		public static final String KEY_ITEM_NAME   = ContractClass.TypeInfo.COLUMN_ITEM_NAME;
		public static final String KEY_MASS_PER_ITEM   = ContractClass.TypeInfo.COLUMN_NAME_MASS_PER_ITEM;
		public static final String KEY_TYPE_ID   = ContractClass.TypeInfo.COLUMN_NAME_TYPE_ID;
		public static final String KEY_NAME   = ContractClass.Types.COLUMN_NAME_NAME;
		public static final String KEY_GOST   = ContractClass.Types.COLUMN_NAME_GOST;
		private static final String DATABASE_CREATE_TABLE_TYPEINFO =
			"create table "+ DATABASE_TABLE_TYPEINFO + " ("
				+ KEY_ROWID + " integer primary key autoincrement, "
				+ KEY_NUMSORT + " string , "
				+ KEY_ITEM_NAME + " string , "
				+ KEY_MASS_PER_ITEM + " real , "
				+ KEY_TYPE_ID + " integer, "
				+" foreign key ("+KEY_TYPE_ID+") references "+DATABASE_TABLE_TYPES+"("+KEY_ROWID+"));";
				
		private static final String DATABASE_CREATE_TABLE_TYPES =
			"create table "+ DATABASE_TABLE_TYPES + " ("
				+ KEY_ROWID + " integer primary key autoincrement, "
				+ KEY_NAME + " string , "
				+ KEY_GOST + " string );";
				
		private Context ctx;
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			ctx = context;
		}
		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE_TABLE_TYPEINFO);
			db.execSQL(DATABASE_CREATE_TABLE_TYPES);
			db.execSQL("insert into types values (null, 'ugolok', 'A');");
			db.execSQL("insert into types values (null, 'shveller', 'B');");
			db.execSQL("insert into types values (null, 'truba', 'C');");
			db.execSQL("insert into types values (null, 'dvutavr', 'A');");
			db.execSQL("insert into types values (null, 'list', 'B');");

			db.execSQL("insert into typeinfo values (null, '45', 'm', 4.1, 0);");
			db.execSQL("insert into typeinfo values (null, '50', 'm', 3.5, 0);");
			db.execSQL("insert into typeinfo values (null, '60', 'm', 4.9, 0);");
			
			db.execSQL("insert into typeinfo values (null, '12', 'm', 4.1, 1);");
			db.execSQL("insert into typeinfo values (null, '14', 'm', 3.5, 1);");
			db.execSQL("insert into typeinfo values (null, '16', 'm', 4.9, 1);");
			
			db.execSQL("insert into typeinfo values (null, '50', 'm', 4.1, 2);");
			db.execSQL("insert into typeinfo values (null, '63', 'm', 3.5, 2);");
			db.execSQL("insert into typeinfo values (null, '76', 'm', 4.9, 2);");
			
			db.execSQL("insert into typeinfo values (null, '16', 'm', 4.1, 3);");
			db.execSQL("insert into typeinfo values (null, '18', 'm', 3.5, 3);");
			db.execSQL("insert into typeinfo values (null, '20', 'm', 4.9, 3);");
			
			db.execSQL("insert into typeinfo values (null, '4', 'm2', 4.1, 4);");
			db.execSQL("insert into typeinfo values (null, '5', 'm2', 3.5, 4);");
			db.execSQL("insert into typeinfo values (null, '6', 'm2', 4.9, 4);");

		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_TABLE_TYPEINFO);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_CREATE_TABLE_TYPES);
			onCreate(db);
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
			sUriMatcher.match(uri) != TYPES
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
		}
		return rowUri;
	}
	@Override
	public boolean onCreate() {
		dbHelper = new DatabaseHelper(getContext());
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
			default:
				throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}
