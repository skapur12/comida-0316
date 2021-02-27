package com.teamcomida.comida_0316;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CollegeSearch extends AppCompatActivity {
    private static final String TAG = "College Review" ;
    ListView listView;
    SearchView searchView;
    List<String> collegeList = new ArrayList<String>();
    ArrayAdapter<String> collegeAdapter;
    ImageView homeButton1, searchButton1, profileButton1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_search);
        listView = (ListView) findViewById(R.id.collegeListView);
        homeButton1 = findViewById(R.id.homeButton1);
        searchButton1 = findViewById(R.id.searchButton1);
        profileButton1 = findViewById(R.id.profileButton1);
        Resources res = getResources();
        String[] colleges = res.getStringArray(R.array.colleges_array);
        Collections.addAll(collegeList, colleges);  //collegeList contains all colleges

        //initialize adapter
        collegeAdapter = new ArrayAdapter<String>(CollegeSearch.this,
                android.R.layout.simple_list_item_1, collegeList);
        listView.setAdapter(collegeAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() { //querying college data
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //do the query for the college
                Toast.makeText(getApplicationContext(),collegeAdapter.getItem(position),Toast.LENGTH_SHORT).show();
                String college = collegeAdapter.getItem(position);
                System.out.println(college);
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                CollectionReference collegeCollection = db.collection("halls"); //set a pointer the set of documents at the halls collection reference
                collegeCollection.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) { //looping through each document
                                /*if(document.getString("college").equals(college)) {
                                    System.out.println(document.getString("name"));
                                }*/
                                System.out.println(document.getString("name"));
                                System.out.println(document.getDocumentReference("college"));
                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                            System.out.println("Task was not successful");
                        }
                    }
                });
            }
        });
        searchView = (SearchView) findViewById(R.id.collegeSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (collegeList.contains(query)) {
                    collegeAdapter.getFilter().filter(query);
                } else {
                    //Toast.makeText(CollegeSearch.this, "No matches found",Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                collegeAdapter.getFilter().filter(newText);
                return false;
            }
        });



        //Search, Profile, and Home Directions
        //Search
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

    @Override
    //could possibly just delete this whole method (onCreateOptionsMenu)
    public boolean onCreateOptionsMenu(Menu menu) {
        //menu filter initialization and inflation
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_college_search,menu);
        //initialize menu item
        MenuItem menuItem = menu.findItem(R.id.college_search_view);
        //initialize search view
        searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (collegeList.contains(query)) {
                    collegeAdapter.getFilter().filter(query);
                } else {
                    Toast.makeText(CollegeSearch.this, "No matches found",Toast.LENGTH_LONG).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                collegeAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);

    }


}

//sources:
//https://stackoverflow.com/questions/59785204/creating-a-search-interface-android
//https://medium.com/@yugandhardcs21/creating-a-search-interface-in-android-dc1fd6a53b4
//https://www.youtube.com/watch?v=rdu1ZqM9rSE
//https://stackoverflow.com/questions/45306133/getactionview-is-deprecated
//https://stackoverflow.com/questions/48492993/firestore-get-documentsnapshots-fields-value for firestore querying
//https://firebase.google.com/docs/firestore/query-data/get-data?authuser=0 docs for querying from firestore