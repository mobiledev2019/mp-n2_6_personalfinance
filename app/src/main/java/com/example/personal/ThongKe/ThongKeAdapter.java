package com.example.personal.ThongKe;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.personal.R;

import java.util.ArrayList;

/**
 * Created by trongnhan on 10/20/16.
 */

public class ThongKeAdapter extends ArrayAdapter<ThongKe> {
    private Activity activity;
    private int idLayout;
    private ArrayList<ThongKe> list;
    public ThongKeAdapter(Activity activity, int idLayout, ArrayList<ThongKe> list){
        super(activity,idLayout,list);
        this.activity=activity;
        this.idLayout=idLayout;
        this.list=list;
    }
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater= LayoutInflater.from(activity);
        convertView=inflater.inflate(idLayout,null);
        TextView txtphannhom = (TextView)convertView.findViewById(R.id.txtphannhom);
        TextView txtsotien=(TextView)convertView.findViewById(R.id.txtsotien);


        txtphannhom.setText(list.get(position).getKhoanthukhoanchi());
        txtsotien.setText(list.get(position).getSotien().toString());

        return convertView;
    }

}
