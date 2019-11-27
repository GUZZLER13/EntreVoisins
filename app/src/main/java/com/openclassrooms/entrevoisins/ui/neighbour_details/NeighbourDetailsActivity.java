package com.openclassrooms.entrevoisins.ui.neighbour_details;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.squareup.picasso.Picasso;


public class NeighbourDetailsActivity extends AppCompatActivity {

    private FloatingActionButton mFloat;
    private NeighbourApiService mApiService = DI.getNeighbourApiService();
    private Neighbour neighbour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_neighbour);
        int id = getIntent().getIntExtra("id", -1);
        int fragment = getIntent().getIntExtra("frag", -1);

        if (fragment == 1) {
            neighbour = DI.getNeighbourApiService().getNeighbours().get(id);
            Log.i("Favorite is", String.valueOf(neighbour.getIsFavorite()));
        } else {
            neighbour = DI.getNeighbourApiService().getFavorites().get(id);
            Log.i("Favorite is", String.valueOf(neighbour.getIsFavorite()));
        }

        mFloat = findViewById(R.id.Favorite);
        if (mApiService.getFavorites().contains(neighbour)) {
            mFloat.setImageResource(R.drawable.ic_star_yellow_24dp);
        } else {
            mFloat.setImageResource(R.drawable.ic_star_border_yellow_24dp);
        }
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

        mFloat.setOnClickListener(view -> {
            if (!mApiService.getFavorites().contains(neighbour)) {
                neighbour.setIsFavorite(true);
                mFloat.setImageResource(R.drawable.ic_star_yellow_24dp);
                mApiService.addFavorite(neighbour);
                Snackbar.make(view, neighbour.getName() + " added to favorites", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            } else {
                neighbour.setIsFavorite(false);
                mFloat.setImageResource(R.drawable.ic_star_border_yellow_24dp);
                mApiService.deleteFavorite(neighbour);
                Snackbar.make(view, neighbour.getName() + " removed to favorites", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (mApiService.getFavorites().size() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(), "You have no favorites", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            Log.i("Name", neighbour.getName());
            Log.i("Favorite", String.valueOf(neighbour.getIsFavorite()));
            Integer sizeList = (mApiService.getFavorites()).size();
            Log.i("Size List of favorites", sizeList.toString());
        });
    }
}
