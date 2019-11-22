package com.openclassrooms.entrevoisins.ui.neighbour_details;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.squareup.picasso.Picasso;


public class NeighbourDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_neighbour);

        int id = getIntent().getIntExtra("id", -1);

        Neighbour neighbour = DI.getNeighbourApiService().getNeighbours().get(id);

        String name = neighbour.getName();
        String avatarURL = neighbour.getAvatarUrl();
        String avatarLarge_URL = avatarURL.replace("http://i.pravatar.cc/150?u=", "http://i.pravatar.cc/300?u=");

        TextView textView1 = findViewById(R.id.WhiteName);
        TextView textView2 = findViewById(R.id.Name);
        TextView textView3 = findViewById(R.id.Mail);
        ImageView imageView = findViewById(R.id.Avatar);

        textView1.setText(neighbour.getName());
        textView2.setText(name);
        textView3.setText("    " + name.toLowerCase() + "@gmail.com");


        Picasso.get().load(avatarLarge_URL).centerCrop().resize(220, 160).into(imageView);

    }


}
