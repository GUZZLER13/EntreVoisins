package com.openclassrooms.entrevoisins.ui.neighbour_details;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.FavoritesFragment;
import com.squareup.picasso.Picasso;


public class NeighbourDetailsActivity extends AppCompatActivity {


    private FloatingActionButton mFloat;
    private NeighbourApiService mApiService = DI.getNeighbourApiService();

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




        
        mFloat = findViewById(R.id.Favorite);

        mFloat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!mApiService.getFavorites().contains(neighbour)) {
                    neighbour.setIsFavorite(true);
                    mApiService.addFavorite(neighbour);


                } else {
                    neighbour.setIsFavorite(false);
                    mApiService.deleteFavorite(neighbour);
                }

                Log.i("Name", neighbour.getName());
                Log.i("favori", String.valueOf(neighbour.getIsFavorite()));
                Integer sizeList = (mApiService.getFavorites()).size();
                Log.i("Size List", sizeList.toString());


                Intent intent = new Intent(view.getContext(), FavoritesFragment.class);
                intent.putExtra("idFav", mApiService.getFavorites().indexOf(neighbour));


            }
        });

    }


}
