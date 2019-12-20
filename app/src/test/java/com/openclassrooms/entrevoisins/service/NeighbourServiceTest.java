package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;
import java.util.Objects;
import java.util.Random;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour mApiService
 */

@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService mApiService = DI.getNeighbourApiService();
    private List<Neighbour> neighbours = mApiService.getNeighbours();


    @Before
    public void setup() {
        mApiService = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {

        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;

        //La liste de voisins demandée est la même que celle espérée
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(expectedNeighbours.toArray())));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = mApiService.getNeighbours().get(new Random().nextInt(mApiService.getNeighbours().size()));
        mApiService.deleteNeighbour(neighbourToDelete);
        neighbourToDelete.setIsFavorite(true);

        // Le voisin supprimé depuis la liste pricipale n'est plus dans aucune liste
        assertFalse(mApiService.getNeighbours().contains(neighbourToDelete));
        assertFalse(mApiService.getFavorites().contains(neighbourToDelete));
    }


    @Test
    public void getFavoritesWithSuccess() {

        mApiService.deleteAllFavorites();
        Neighbour neighbour = neighbours.get(new Random().nextInt(mApiService.getNeighbours().size()));

        neighbour.setIsFavorite(true);


        //La liste des favoris ne contient que le voisin ajouté en favoris

        assertTrue(mApiService.getFavorites().contains(neighbour));
    }

    @Test
    public void addFavNeighbourWithSuccess() {
        Neighbour neighbour = neighbours.get(new Random().nextInt(mApiService.getNeighbours().size()));
        mApiService.addFavorite(neighbour);

        //La liste des favoris contient bien le voisin ajouté dans la liste des favoris
        assertTrue(mApiService.getFavorites().contains(neighbour));
    }

    @Test
    public void deleteFavNeighbourWithSuccess() {
        List<Neighbour> neighbours = mApiService.getNeighbours();
        List<Neighbour> list = mApiService.getFavorites();

        //Choisir un voisin au hasard dans la liste principale
        Neighbour neighbourToDelete = neighbours.get(new Random().nextInt(mApiService.getNeighbours().size()));

        //Ajouter ce voisin dans la liste des favoris (vierge)
        list.clear();
        list.add(neighbourToDelete);

        // Supprimer ce voisin des favoris
        mApiService.deleteFavorite(neighbourToDelete);

        // Le voisin supprimé depuis les favoris ne doit plus être dans la liste des favoris ...
        assertFalse(mApiService.getFavorites().contains(neighbourToDelete));

        // ... mais doit rester dans la liste pricipale
        assertTrue(neighbours.contains(neighbourToDelete));
    }
}
