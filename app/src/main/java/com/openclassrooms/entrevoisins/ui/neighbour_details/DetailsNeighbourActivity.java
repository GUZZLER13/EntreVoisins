package com.openclassrooms.entrevoisins.ui.neighbour_details;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.openclassrooms.entrevoisins.R;

public class DetailsNeighbourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_neighbour);
        Integer id = getIntent().getIntExtra("id", -1);
        TextView textView = findViewById(R.id.idNumber);
        textView.setText(id.toString());
    }
}
