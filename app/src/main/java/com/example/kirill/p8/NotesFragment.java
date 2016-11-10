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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


public class NotesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private ListView lvNotes;
    private NoteFullQueryAdapter mAdapter;
    MainActivity mainAct;

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

        mAdapter = new NoteFullQueryAdapter(getContext(), null, 0);
        View v = inflater.inflate(R.layout.notes_listview, container, false);
        if (getActivity().getSupportLoaderManager().getLoader(0)!=null){
            getActivity().getSupportLoaderManager().restartLoader(0, null, this);
        }else
        {
            getActivity().getSupportLoaderManager().initLoader(0, null, this);
        }
        ListView lvNotes=(ListView) v.findViewById(R.id.lvNotes);
        lvNotes.setAdapter(mAdapter);
        lvNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoteFullQueryAdapter.ViewNote vhldr=(NoteFullQueryAdapter.ViewNote) view.getTag();
                listener.NoteItemClick(position,id,vhldr.NoteID);
            }
        });


        //try to show tooblar button
        //MenuItem x = (MenuItem) getActivity().findViewById(R.id.miDelete);
        //Menu y = (Menu) getActivity().findViewById();
       // MenuItem x = (MenuItem) getActivity().findViewById(R.id.miDelete);
       // x.setVisible(true);
        return v;

    }

//    @Override
//    public void onListItemClick(ListView l, View v, int position, long id) {
//        super.onListItemClick(l, v, position, id);
//        NoteAdapter.ViewNote vnt= (NoteAdapter.ViewNote) v.getTag();
//        listener.NoteItemClick(position, id,vnt.NoteID);
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (onItemClickListener) context;
        mainAct = (MainActivity) context;
        mainAct.menuDelete.setVisible(true);
    }

    @Override
    public void onDetach() {
        mainAct.menuDelete.setVisible(false);
        super.onDetach();
    }
    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle arg1) {
        return new CursorLoader(
                getContext(),
                ContractClass.NotesFullQuery.CONTENT_URI,
                ContractClass.NotesFullQuery.DEFAULT_PROJECTION,
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

