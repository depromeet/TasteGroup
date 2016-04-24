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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.authentication.*;

import java.util.ArrayList;

public class RestInfoActivity extends AppCompatActivity {
    public static final String REST_TYPE = "restType";
    private TextView nameText;
    private TextView phoneText;
    private int resId;

    public class Restaurant {
        private String Name;
        private Long Telephone;

        public Restaurant() {
            // empty default constructor, necessary for Firebase to be able to deserialize blog posts
        }

        public String getName() {
            return this.Name;
        }

        public Long getTelephone() {
            return this.Telephone;
        }

    }
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_info);

        nameText = (TextView) findViewById(R.id.Name);
        phoneText = (TextView) findViewById(R.id.Telephone);
        int resId = (Integer)getIntent().getExtras().get(REST_TYPE);
        nameText.setText(String.valueOf(resId));

        Firebase baseRef = new Firebase(Constants.FIREBASE_URL);
        baseRef.child("Restaurants").child(String.valueOf(resId)).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                //Restaurant res = snapshot.getValue(Restaurant.class);
                //nameText.setText("Name: " + res.getName());
                //phoneText.setText("Telephone: " + String.valueOf(res.getTelephone()));
                nameText.setText("Name: " + snapshot.child("Name").getValue(String.class));
                phoneText.setText(("Telephone: " + snapshot.child("Telephone").getValue(Long.class)));
            }

            @Override public void onCancelled(FirebaseError error) { }

        });
    }
}