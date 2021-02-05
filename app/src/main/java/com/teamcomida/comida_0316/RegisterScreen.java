package com.teamcomida.comida_0316;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

import android.app.Activity;

public class RegisterScreen extends Fragment{

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_register_screen, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.hello_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(RegisterScreen.this)
                        .navigate(R.id.action_RegisterScreen_to_FirstFragment);
            }
        });

    //get the spinner from the xml.
        Spinner dropdown = (Spinner) view.findViewById(R.id.spinner1);
    //create a list of items for the spinner.
        String[] items = new String[]{"Kennesaw State University", "Georgia Institute of Technology",
                "Georgia Southern University", "Georgia State University", "University of Georgia"};
    //create an adapter to describe how the items are displayed, adapters are used in several places in android.
    //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, items);
    //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);


    }
}