package com.depromeet.tastegroup.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.depromeet.tastegroup.R;

import java.util.ArrayList;

/**
 * Created by jongwook on 4/27/2016.
 */
public class GridviewAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<Gridviewitem> data;
    private int layout;

    public GridviewAdapter(Context context, int layout, ArrayList<Gridviewitem> data){
        this.inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data=data;
        this.layout=layout;
    }
    @Override
    public int getCount(){return data.size();}
    @Override
    public String getItem(int position){return data.get(position).toString();}
    @Override
    public long getItemId(int position){return position;}
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView==null){
            convertView=inflater.inflate(layout,parent,false);
        }
        Gridviewitem gridviewitem=data.get(position);
        TextView text = (TextView)convertView.findViewById(R.id.rest_text);
        text.setText(gridviewitem.getText());
        return convertView;
    }
}