package com.depromeet.tastegroup;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public class Listviewitem {
        private int icon;

        public int getIcon(){return icon;}

        public Listviewitem(int icon){
            this.icon=icon;
        }
    }
    public class ListviewAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<Listviewitem> data;
        private int layout;

        public ListviewAdapter(Context context, int layout, ArrayList<Listviewitem> data){
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
            Listviewitem listviewitem=data.get(position);
            ImageView icon = (ImageView)convertView.findViewById(R.id.list_img);

            icon.setImageResource(listviewitem.getIcon());

            return convertView;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView)findViewById(R.id.taste_list);

        ArrayList<Listviewitem> data=new ArrayList<>();
        Listviewitem lion=new Listviewitem(R.drawable.img1);
        Listviewitem tiger=new Listviewitem(R.drawable.img2);
        Listviewitem dog=new Listviewitem(R.drawable.img3);
        data.add(lion);
        data.add(tiger);
        data.add(dog);

        listView.setAdapter(new ListviewAdapter(this,R.layout.picture_layout,data));
    }
}