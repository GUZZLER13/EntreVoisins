package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.DeleteFavoriteEvent;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.ui.neighbour_details.NeighbourDetailsActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//lien entre la vue RecyclerView et le contrôleur (fragment)
public class MyNeighbourRecyclerViewAdapter extends RecyclerView.Adapter<MyNeighbourRecyclerViewAdapter.ViewHolder> {

    private final List<Neighbour> mNeighbours;
    private boolean isFavoriteFragment;


    MyNeighbourRecyclerViewAdapter(List<Neighbour> items, boolean isFavoriteFragment) {

        /* Ajout du booléen isFavoriteFragment en paramètre qui sert à différencier les 2 listes de voisins */
        mNeighbours = items;
        this.isFavoriteFragment = isFavoriteFragment;
    }


    @NonNull
    @Override
    // Création du viewHolder pour chaque ligne de la recyclerview
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_neighbour, parent, false);
        return new ViewHolder(view);
    }

    @Override
    //Mise a jour apparence de chaque ligne du recyclerview
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Neighbour neighbour = mNeighbours.get(position);

        holder.mNeighbourName.setText(neighbour.getName());

        //Chargement image avatar
        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);



        /* envoi de l'id et du booléen vers l'activité de détails */
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(holder.itemView.getContext(), NeighbourDetailsActivity.class);
            intent.putExtra("id", mNeighbours.indexOf(neighbour));
            intent.putExtra("frag", isFavoriteFragment);
            holder.itemView.getContext().startActivity(intent);
        });


        /* méthode de suppression des voisins en utilisant les events */
            holder.mDeleteButton.setOnClickListener(v -> {
                if (isFavoriteFragment) {
                    EventBus.getDefault().post(new DeleteFavoriteEvent(neighbour));

                } else {
                    EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour));


                    /* Si on supprime le voisin dans la liste principale, il se supprime aussi de la liste des favoris */
                    EventBus.getDefault().post(new DeleteFavoriteEvent(neighbour));
                }


            /* Si la liste de favoris est vide >>> start ListNeighbourActivity */
            if (getItemCount() == 0 && isFavoriteFragment) {
                Intent intent = new Intent(holder.itemView.getContext(), ListNeighbourActivity.class);
                holder.itemView.getContext().startActivity(intent);
                Toast toast = Toast.makeText(holder.itemView.getContext(), "You have no favorites", Toast.LENGTH_LONG);
                toast.show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return mNeighbours.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_list_avatar)
        public ImageView mNeighbourAvatar;
        @BindView(R.id.item_list_name)
        public TextView mNeighbourName;
        @BindView(R.id.item_list_delete_button)
        public ImageButton mDeleteButton;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
