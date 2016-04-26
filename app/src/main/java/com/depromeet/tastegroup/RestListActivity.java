package com.depromeet.tastegroup;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class RestListActivity extends AppCompatActivity {
    public static final String FOOD_TYPE = "restNo";
    private int foodType;
    private ListView listView;
    private ListviewAdapter listviewAdapter;
    private ArrayList<Listviewitem> data;
    private ArrayList<Integer> resIds;

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
            TextView text = (TextView)convertView.findViewById(R.id.rest_text);
            text.setText(listviewitem.getText());

            return convertView;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_list);
        // Set up listview click
        listView = (ListView)findViewById(R.id.rest_list);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(RestListActivity.this,RestInfoActivity.class);
                intent.putExtra(RestInfoActivity.REST_TYPE ,resIds.get(position));
                startActivity(intent);
            }
        });


        // Display the data to the listview
        data = new ArrayList<>();
        resIds = new ArrayList<>();
        listviewAdapter = new ListviewAdapter(this, R.layout.restaurant_card, data);
        listView.setAdapter(listviewAdapter);

        // Choose the type of food
        foodType = (Integer)getIntent().getExtras().get(FOOD_TYPE);
        String foodTypeString;
        switch (foodType) {
            case 0: foodTypeString = "Food";
                break;
            case 1: foodTypeString = "Alcohol";
                break;
            case 2: foodTypeString = "Hangover";
                break;
            default: foodTypeString = "Alcohol";
                break;
        }

        // Set up Firebase
        Firebase baseRef = new Firebase(Constants.FIREBASE_URL);
        baseRef.child("Categories").child(foodTypeString).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Listviewitem rest = new Listviewitem(postSnapshot.getValue(String.class));
                    data.add(rest);
                    int resId = Integer.parseInt(postSnapshot.getKey());
                    resIds.add(resId);
                }
                listviewAdapter.notifyDataSetChanged();

                ((BaseAdapter) listView.getAdapter()).notifyDataSetChanged();
            }
            @Override public void onCancelled(FirebaseError error) { }
        });


    }
}
