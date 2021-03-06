package com.example.kirill.p8;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NoteFullQueryAdapter extends CursorAdapter {

	private LayoutInflater mInflater;


	public NoteFullQueryAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
		mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public void bindView(View view, Context ctx, Cursor cur) {
		long id = cur.getLong(cur.getColumnIndex(ContractClass.NotesFullQuery._ID));
		Integer noteType_ID = cur.getInt(cur.getColumnIndex(ContractClass.NotesFullQuery.COLUMN_TYPE_ID));
		Integer noteTypeInfo_ID = cur.getInt(cur.getColumnIndex(ContractClass.NotesFullQuery.COLUMN_TYPEINFO_ID));
		Double noteQuantity1=cur.getDouble(cur.getColumnIndex(ContractClass.NotesFullQuery.COLUMN_QUANTITY1));
		Double noteQuantity2=cur.getDouble(cur.getColumnIndex(ContractClass.NotesFullQuery.COLUMN_QUANTITY2));
		String noteNote=cur.getString(cur.getColumnIndex(ContractClass.NotesFullQuery.COLUMN_NOTE));
		String noteNoteName=cur.getString(cur.getColumnIndex(ContractClass.NotesFullQuery.COLUMN_TYPE_NAME));
		String noteNoteGOST=cur.getString(cur.getColumnIndex(ContractClass.NotesFullQuery.COLUMN_TYPE_GOST));
		String noteNoteNumSort=cur.getString(cur.getColumnIndex(ContractClass.NotesFullQuery.COLUMN_TYPEINFO_NUMSORT));
		ViewNote vNote = (ViewNote) view.getTag();
		if(vNote != null) {
			vNote.tvNotesType_ID.setText(String.valueOf(noteType_ID));
			vNote.tvNotesTypeInfo_ID.setText(String.valueOf(noteTypeInfo_ID));
			vNote.tvNotesQuantity1.setText(String.valueOf(noteQuantity1));
			vNote.tvNotesQuantity2.setText(String.valueOf(noteQuantity2));
			vNote.tvNotesNote.setText(noteNote);
			vNote.tvNotesName.setText(noteNoteName);
			vNote.tvNotesGOST.setText(noteNoteGOST);
			vNote.tvNoteNumSort.setText(noteNoteNumSort);
			vNote.NoteID = id;
		}
	}

	@Override
	public View newView(Context ctx, Cursor cur, ViewGroup parent) {
		View root = mInflater.inflate(R.layout.notes, parent, false);
		ViewNote vNote = new ViewNote();
		TextView tvNotesType_ID = (TextView) root.findViewById(R.id.tvNotesType_ID);
		TextView tvNotesTypeInfo_ID = (TextView) root.findViewById(R.id.tvNotesTypeInfo_ID);
		TextView tvNotesQuantity1 = (TextView) root.findViewById(R.id.tvNotesQuantity1);
		TextView tvNotesQuantity2 = (TextView) root.findViewById(R.id.tvNotesQuantity2);
		TextView tvNotesNote = (TextView) root.findViewById(R.id.tvNotesNote);
		TextView tvNotesName = (TextView) root.findViewById(R.id.tvNotesTypeName);
		TextView tvNotesGOST = (TextView) root.findViewById(R.id.tvNotesTypeGOST);
		TextView tvNoteNumSort = (TextView) root.findViewById(R.id.tvNotesTypeInfoNumSort);
		vNote.tvNotesType_ID = tvNotesType_ID;
		vNote.tvNotesTypeInfo_ID = tvNotesTypeInfo_ID;
		vNote.tvNotesQuantity1 = tvNotesQuantity1;
		vNote.tvNotesQuantity2 = tvNotesQuantity2;
		vNote.tvNotesNote = tvNotesNote;
		vNote.tvNotesName = tvNotesName;
		vNote.tvNotesGOST = tvNotesGOST;
		vNote.tvNoteNumSort = tvNoteNumSort;
		root.setTag(vNote);
		return root;
	}



	public static class ViewNote {
		public TextView tvNotesType_ID;
		public TextView tvNotesTypeInfo_ID;
		public TextView tvNotesQuantity1;
		public TextView tvNotesQuantity2;
		public TextView tvNotesNote;
		public TextView tvNotesName;
		public TextView tvNotesGOST;
		public TextView tvNoteNumSort;
		public long NoteID;
	}

}
