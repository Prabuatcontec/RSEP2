package com.example.rsepphase2;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.RequiresApi;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ProgramAdapator extends ArrayAdapter<String> {
    Context context;
    ArrayList<String>  sname;
    ArrayList<String> pname;
    ArrayList<String> mname;
    ArrayList<String> oname;
    public ProgramAdapator(Context context, ArrayList<String> pname, ArrayList<String> sname,ArrayList<String> oname, ArrayList<String> mname){
        super(context,
                R.layout.single_item,
                R.id.owner,
                mname);

        this.context = context;
        this.pname = pname;
        this.mname = mname;
        this.oname = oname;
        this.sname = sname;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View getView(int position, View convertView, ViewGroup parent) {

        View singleItem = convertView;

        ProgramViewHolder holder = null;

        if(singleItem == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            singleItem = layoutInflater.inflate(R.layout.single_item, parent, false);
            holder = new ProgramViewHolder(singleItem);
            singleItem.setTag(holder);
        } else {
            holder = (ProgramViewHolder) singleItem.getTag();
        }


        String rs = sname.get(position);
        if (rs.contains("-") == true) {
            singleItem.setBackgroundResource(R.drawable.border1);
        } else {
            singleItem.setBackgroundResource(R.drawable.border);
        }

        SimpleDateFormat formatter = new SimpleDateFormat("E, MMM dd yyyy");

        String s = sname.get(position);
        String[] arrOfStr = s.split("T");


        holder.pname.setText(pname.get(position));
        holder.mname.setText(mname.get(position));
        holder.oname.setText(oname.get(position));
        holder.sname.setText(arrOfStr[0]);


        return singleItem;
    }
}
