package com.example.kirill.p8;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by _ on 15.09.2016.
 */
public class TypesInfoFragment extends Fragment{


    public interface onTypesInfoItemClickListener {
        public void onTypesInfoItemClick(int position, long id);
    }
    onTypesInfoItemClickListener listener;

    public static TypesInfoFragment newInstance(String[] typeInfo) {
        TypesInfoFragment ti = new TypesInfoFragment();
        Bundle args = new Bundle();
        args.putStringArray("typeinfo", typeInfo);
        ti.setArguments(args);
        return ti;
    }

    String[] getTypeInfo() {
        return getArguments().getStringArray("typeinfo");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.type_text_frag, container, false);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_list_item_1);
        if(getTypeInfo() != null && getTypeInfo().length > 0) {
            arrayAdapter.addAll(getTypeInfo());
        }
        else {
            arrayAdapter.add("No typeinfo");
        }
        ListView lvTypeInfo=(ListView) v.findViewById(R.id.lvName1);
        lvTypeInfo.setAdapter(arrayAdapter);
        lvTypeInfo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listener.onTypesInfoItemClick(position,id);
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (onTypesInfoItemClickListener) context;
    }
}
