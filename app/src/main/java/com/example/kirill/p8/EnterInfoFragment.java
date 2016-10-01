package com.example.kirill.p8;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;


public class EnterInfoFragment extends DialogFragment {

    public interface onEnterInfoItemClickListener {
        public void onEnterInfoItemClick(int position, long id);
    }
    onEnterInfoItemClickListener listener;


}
