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
import java.util.Random;

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
            new ActivityTestRule<>(ListNeighbourActivity.class);
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
        mApiService.getFavorites().clear();
        mApiService.addFavorite(mNeighbours.get(2));

        // size of favorites list : 1
        int ITEMS_COUNT_FAV = DI.getNeighbourApiService().getFavorites().size();
        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(1));

        // Given : We remove the element at position 2
        int ITEMS_COUNT = DI.getNeighbourApiService().getNeighbours().size();

        // size neighbours list : 12
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(ITEMS_COUNT));

        // When perform a click on a delete icon (from main list)
        onView(ViewMatchers.withId(R.id.list_neighbours))
                .perform(RecyclerViewActions.actionOnItemAtPosition(2, new DeleteViewAction()));

        // Then : the number of elements is 12 - 1 (in both lists)
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
        onView(withId(R.id.details_neighbour)).check(matches(isDisplayed()));
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

        //When : Add 3 Neighbours at random in the Favorite List and check the List's size
        int id1 = new Random().nextInt(mApiService.getNeighbours().size());
        mApiService.addFavorite(mNeighbours.get(id1));
        int id2 = new Random().nextInt(mApiService.getNeighbours().size());
        while (id2 == id1) {id2 = new Random().nextInt(mApiService.getNeighbours().size());}
        mApiService.addFavorite(mNeighbours.get(id2));
        int id3 = new Random().nextInt(mApiService.getNeighbours().size());
        while (id3 == id1 || id3 == id2) {id3 = new Random().nextInt(mApiService.getNeighbours().size());}
        mApiService.addFavorite(mNeighbours.get(id3));

        //Then : The FavList have only 3 Neighbours
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
     * When we delete an item on the favorites list, the item is no more shown but but it still exists in the list of neighbours
     */
    @Test
    public void myFavoritesList_deleteAction_shouldRemoveItem() {
        int size = mApiService.getNeighbours().size();

        //Given : Add 2 Neighbours at random in the Favorite List
        int id1 = new Random().nextInt(mApiService.getNeighbours().size());
        mApiService.addFavorite(mNeighbours.get(id1));
        int id2 = new Random().nextInt(mApiService.getNeighbours().size());
        while (id2 == id1) {
            id2 = new Random().nextInt(mApiService.getNeighbours().size());
        }
        mApiService.addFavorite(mNeighbours.get(id2));


        //check the List's size (2)
        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(2));

        //Scroll to the favorite page in the container
        onView(withId(R.id.container)).perform(scrollRight());

        // When perform a click on a delete icon for the 2nd item
        onView(ViewMatchers.withId(R.id.list_favorites))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, new DeleteViewAction()));

        // Then : the number of element is 1 (in favorites list)
        onView(ViewMatchers.withId(R.id.list_favorites)).check(withItemCount(1));

        // Then : the number of elements has not changed (in main list)
        onView(ViewMatchers.withId(R.id.list_neighbours)).check(withItemCount(size));
    }
}