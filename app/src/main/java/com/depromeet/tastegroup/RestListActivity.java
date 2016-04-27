package com.depromeet.tastegroup;

import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.StateListDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.depromeet.tastegroup.utils.Constants;
import com.depromeet.tastegroup.utils.GridviewAdapter;
import com.depromeet.tastegroup.utils.Gridviewitem;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class RestListActivity extends AppCompatActivity {
    public static final String FOOD_TYPE = "restNo";
    private int foodType;
    private GridView gridView;
    private GridviewAdapter gridviewAdapter;
    private ArrayList<Gridviewitem> data;
    private ArrayList<Integer> resIds;
    private RadioButton currentSelection;
    private String[] foodCategory = {"전체", "고기", "일식", "중식", "치킨", "피자", "분식", "양식", "기타"};
    private String[] alcoholCategory = {"전체", "소주", "막걸리", "양주", "기타"};
    private String[] hangoverCategory = {"전체", "탕", "찌게", "기타"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_list);
        foodType = (Integer)getIntent().getExtras().get(FOOD_TYPE);

        // Create RadioButton Group with food type selection
        createCategories();
        // Get data from firebase and display the list of restaurants
        getData();
    }

    public void createCategories() {
        RadioGroup myRadioGroup = (RadioGroup) findViewById(R.id.my_radiogroup);
        // Find out which type of foods we have
        final String[] categoryList;
        switch (foodType) {
            case 0: categoryList = foodCategory;
                break;
            case 1: categoryList = alcoholCategory;
                break;
            case 2: categoryList = hangoverCategory;
                break;
            default: categoryList = alcoholCategory;
                break;
        }

        // Create radiobutton for each possible category
        for (int i = 0; i < categoryList.length; i++) {
            RadioButton rb = new RadioButton(this);
            rb.setButtonDrawable(new StateListDrawable());
            rb.setText(categoryList[i]);
            float scale = getResources().getDisplayMetrics().density;
            int dpAsPixels = (int) (15*scale + 0.5f);
            rb.setPadding(dpAsPixels,0,0,0);
            rb.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);

            // Set the "All" setting as the default value
            if (i == 0) {
                currentSelection = rb;
                rb.setTypeface(null, Typeface.BOLD);
                rb.setPaintFlags(rb.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
            }
            myRadioGroup.addView(rb);
        }

        // Check when the button is clicked to change the selected setting
        myRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup rGroup, int checkedId) {
                // Deselect the currently selected button
                currentSelection.setTypeface(null, Typeface.NORMAL);
                currentSelection.setPaintFlags(currentSelection.getPaintFlags() & (~ Paint.UNDERLINE_TEXT_FLAG));

                // Get the new button and make it bolded and underlined
                RadioButton checkedRadioButton = (RadioButton)rGroup.findViewById(checkedId);
                currentSelection = checkedRadioButton;
                boolean isChecked = checkedRadioButton.isChecked();
                if (isChecked) {
                    checkedRadioButton.setTypeface(null, Typeface.BOLD);
                    checkedRadioButton.setPaintFlags(checkedRadioButton.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
                }
            }
        });
    }

    public void getData() {
        // Set up listview click
        gridView = (GridView) findViewById(R.id.rest_list);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        gridviewAdapter = new GridviewAdapter(this, R.layout.restaurant_card, data);
        gridView.setAdapter(gridviewAdapter);

        // Choose the type of food
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
                    Gridviewitem rest = new Gridviewitem(postSnapshot.getValue(String.class));
                    data.add(rest);
                    int resId = Integer.parseInt(postSnapshot.getKey());
                    resIds.add(resId);
                }
                gridviewAdapter.notifyDataSetChanged();

                ((BaseAdapter) gridView.getAdapter()).notifyDataSetChanged();
            }
            @Override public void onCancelled(FirebaseError error) { }
        });
    }

}
