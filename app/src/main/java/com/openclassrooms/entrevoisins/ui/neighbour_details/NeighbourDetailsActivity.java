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
        String address = getIntent().getStringExtra("address");
        String mail = getIntent().getStringExtra("mail");
        String phone = getIntent().getStringExtra("phone");
        String description = getIntent().getStringExtra("description");
        String descriptionTitle = getIntent().getStringExtra("descriptionTitle");
        String avatarURL = getIntent().getStringExtra("avatarURL");
        String avatarURL2 = avatarURL.replace("http://i.pravatar.cc/150?u=", "http://i.pravatar.cc/300?u=");

        TextView textView1 = findViewById(R.id.WhiteName);
        TextView textView2 = findViewById(R.id.Name);
        TextView textView3 = findViewById(R.id.Adresse);
        TextView textView4 = findViewById(R.id.Mail);
        TextView textView5 = findViewById(R.id.Phone);
        TextView textView6 = findViewById(R.id.Description);
        TextView textView7 = findViewById(R.id.DescriptionTitle);
        ImageView imageView = findViewById(R.id.Avatar);

        textView1.setText(name);
        textView2.setText(name);
        textView3.setText(address);
        textView4.setText(mail);
        textView5.setText(phone);
        textView6.setText(description);
        textView7.setText(descriptionTitle);
        Picasso.get().load(avatarURL2).centerCrop().resize(220, 160).into(imageView);

    }
}
