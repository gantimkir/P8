package com.example.kirill.p8;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;


public class EnterInfoFragment extends DialogFragment {
    public Double dblTotalMassPerItem=0.0;
    public Double dblTotalItemPerMass=0.0;
    public Double dblMassPerItem=0.0;
    public String strNumSort="";
    public interface onEnterInfoItemClickListener {
            public void onDialogPositiveClick(EnterInfoFragment dialog, Bundle args);
            public void onDialogNegativeClick(EnterInfoFragment dialog);
            public void onDialogNeutralClick(EnterInfoFragment dialog);
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
        args.putString("name", typeinfoEnter[5]);
        args.putString("gost", typeinfoEnter[6]);
        frmtEnterInfo.setArguments(args);
        return frmtEnterInfo;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (onEnterInfoItemClickListener) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final RelativeLayout rltView=(RelativeLayout) inflater.inflate(R.layout.enter_typeinfo,null);
        final EditText editTextItemQuantity=(EditText) rltView.findViewById(R.id.editTextItemQuantity);
        final EditText editTextMassQuantity=(EditText) rltView.findViewById(R.id.editTextMassQuantity);

        String strTemp=getArguments().getString("numsort"); TextView text = (TextView)rltView.findViewById(R.id.textViewNumSort);
        text.setText("Num of sortament: " + strTemp);
        strTemp=getArguments().getString("item_name"); text = (TextView)rltView.findViewById(R.id.textViewItemName);
        text.setText("Item name: " + strTemp);
        final String strItemName=strTemp;
        strTemp=String.valueOf(getArguments().getInt("type_id")); text = (TextView)rltView.findViewById(R.id.textViewTypeID);
        text.setText("Type ID: " + strTemp);
        strTemp=String.valueOf(getArguments().getInt("typeinfo_id")); text = (TextView)rltView.findViewById(R.id.textViewTypeInfoID);
        text.setText("TypeInfo ID: " + strTemp);
        strTemp=String.valueOf(getArguments().getDouble("mass_per_item")); text = (TextView)rltView.findViewById(R.id.textViewMassPerItem);
        text.setText("Mass per item: " + strTemp);
        dblMassPerItem=Double.parseDouble(strTemp);
        strTemp=String.valueOf(getArguments().getString("name")); text = (TextView)rltView.findViewById(R.id.textViewTypeName);
        text.setText("Type name: " + strTemp);
        strTemp=String.valueOf(getArguments().getString("gost")); text = (TextView)rltView.findViewById(R.id.textViewTypeGOST);
        text.setText("GOST: " + strTemp);


        builder.setTitle("Enter form");

        builder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Bundle args=getArguments();
                String strItemQuantity=editTextItemQuantity.getText().toString();
                String strMassQuantity=editTextMassQuantity.getText().toString();

                if (strItemQuantity != null && !strItemQuantity.isEmpty()) {
                    args.putDouble("ItemQuantity",Double.valueOf(strItemQuantity));
                }
                else {args.putDouble("ItemQuantity",0.0d);}

                if (strItemQuantity != null && !strItemQuantity.isEmpty()) {
                    args.putDouble("MassQuantity",Double.valueOf(strMassQuantity));
                }
                else {args.putDouble("MassQuantity",0.0d);}
                listener.onDialogPositiveClick(EnterInfoFragment.this, args);

            }
        });
        builder.setNegativeButton("NEXT", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onDialogNegativeClick(EnterInfoFragment.this);
            }
        });
        builder.setNeutralButton("PREVIOUS", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                listener.onDialogNeutralClick(EnterInfoFragment.this);
            }
        });

        editTextItemQuantity.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),"Mode #1 Render",Toast.LENGTH_SHORT).show();
                        dblTotalMassPerItem=Double.valueOf(editTextItemQuantity.getText().toString())*dblMassPerItem;
                        TextView txtTotalMassPerItem=(TextView) rltView.findViewById(R.id.textViewTotalMassPerItem);
                        String strTemp=String.format("%1$,.2f",dblTotalMassPerItem)+" kg";
                        txtTotalMassPerItem.setText(strTemp);
                    }
                }
        );

        editTextMassQuantity.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),"Mode #2 Render",Toast.LENGTH_SHORT).show();
                        dblTotalItemPerMass=Double.valueOf(editTextMassQuantity.getText().toString())/dblMassPerItem;
                        TextView txtTotalItemPerMass=(TextView) rltView.findViewById(R.id.textViewTotalItemPerMass);
                        String strTemp=String.format("%1$,.3f",dblTotalItemPerMass)+" "+strItemName;
                        txtTotalItemPerMass.setText(strTemp);
                    }
                }
        );

        final Switch swtchMode=(Switch) rltView.findViewById(R.id.switchMode);
        swtchMode.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity(),"Mode was changed",Toast.LENGTH_SHORT).show();
                    }
                }        );

        builder.setView(rltView);
//        return super.onCreateDialog(savedInstanceState);
        return builder.create();
    }

}
