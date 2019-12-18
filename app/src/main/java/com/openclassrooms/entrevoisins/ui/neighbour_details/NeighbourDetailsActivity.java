package com.openclassrooms.entrevoisins.ui.neighbour_details;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class NeighbourDetailsActivity extends AppCompatActivity {

    private FloatingActionButton mFloat;
    private NeighbourApiService mApiService = DI.getNeighbourApiService();
    private Neighbour neighbour;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_neighbour);

        // Récupération Intent

        int id = getIntent().getIntExtra("id", -1);
        boolean isFavoriteFragment = getIntent().getBooleanExtra("frag", false);

        //Chargement liste voisins différente si fragment favoris ou non

        if (!isFavoriteFragment) {
            neighbour = DI.getNeighbourApiService().getNeighbours().get(id);
        } else {
            neighbour = DI.getNeighbourApiService().getFavorites().get(id);
        }

        // Traitement icone favori

        mFloat = findViewById(R.id.Favorite);
        if (mApiService.getFavorites().contains(neighbour)) {
            mFloat.setImageResource(R.drawable.ic_star_yellow_24dp);
        } else {
            mFloat.setImageResource(R.drawable.ic_star_border_yellow_24dp);
        }


        String name = neighbour.getName();
        TextView textView2 = findViewById(R.id.Name);
        TextView textView3 = findViewById(R.id.Mail);
        ImageView imageView = findViewById(R.id.Avatar);
        textView2.setText(name);
        textView3.setText(String.format("    %s@gmail.com", name.toLowerCase()));

        // utilisation de Picasso (librairie de chargement d'images Asynchrone)

        Picasso.get().load(neighbour.getAvatarUrl().replace("http://i.pravatar.cc/150?u=", "http://i.pravatar.cc/300?u=")).centerCrop().resize(220, 160).into(imageView);

        // Traitement Toolbar

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(neighbour.getName());
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        // Traitement voisin en favori ou non + icone favori ET affichage toast + snackbar

        mFloat.setOnClickListener(view -> {
            if (!neighbour.getIsFavorite()) {
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
                    toast.setGravity(1, 1, 1);
                    toast.show();
                }
            }
        });
    }
}
