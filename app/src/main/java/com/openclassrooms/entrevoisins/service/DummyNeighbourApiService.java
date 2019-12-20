package com.openclassrooms.entrevoisins.service;

import com.openclassrooms.entrevoisins.model.Neighbour;

import java.util.ArrayList;
import java.util.List;

/**
 * Dummy mock for the Api
 */
public class DummyNeighbourApiService implements NeighbourApiService {

    private List<Neighbour> neighbours = DummyNeighbourGenerator.generateNeighbours();
//    private List<Neighbour> favorites = new ArrayList<>();


    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getNeighbours() {
        return neighbours;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteNeighbour(Neighbour neighbour) {
        neighbours.remove(neighbour);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Neighbour> getFavorites() {
        List<Neighbour> res = new ArrayList<>();
        for (Neighbour m : neighbours) {
            if (m.getIsFavorite()) {
                res.add(m);
            }
        }
        return res;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addFavorite(Neighbour neighbour) {
        neighbour.setIsFavorite(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteFavorite(Neighbour neighbour) {
        neighbour.setIsFavorite(false);
    }



    @Override
    public void deleteAllFavorites() {
        for (Neighbour m : neighbours) {
            if (m.getIsFavorite()) {
                m.setIsFavorite(false);
            }
        }
    }
}
