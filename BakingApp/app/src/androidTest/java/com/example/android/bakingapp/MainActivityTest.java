package com.example.android.bakingapp;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void TestRecipeIngredient() {
        // Test ingredient at position 1.
        String ingredient = "+  350.0 G Bittersweet chocolate (60-70% cacao)" + System.lineSeparator()
                + "+  226.0 G unsalted butter" + System.lineSeparator()
                + "+  300.0 G granulated sugar" + System.lineSeparator()
                + "+  100.0 G light brown sugar" + System.lineSeparator()
                + "+  5.0 UNIT large eggs" + System.lineSeparator()
                + "+  1.0 TBLSP vanilla extract" + System.lineSeparator()
                + "+  140.0 G all purpose flour" + System.lineSeparator()
                + "+  40.0 G cocoa powder" + System.lineSeparator()
                + "+  1.5 TSP salt" + System.lineSeparator()
                + "+  350.0 G semisweet chocolate chips";

        // Click item at position 1
        onView(withId(R.id.recyclerview_recipe)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));
        onView(withId(R.id.tv_recipe_ingredient)).check(matches(withText(ingredient)));

    }


}
