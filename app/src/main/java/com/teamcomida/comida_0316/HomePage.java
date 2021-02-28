package com.teamcomida.comida_0316;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class HomePage extends AppCompatActivity {
    ImageView profileButton2, searchButton2, homeButton2;
    TextView collegeChoice;
    FirebaseFirestore fStore;
    String userId;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        homeButton2 = findViewById(R.id.homeButton2);
        searchButton2 = findViewById(R.id.searchButton2);
        profileButton2 = findViewById(R.id.profileButton2);
        collegeChoice = findViewById(R.id.collegeChoice);
        fAuth = FirebaseAuth.getInstance();

        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();



        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d("DocumentSnapshot","Error:"+e.getMessage());
                }else {
                    //getting the string using key defined from the document snapshot
                    collegeChoice.setText(documentSnapshot.getString("college"));
                }

            }
        });

        //Search, Profile, and Home Directions
        //Search
        searchButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), CollegeSearch.class));
            }
        });

        //User Profile
        profileButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        //Home Button
        homeButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), HomePage.class));
            }
        });
    }
}