package com.depromeet.tastegroup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
    private ImageView imgView;
    private int resId;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_info);

        nameText = (TextView) findViewById(R.id.Name);
        phoneText = (TextView) findViewById(R.id.Telephone);
        imgView = (ImageView) findViewById(R.id.img);
        int resId = (Integer)getIntent().getExtras().get(REST_TYPE);
        nameText.setText(String.valueOf(resId));

        Firebase baseRef = new Firebase(Constants.FIREBASE_URL);
        baseRef.child("Restaurants").child(String.valueOf(resId)).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                nameText.setText(snapshot.child("Name").getValue(String.class));
                phoneText.setText(("Telephone: " + snapshot.child("Telephone").getValue(Long.class)));
                byte[] decodedBytes = Base64.decode(snapshot.child("Image").getValue(String.class), 0);
                Bitmap bitImg = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
                imgView.setImageBitmap(bitImg);
            }

            @Override public void onCancelled(FirebaseError error) { }

        });
    }
}