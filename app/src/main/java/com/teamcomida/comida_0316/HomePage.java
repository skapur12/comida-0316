package com.teamcomida.comida_0316;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomePage extends AppCompatActivity {
    private static final String TAG = "TAG";

    ImageView profileButton2, searchButton2, homeButton2;
    TextView collegeChoice;
    FirebaseFirestore fStore;
    String userId;
    FirebaseAuth fAuth;
    ListView homeListView;
    RatingBar ratingBar;
    List<String> homeList = new ArrayList<String>();
    public static String theSample;
    public static String homeSearchedDiningHall;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //FirebaseFirestore db = FirebaseFirestore.getInstance();

        FirebaseFirestore db = FirebaseFirestore.getInstance();


        homeButton2 = findViewById(R.id.homeButton2);
        searchButton2 = findViewById(R.id.searchButton2);
        profileButton2 = findViewById(R.id.profileButton2);
        collegeChoice = findViewById(R.id.collegeChoice);
        homeListView = findViewById(R.id.homeListView);

        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setEnabled(false);

        fAuth = FirebaseAuth.getInstance();

        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        CollectionReference halls2 = fStore.collection("halls2");


        //NEW INFO

        //query reviews in writtenReviews2 to get average star rating for college overall
        db.collection("writtenReviews2").whereEqualTo("college", MainActivity.globalUserCollege)
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    System.out.println("successful");
                    float totOverallStars = 0;
                    int totReviews = 0;
                    for (QueryDocumentSnapshot document: Objects.requireNonNull(task.getResult())) {
                        //get necessary firestore field values for building string

                        totOverallStars += Float.parseFloat(Objects.requireNonNull(document.getString("overallRating")));
                        totReviews++;


                    }
                    ratingBar.setRating(totOverallStars / totReviews);


                } else {
                    System.out.println("task unsuccessful");
                }
            }
        });

        //END NEW INFO


        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e!=null){
                    Log.d("DocumentSnapshot","Error:"+e.getMessage());
                }else {
                    //getting the string using key defined from the document snapshot
                    collegeChoice.setText(documentSnapshot.getString("college"));
                    theSample = documentSnapshot.getString("college");
                }

            }
        });

        String ourCollege = collegeChoice.toString();
        System.out.println("OURCOLLEGE" + ourCollege);
        System.out.println("THESAMPLE" + theSample);


        //NEW ADDITIONS

        fStore.collection("halls2")
                .whereEqualTo("college", MainActivity.globalUserCollege)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                            System.out.println("NEW COLLEGE TITLE" + ourCollege);
                            Log.d(TAG, document.getId() + " => " + document.getData());
                            System.out.println("NEW DOCUMENT id" + document.getId() + " DOC DATA " + document.getData());
                            homeList.add(document.getId());
                            System.out.println("NEW INTERMED"  + homeList);

                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                    ArrayAdapter<String> homeAdapter;
                    homeAdapter = new ArrayAdapter<String>(HomePage.this,
                            android.R.layout.simple_list_item_1, homeList);
                    homeListView.setAdapter(homeAdapter);


                    homeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            startActivity(new Intent(getApplicationContext(), HomeDining_2.class));
                            homeSearchedDiningHall = homeAdapter.getItem(position);
                        }
                    });
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