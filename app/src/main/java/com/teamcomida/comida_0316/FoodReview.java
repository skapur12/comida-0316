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
    TextView comments;
    ImageView homeButton1, searchButton1, profileButton1;
    Button submit, uploadPhoto;

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
        comments = findViewById(R.id.commentsTextField);
        comments.setMovementMethod(new ScrollingMovementMethod());
        submit = findViewById(R.id.submitButton);
        uploadPhoto = findViewById(R.id.uploadPhotoButton);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float overallRating = overall.getRating();
                float qualityRating = quality.getRating();
                float hygieneRating = hygiene.getRating();
                float priceRating = price.getRating();
                float serviceRating = service.getRating();
                String comment = comments.getText().toString();
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                HashMap<String, Object> data = new HashMap<>();
                data.put("overallRating", Float.toString(overallRating));
                data.put("quality", Float.toString(qualityRating));
                data.put("hygiene", Float.toString(hygieneRating));
                data.put("priceRating", Float.toString(priceRating));
                data.put("service", Float.toString(serviceRating));
                data.put("comments", comment);
                data.put("name", "test123");     //replace with MainActivity.globalUserFullName when activity is connected
                data.put("hallName", "test123"); //replace with HomePage.homeSearchedDiningHall when activity is connected
                data.put("food", "test123");    //replace once food is created
                data.put("college", "test123"); //replace with MainActivity.globalUserCollege when activity is connected
                data.put("username", "test123"); //replace with MainActivity.globalUserUsername when activity is connected

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