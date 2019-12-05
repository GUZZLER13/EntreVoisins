package com.openclassrooms.entrevoisins.neighbour_list;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.DummyNeighbourGenerator;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;
import com.openclassrooms.entrevoisins.ui.neighbour_list.ListNeighbourActivity;
import com.openclassrooms.entrevoisins.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.ViewPagerActions.scrollRight;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.openclassrooms.entrevoisins.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;


/**
 * Test class for list of neighbours
 */

@RunWith(AndroidJUnit4.class)
public class NeighboursListTest {

    @Rule
    public ActivityTestRule<ListNeighbourActivity> mActivityRule =
            new ActivityTestRule(ListNeighbourActivity.class);
    private List<Neighbour> mNeighbours = DummyNeighbourGenerator.DUMMY_NEIGHBOURS;
    private NeighbourApiService mApiService = DI.getNeighbourApiService();
    private List<Neighbour> mFavorites = mApiService.getFavorites();

    @Before
    public void setUp() {
        ListNeighbourActivity mActivity = mActivityRule.getActivity();
        assertThat(mActivity, notNullValue());
    }


    /**
     * When we delete an item, the item is no more shown in both lists
     */
    @Test
    public void myNeighboursList_deleteAction_shouldRemoveItem_inBothLists() {
        // Given : We add the element at position 2 to the favorites
        mApiService.addFavorite(mNeighbours.get(2));
        int ITEMS_COUNT_FAV = DI.getNeighbourApiService().getFavorites().size();
        // size favorites : 1
        // Given : We remove the element at position 2
        int ITEMS_COUNT = DI.getNeighbourApiService().getNeighbours().size();
        // size neighbours: 12
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));
        // When perform a click on a delete icon
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, new DeleteViewAction()));
        // Then : the number of elements is 12 - 1
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT - 1));
        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(ITEMS_COUNT_FAV - 1));

    }

    /**
     * We ensure that our recyclerView is displaying at least on item
     */
    @Test
    public void myNeighboursList_shouldNotBeEmpty() {
        // First scroll to the position that needs to be matched and click on it.
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .check(matches(hasMinimumChildCount(1)));
    }


    /**
     * When we click on a Neighbour, display the Activity Vue
     */
    @Test
    public void myNeighboursList_clickAction_shouldDisplayVue() {
        //Given : Click on the item
        onView(withId(R.id.list_neighbours)).
                perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        //Then : Go to the activityDetails
        onView(withId(R.id.ScrollView01)).check(matches(isDisplayed()));
    }


    /**
     * The FavList's size == number of Favorites Neighbours
     */
    @Test
    public void myFavoritesList_haveOnlyFavorites() {
        //Given : The Favorite List is empty
        onView(withId(R.id.container)).perform(scrollRight());
        mFavorites.clear();
        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(0));

        //When : Add 3 Neighbours in the Favorite List and check the List's size
        for (int i = 0; i < 3; i++) {
            mApiService.addFavorite(mNeighbours.get(i));
        }

        //Then : The FavList have 3 Neighbours
        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(3));
    }


    /**
     * When we click on a Neighbour, give the good Neighbour's name
     */
    @Test
    public void activityVue_loadNameText_shouldBeTheGoodOne() {
        //Given : A Neighbour for the test
        int POSITION = 0;
        Neighbour neighbour = mNeighbours.get(POSITION);
        //When : Click on it
        onView(withId(R.id.list_neighbours)).
                perform(RecyclerViewActions.actionOnItemAtPosition(POSITION, click()));
        //Then : Have the good name on ActivityVue
        onView(withId(R.id.Name)).check(matches(withText(neighbour.getName())));
    }


    /**
     * When we delete an item on the favorites list, the item is no more shown
     */
    @Test
    public void myFavoritesList_deleteAction_shouldRemoveItem() {
        //Given : Add 2 Neighbours in the Favorite List and check the List's size
        mApiService.addFavorite(mNeighbours.get(0));
        mApiService.addFavorite(mNeighbours.get(1));
        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(2));

        //Scroll to the favorite page in the container
        onView(withId(R.id.container)).perform(scrollRight());

        // When perform a click on a delete icon for the 2nd item
        onView(ViewMatchers.withId(R.id.list_favorites))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));
        // Then : the number of element is 1
        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(1));
    }
}