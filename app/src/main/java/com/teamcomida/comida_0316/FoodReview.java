package com.teamcomida.comida_0316;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class FoodReview extends AppCompatActivity {
    RatingBar overall, quality, hygiene, price, service;
    TextView comments, diningHallTextField, nameOfUsersCollege;
    ImageView homeButton1, searchButton1, profileButton1;
    Button submit, uploadPhoto;
    ImageView backButtonFoodReview;
    EditText user_food, user_comments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_review);
        homeButton1 = findViewById(R.id.homeButtonFoodReview);
        searchButton1 = findViewById(R.id.searchButtonFoodReview);
        profileButton1 = findViewById(R.id.profileButtonFoodReview);
        overall = findViewById(R.id.overallRatingBar);
        quality = findViewById(R.id.qualityRatingBar);
        hygiene = findViewById(R.id.hygieneRatingBar);
        price = findViewById(R.id.priceRatingBar);
        service = findViewById(R.id.serviceRatingBar);
        comments = findViewById(R.id.commentsPlainText);

        submit = findViewById(R.id.submitButton);

        user_food = findViewById(R.id.foodNameTextField);

        user_comments = findViewById(R.id.commentsTextField);
        user_comments.setMovementMethod(new ScrollingMovementMethod());

        backButtonFoodReview = findViewById(R.id.backButtonFoodReview);

        diningHallTextField = findViewById(R.id.diningHallTextField);
        //diningHallTextField.setText(HomePage.homeSearchedDiningHall);

        nameOfUsersCollege = findViewById(R.id.nameOfUsersCollege);
        //nameOfUsersCollege.setText(MainActivity.globalUserCollege);

        if (HomeDining_2.testingReview == 1) {
            diningHallTextField.setText(HomePage.homeSearchedDiningHall);
            nameOfUsersCollege.setText(MainActivity.globalUserCollege);
        } else if (HomeDining_2.testingReview == 5) {
            diningHallTextField.setText(SearchResults.theSearchedDiningHall);
            nameOfUsersCollege.setText(CollegeSearch.theSearchedCollege);
        }

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float overallRating = overall.getRating();
                float qualityRating = quality.getRating();
                float hygieneRating = hygiene.getRating();
                float priceRating = price.getRating();
                float serviceRating = service.getRating();
                String comment = user_comments.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();

                String food = user_food.getText().toString();

                HashMap<String, Object> data = new HashMap<>();
                data.put("overallRating", Float.toString(overallRating));
                data.put("quality", Float.toString(qualityRating));
                data.put("hygiene", Float.toString(hygieneRating));
                data.put("priceRating", Float.toString(priceRating));
                data.put("service", Float.toString(serviceRating));
                data.put("comments", comment);

                data.put("name", MainActivity.globalUserFullName);     //replace with MainActivity.globalUserFullName when activity is connected
                //data.put("hallName", "test123"); //replace with HomePage.homeSearchedDiningHall when activity is connected
                data.put("food", food);    //replace once food is created
               // data.put("college", "test123"); //replace with MainActivity.globalUserCollege when activity is connected
                data.put("username", MainActivity.globalUserUsername); //replace with MainActivity.globalUserUsername when activity is connected

                if (HomeDining_2.testingReview == 1) {
                    data.put("hallName", HomePage.homeSearchedDiningHall);
                    data.put("college", MainActivity.globalUserCollege);

                    startActivity(new Intent(getApplicationContext(), HomeDining_2.class));

                } else if (HomeDining_2.testingReview == 5) {
                    data.put("hallName", SearchResults.theSearchedDiningHall);
                    data.put("college", CollegeSearch.theSearchedCollege );

                    startActivity(new Intent(getApplicationContext(), SearchDining_2.class));

                }

                String id = db.collection("writtenReviews2").document().getId();
                db.collection("writtenReviews2").document(id).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(FoodReview.this,"review successfully completed", Toast.LENGTH_SHORT).show();
                        System.out.println("successful");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(FoodReview.this,"Something went wrong. Review not successfully stored", Toast.LENGTH_SHORT).show();
                        System.out.println("failure");
                    }
                });
            }
        });

//        uploadPhoto.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // open gallery
//                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(openGalleryIntent, 1000);
//            }
//        });

        //Back Button Logic
        if (HomeDining_2.testingReview == 1) {

            backButtonFoodReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), HomeDining_2.class));
                }
            });

        } else if (HomeDining_2.testingReview == 5) {

            backButtonFoodReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), SearchDining_2.class));
                }
            });

        }


        //Submitting the Review
        /* if (HomeDining_2.testingReview == 1) {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), HomeDining_2.class));
                }
            });

        } else if (HomeDining_2.testingReview == 5) {
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), SearchDining_2.class));
                }
            });

        } */




        searchButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CollegeSearch.class));
            }
        });

        //User Profile
        profileButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //Home Button
        homeButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomePage.class));
            }
        });
    }
}
//make custom ratingBar