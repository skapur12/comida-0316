package com.teamcomida.comida_0316;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Collection;

public class HomeDining_2 extends AppCompatActivity {
    ImageView homeButton5, searchButton5, profileButton5, backButton;
    TextView diningHallChoice, myCollegeChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dining_2);

        homeButton5 = findViewById(R.id.homeButton5);
        searchButton5 = findViewById(R.id.searchButton5);
        profileButton5 = findViewById(R.id.profileButton5);
        backButton = findViewById(R.id.backButton);

        diningHallChoice = findViewById(R.id.diningHallChoice);
        diningHallChoice.setText(HomePage.homeSearchedDiningHall);

        myCollegeChoice = findViewById(R.id.myCollegeChoice);
        myCollegeChoice.setText(MainActivity.globalUserCollege);



        //Search, Profile, Home, and Back Buttons
        //Search
        searchButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CollegeSearch.class));
            }
        });

        //User Profile
        profileButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //Home Button
        homeButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomePage.class));
            }
        });

        //Back Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomePage.class));
            }
        });
    }
}

