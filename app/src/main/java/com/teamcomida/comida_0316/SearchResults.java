package com.teamcomida.comida_0316;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SearchResults extends AppCompatActivity {
    private static final String TAG = "TAG";
    TextView collegeTitle;
    ImageView homeButton3, profileButton3, searchButton3, backButton;
    ListView hallListView;
    List<String> hallList = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference halls2 = db.collection("halls2");
        Map<String, Object> data1 = new HashMap<>();
        data1.put("college", "Georgia Institute of Technology");
        halls2.document("West Village Dining Hall").set(data1);
        halls2.document("Brittain Dining Hall").set(data1);
        halls2.document("North Avenue Dining Hall").set(data1);


        collegeTitle = findViewById(R.id.collegeTitle);
        homeButton3 = findViewById(R.id.homeButton3);
        profileButton3 = findViewById(R.id.profileButton3);
        searchButton3 = findViewById(R.id.searchButton3);
        backButton = findViewById(R.id.backButton);
        hallListView = findViewById(R.id.hallListView);
        //ArrayAdapter<String> hallAdapter;


        collegeTitle.setText(CollegeSearch.theSearchedCollege);

        CollectionReference citiesRef = db.collection("halls2");
        Query query = citiesRef.whereEqualTo("college", collegeTitle.toString());


        db.collection("halls2")
                .whereEqualTo("college", CollegeSearch.theSearchedCollege)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            System.out.println("COLLEGE TITLE" + collegeTitle.toString());
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            System.out.println("DOCUMENT id" + document.getId() + " DOC DATA " + document.getData());
                            hallList.add(document.getId());
                            System.out.println("INTERMED"  + hallList);

                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                    ArrayAdapter<String> hallAdapter;
                    hallAdapter = new ArrayAdapter<String>(SearchResults.this,
                            android.R.layout.simple_list_item_1, hallList);
                    hallListView.setAdapter(hallAdapter);
                });

        hallListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(), SearchDining_2.class));
            }
        });
//System.out.println("MY halllist" + hallList);
        //hallAdapter = new ArrayAdapter<String>(SearchResults.this,
        //        android.R.layout.simple_list_item_1, hallList);
        //hallListView.setAdapter(hallAdapter);

        //Search, Profile, and Home Directions
        //Search
        searchButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CollegeSearch.class));
            }
        });

        //User Profile
        profileButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //Home Button
        homeButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomePage.class));
            }
        });


        //Back Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CollegeSearch.class));
            }
        });
    }
}