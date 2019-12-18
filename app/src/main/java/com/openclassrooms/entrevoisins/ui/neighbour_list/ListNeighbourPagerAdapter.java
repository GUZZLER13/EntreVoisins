package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class ListNeighbourPagerAdapter extends FragmentPagerAdapter {

    ListNeighbourPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * getItem is called to instantiate the fragment for the given page.
     *
     * @param position
     * @return
     */

    // nouvelle instance d'un fragment selon onglet sélectionné
    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return FavoritesFragment.newInstance();
        } else {
            return NeighbourFragment.newInstance();
        }
    }

    /**
     * get the number of pages
     *
     * @return
     */

    // Prise en compte des 2 onglets (My neighbours et favorites)
    @Override
    public int getCount() {
        return 2;
    }
}