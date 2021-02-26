package com.example.rsepphase2;

import android.view.View;
import android.widget.TextView;

public class ProgramViewHolder {

    TextView sname;
    TextView pname;
    TextView mname;
    TextView oname;

    ProgramViewHolder(View v){
        sname = v.findViewById(R.id.Amount);
        pname = v.findViewById(R.id.owner);
        mname = v.findViewById(R.id.date);
        oname = v.findViewById(R.id.rs);
    }
}
