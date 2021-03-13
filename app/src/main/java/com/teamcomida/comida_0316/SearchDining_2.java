package com.teamcomida.comida_0316;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchDining_2 extends AppCompatActivity {
    ImageView homeButton4, searchButton4, profileButton4;
    TextView diningHallChoice, myCollegeChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dining_2);

        homeButton4 = findViewById(R.id.homeButton4);
        searchButton4 = findViewById(R.id.searchButton4);
        profileButton4 = findViewById(R.id.profileButton4);

        diningHallChoice = findViewById(R.id.diningHallChoice);
        diningHallChoice.setText(SearchResults.theSearchedDiningHall);

        myCollegeChoice = findViewById(R.id.myCollegeChoice);
        myCollegeChoice.setText(CollegeSearch.theSearchedCollege);


        //Search, Profile, and Home Buttons

        //Search
        searchButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CollegeSearch.class));
            }
        });

        //User Profile
        profileButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //Home Button
        homeButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomePage.class));
            }
        });

    }
}