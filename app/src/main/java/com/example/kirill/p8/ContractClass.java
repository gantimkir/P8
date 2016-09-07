package com.example.kirill.p8;

import android.net.Uri;
import android.provider.BaseColumns;

public final class ContractClass {
	public static final String AUTHORITY = "com.example.kirill.p8.provider.Sortament";
	private ContractClass() {}
	public static final class TypeInfo implements BaseColumns {
		private TypeInfo() {}
		public static final String TABLE_NAME ="typeinfo";
		private static final String SCHEME = "content://";
		private static final String PATH_TYPEINFO = "/typeinfo";
		private static final String PATH_TYPEINFO_ID = "/typeinfo/";
		public static final int STUDENTS_ID_PATH_POSITION = 1;
		public static final Uri CONTENT_URI =  Uri.parse(SCHEME + AUTHORITY + PATH_TYPEINFO);
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_TYPEINFO_ID);
		//public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + AUTHORITY + PATH_STUDENTS_ID + "/#");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.example.kirill.typeinfo";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.example.kirill.typeinfo";
		public static final String DEFAULT_SORT_ORDER = "numsort ASC";
		public static final String COLUMN_NAME_NUMSORT   = "numsort";
		public static final String COLUMN_NAME_ITEM_NAME   = "item_name";
		public static final String COLUMN_NAME_MASS_PER_ITEM   = "mass_per_item";
		public static final String COLUMN_NAME_TYPE_ID   = "type_id";
		public static final String[] DEFAULT_PROJECTION = new String[] {
			ContractClass.TypeInfo._ID,
			ContractClass.TypeInfo.COLUMN_NAME_NUMSORT,
			ContractClass.TypeInfo.COLUMN_NAME_ITEM_NAME,
			ContractClass.TypeInfo.COLUMN_NAME_MASS_PER_ITEM,
			ContractClass.TypeInfo.COLUMN_NAME_TYPE_ID
		};
	}
	public static final class Types implements BaseColumns {
		private Types() {}
		public static final String TABLE_NAME ="types";
		private static final String SCHEME = "content://";
		private static final String PATH_CLASSES = "/types";
		private static final String PATH_CLASSES_ID = "/types/";
		public static final int CLASSES_ID_PATH_POSITION = 1;
		public static final Uri CONTENT_URI =  Uri.parse(SCHEME + AUTHORITY + PATH_CLASSES);
		public static final Uri CONTENT_ID_URI_BASE = Uri.parse(SCHEME + AUTHORITY + PATH_CLASSES_ID);
		//public static final Uri CONTENT_ID_URI_PATTERN = Uri.parse(SCHEME + AUTHORITY + PATH_CLASSES_ID + "/#");
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.example.kirill.types";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.example.kirill.types";
		public static final String DEFAULT_SORT_ORDER = "name ASC";
		public static final String COLUMN_NAME_NAME   = "name";
		public static final String COLUMN_NAME_GOST   = "gost";
		public static final String[] DEFAULT_PROJECTION = new String[] {
			ContractClass.Types._ID,
			ContractClass.Types.COLUMN_NAME_NAME,
			ContractClass.Types.COLUMN_NAME_GOST
		};
	}
}

