package com.example.kirill.p8;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class EnterInfoFragment extends DialogFragment {

    public interface onEnterInfoItemClickListener {
        public void onEnterInfoItemClick(int position, long id);
    }
    onEnterInfoItemClickListener listener;

    public static EnterInfoFragment newInstance(String[] typeinfoEnter) {
        EnterInfoFragment frmtEnterInfo = new EnterInfoFragment();
        Bundle args = new Bundle();
        args.putString("numsort", typeinfoEnter[0]);
        args.putString("item_name", typeinfoEnter[1]);
        args.putInt("type_id", Integer.valueOf(typeinfoEnter[2]));
        args.putInt("typeinfo_id", Integer.valueOf(typeinfoEnter[3]));
        args.putDouble("mass_per_item", Double.valueOf(typeinfoEnter[4]));
        frmtEnterInfo.setArguments(args);
        return frmtEnterInfo;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (onEnterInfoItemClickListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.enter_typeinfo, container, false);
        return v;
    }
}
