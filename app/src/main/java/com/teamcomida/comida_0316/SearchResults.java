package com.teamcomida.comida_0316;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SearchResults extends AppCompatActivity {
    TextView collegeTitle;
    ImageView homeButton3, profileButton3, searchButton3, backButton;
    ListView hallListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_results);

        collegeTitle = findViewById(R.id.collegeTitle);
        homeButton3 = findViewById(R.id.homeButton3);
        profileButton3 = findViewById(R.id.profileButton3);
        searchButton3 = findViewById(R.id.searchButton3);
        backButton = findViewById(R.id.backButton);
        hallListView = findViewById(R.id.hallListView);

        collegeTitle.setText(CollegeSearch.theSearchedCollege);

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