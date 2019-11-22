package com.openclassrooms.entrevoisins.ui.neighbour_details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.entrevoisins.R;
import com.squareup.picasso.Picasso;


public class NeighbourDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_neighbour);

        String name = getIntent().getStringExtra("name");
        String avatarURL = getIntent().getStringExtra("avatarURL");
        String avatarURL2 = avatarURL.replace("http://i.pravatar.cc/150?u=", "http://i.pravatar.cc/300?u=");

        TextView textView1 = findViewById(R.id.WhiteName);
        TextView textView2 = findViewById(R.id.Name);
        TextView textView4 = findViewById(R.id.Mail);
        ImageView imageView = findViewById(R.id.Avatar);

        textView1.setText(name);
        textView2.setText(name);
        textView4.setText("    " + name.toLowerCase() + "@gmail.com");

        Picasso.get().load(avatarURL2).centerCrop().resize(220, 160).into(imageView);


    }


}
