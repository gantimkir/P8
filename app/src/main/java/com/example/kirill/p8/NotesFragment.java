package com.example.kirill.p8;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class NotesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView lvNotes;
    private NoteAdapter mAdapter;

    public interface onItemClickListener {
        public void NoteItemClick(int position, long id, long noteID);
    }
    onItemClickListener listener;

    public static NotesFragment newInstance() {
        NotesFragment frmtNotes = new NotesFragment();
        return frmtNotes;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mAdapter = new NoteAdapter(getContext(), null, 0);
//        setListAdapter(mAdapter);
//        getActivity().getSupportLoaderManager().initLoader(0, null, this);

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mAdapter = new NoteAdapter(getContext(), null, 0);
        View v = inflater.inflate(R.layout.notes_listview, container, false);
        if (getActivity().getSupportLoaderManager().getLoader(0)!=null){
            getActivity().getSupportLoaderManager().restartLoader(0, null, this);
        }else
        {
            getActivity().getSupportLoaderManager().initLoader(0, null, this);
        }
        ListView lvTypeInfo=(ListView) v.findViewById(R.id.lvNotes);
        lvTypeInfo.setAdapter(mAdapter);
        lvTypeInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TypesInfoAdapter.ViewHolder vhldr=(TypesInfoAdapter.ViewHolder) view.getTag();
                listener.NoteItemClick(position,id,vhldr.classID);
            }
        });
        return v;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        NoteAdapter.ViewNote vnt= (NoteAdapter.ViewNote) v.getTag();
        listener.NoteItemClick(position, id,vnt.NoteID);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (onItemClickListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new CursorLoader(
                getContext(),
                ContractClass.Notes.CONTENT_URI,
                ContractClass.Notes.DEFAULT_PROJECTION,
                null,
                null,
                null);
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor newData) {
        mAdapter.swapCursor(newData);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}

