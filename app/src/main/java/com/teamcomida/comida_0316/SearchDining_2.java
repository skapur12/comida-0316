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

import java.util.Objects;

public class SearchDining_2 extends AppCompatActivity {
    ImageView homeButton4, searchButton4, profileButton4, backButton;
    TextView diningHallChoice, myCollegeChoice, reviewTextView;
    RatingBar rating;
    Button writeAReviewButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dining_2);

        homeButton4 = findViewById(R.id.homeButton4);
        searchButton4 = findViewById(R.id.searchButton4);
        profileButton4 = findViewById(R.id.profileButton4);
        backButton = findViewById(R.id.backButton);

        diningHallChoice = findViewById(R.id.diningHallChoice);
        diningHallChoice.setText(SearchResults.theSearchedDiningHall);

        myCollegeChoice = findViewById(R.id.myCollegeChoice);
        myCollegeChoice.setText(CollegeSearch.theSearchedCollege);

        rating = findViewById(R.id.ratingBar2);
        rating.setEnabled(false);

        reviewTextView = findViewById(R.id.reviewTextView);
        reviewTextView.setMovementMethod(new ScrollingMovementMethod());

        writeAReviewButton = findViewById(R.id.writeAReviewButton);

        HomeDining_2.testingReview = 5;

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference reviewsCollection = db.collection("writtenReviews2");
        reviewsCollection.whereEqualTo("college", CollegeSearch.theSearchedCollege)
                .whereEqualTo("hallName", SearchResults.theSearchedDiningHall).get()
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
                        String overallStars = document.getString("overallRating");
                        String priceRating = document.getString("priceRating");
                        String qualityRating = document.getString("quality");
                        String serviceRating = document.getString("service");
                        String hygieneRating = document.getString("hygiene");
                        String comments = document.getString("comments");

                        //build current review string
                        String currReview = String.format("\tName: %s\n\n\tOverall: %s/5.0\n\tPrice: %s/5.0" +
                                "\n\tQuality: %s/5.0\n\tService: %s/5.0\n\tHygiene: %s/5.0\n\tAdditional Comments: %s\n\n\n\n", name, overallStars,
                                priceRating, qualityRating, serviceRating, hygieneRating, comments);

                        totOverallStars += Float.parseFloat(Objects.requireNonNull(document.getString("overallRating")));
                        totReviews++;

                        aggReviews.append(currReview);
                    }
                    rating.setRating(totOverallStars / totReviews);
                    reviewTextView.setText(aggReviews);

                } else {
                    System.out.println("task unsuccessful");
                }
            }
        });
        //Review Button

        writeAReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FoodReview.class));
            }
        });



        //Search, Profile, Home, and Back Buttons

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

        //Back Button
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SearchResults.class));
            }
        });

    }
}

//recycler view link: https://www.youtube.com/watch?v=18VcnYN5_LM
//scroll view link: https://www.youtube.com/watch?v=_IF1vJF7Xb8