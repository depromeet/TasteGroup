package com.depromeet.tastegroup;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RestInfoActivity extends AppCompatActivity {
    public static final String REST_TYPE = "restType";

    public class Listviewitem {
        private String text;

        public String getText(){return text;}

        public Listviewitem(String text){
            this.text=text;
        }
    }
    private class ListviewAdapter extends BaseAdapter {
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
            TextView text = (TextView)convertView.findViewById(R.id.item_text);

            text.setText(listviewitem.getText());

            return convertView;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_info);

        ListView listView = (ListView)findViewById(R.id.rest_info_list);

        ArrayList<Listviewitem> data=new ArrayList<>();

        int restNo = (Integer)getIntent().getExtras().get(REST_TYPE);

        Listviewitem lion=new Listviewitem(String.valueOf(restNo));
        Listviewitem tiger=new Listviewitem("BBBBBBBB");
        Listviewitem dog=new Listviewitem("CCCCCCCC");

        data.add(lion);
        data.add(tiger);
        data.add(dog);

        listView.setAdapter(new ListviewAdapter(this, R.layout.rest_list_layout, data));

    }
}