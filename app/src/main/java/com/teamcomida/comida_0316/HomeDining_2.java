package com.teamcomida.comida_0316;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
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
import java.util.Objects;

public class HomeDining_2 extends AppCompatActivity {
    ImageView homeButton5, searchButton5, profileButton5, backButton;
    TextView diningHallChoice, myCollegeChoice, homeReviews;
    Button writeAReview;
    RatingBar rating;

    public static int testingReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_dining_2);

        testingReview = 1;

        homeButton5 = findViewById(R.id.homeButton5);
        searchButton5 = findViewById(R.id.searchButton5);
        profileButton5 = findViewById(R.id.profileButton5);
        backButton = findViewById(R.id.backButton);

        writeAReview = findViewById(R.id.writeAReview);

        rating = findViewById(R.id.ratingBar2);
        rating.setEnabled(false);

        homeReviews = findViewById(R.id.homeReviews);
        homeReviews.setMovementMethod(new ScrollingMovementMethod());


        diningHallChoice = findViewById(R.id.diningHallChoice);
        diningHallChoice.setText(HomePage.homeSearchedDiningHall);

        myCollegeChoice = findViewById(R.id.myCollegeChoice);
        myCollegeChoice.setText(MainActivity.globalUserCollege);

        //NEW STUFF

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reviewsCollection = db.collection("writtenReviews2");
        reviewsCollection.whereEqualTo("college", MainActivity.globalUserCollege)
                .whereEqualTo("hallName", HomePage.homeSearchedDiningHall).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        StringBuilder aggReviews = new StringBuilder();
                        float totOverallStars = 0;
                        int totReviews = 0;
                        if (task.isSuccessful()) {
                            System.out.println("successful");
                            for (QueryDocumentSnapshot document: Objects.requireNonNull(task.getResult())) {
                                //get necessary firestore field values for building string
                                String name = document.getString("name");
                                String food = document.getString("food");
                                String overallStars = document.getString("overallRating");
                                String priceRating = document.getString("priceRating");
                                String qualityRating = document.getString("quality");
                                String serviceRating = document.getString("service");
                                String hygieneRating = document.getString("hygiene");
                                String comments = document.getString("comments");

                                //build current review string
                                String currReview = String.format("\tName: %s\n\n\tFood: %s\n\tOverall: %s/5.0\n\tPrice: %s/5.0" +
                                                "\n\tQuality: %s/5.0\n\tService: %s/5.0\n\tHygiene: %s/5.0\n\tAdditional Comments: %s\n\n\n\n", name, food, overallStars,
                                        priceRating, qualityRating, serviceRating, hygieneRating, comments);

                                totOverallStars += Float.parseFloat(Objects.requireNonNull(document.getString("overallRating")));
                                totReviews++;

                                aggReviews.append(currReview);
                            }
                            rating.setRating(totOverallStars / totReviews);
                            homeReviews.setText(aggReviews);

                        } else {
                            System.out.println("task unsuccessful");
                        }
                    }
                });

        //END NEW STUFF

        //Write A Review
        writeAReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FoodReview.class));
            }
        });



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

