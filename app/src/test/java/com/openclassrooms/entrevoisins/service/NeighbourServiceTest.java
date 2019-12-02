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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test on Neighbour mApiService
 */

@RunWith(JUnit4.class)
public class NeighbourServiceTest {

    private NeighbourApiService mApiService;

    @Before
    public void setup() {
        mApiService = DI.getNewInstanceApiService();
    }

    @Test
    public void getNeighboursWithSuccess() {
        List<Neighbour> neighbours = mApiService.getNeighbours();
        List<Neighbour> expectedNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
        assertThat(neighbours, IsIterableContainingInAnyOrder.containsInAnyOrder(Objects.requireNonNull(expectedNeighbours.toArray())));
    }

    @Test
    public void deleteNeighbourWithSuccess() {
        Neighbour neighbourToDelete = mApiService.getNeighbours().get(0);
        mApiService.deleteNeighbour(neighbourToDelete);
        assertFalse(mApiService.getNeighbours().contains(neighbourToDelete));
    }

    @Test
    public void getFavoritesWithSuccess() {
        mApiService.getFavorites().clear();
        Neighbour neighbour = new Neighbour(1, "test", "test");
        mApiService.getFavorites().add(neighbour);
        assertTrue(mApiService.getFavorites().contains(neighbour));
    }

    @Test
    public void addFavNeighbourWithSuccess() {
        Neighbour neighbour = new Neighbour(1, "test", "test");
        mApiService.addFavorite(neighbour);
        assertTrue(mApiService.getFavorites().contains(neighbour));
    }

    @Test
    public void deleteFavNeighbourWithSuccess() {
        Neighbour neighbourToDelete = mApiService.getNeighbours().get(0);
        List<Neighbour> list = mApiService.getFavorites();
        list.clear();
        list.add(neighbourToDelete);
        assertEquals(mApiService.getNeighbours().get(0), neighbourToDelete);
        mApiService.deleteNeighbour(neighbourToDelete);
        assertFalse(mApiService.getNeighbours().contains(neighbourToDelete));
    }
}
