package com.setiaki.moviecatalogue

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.setiaki.moviecatalogue.ui.home.HomeActivity
import com.setiaki.moviecatalogue.util.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class InstrumentationTest {

    // Test harus dilakukan dengan koneksi internet

    @get:Rule(order = 0)
    var hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var homeActivityRule = ActivityScenarioRule(HomeActivity::class.java)

    companion object {
        @BeforeClass
        @JvmStatic
        fun clearDatabase() {
            InstrumentationRegistry.getInstrumentation().targetContext.deleteDatabase("app_database")
        }
    }

    @Before
    fun setUp() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testALoadTopRatedMovies() {
        onView(withId(R.id.rv_catalogue)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_catalogue)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                5
            )
        )
    }

    @Test
    fun testBLoadTopRatedTvShows() {
        onView(withText("TV Show")).perform(click())
        onView(withId(R.id.rv_catalogue)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_catalogue)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                5
            )
        )
    }

    @Test
    fun testCLoadTopRatedMovieDetail() {
        onView(withId(R.id.rv_catalogue)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genres)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_vote_average)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).check(matches(isDisplayed()))
    }

    @Test
    fun testDLoadTopRatedTvShowDetail() {
        onView(withText("TV Show")).perform(click())
        onView(withId(R.id.rv_catalogue)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genres)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_vote_average)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).check(matches(isDisplayed()))
    }

    @Test
    fun testELoadFavoritedMovies() {
        onView(withId(R.id.rv_catalogue)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.fab_favorite)).perform(click())
        onView(withId(R.id.activity_detail)).perform(pressBack())
        onView(withId(R.id.menu_favorite)).perform(click())

        onView(withId(R.id.rv_catalogue)).apply {
            check(matches(isDisplayed()))
            perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    0
                )
            )
        }
    }

    @Test
    fun testFLoadFavoritedTvShows() {
        onView(withText("TV Show")).perform(click())
        onView(withId(R.id.rv_catalogue)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.fab_favorite)).perform(click())
        onView(withId(R.id.activity_detail)).perform(pressBack())
        onView(withId(R.id.menu_favorite)).perform(click())

        onView(withText("TV Show")).perform(click())
        onView(withId(R.id.rv_catalogue)).apply {
            check(matches(isDisplayed()))
            perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                    0
                )
            )
        }
    }

    @Test
    fun testGLoadFavoritedMovieDetail() {
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withId(R.id.rv_catalogue)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genres)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_vote_average)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).check(matches(isDisplayed()))
    }

    @Test
    fun testHLoadFavoritedTvShowDetail() {
        onView(withId(R.id.menu_favorite)).perform(click())
        onView(withText("TV Show")).perform(click())
        onView(withId(R.id.rv_catalogue)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.tv_title)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_release_date)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_genres)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_vote_average)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.fab_favorite)).check(matches(isDisplayed()))
    }


}