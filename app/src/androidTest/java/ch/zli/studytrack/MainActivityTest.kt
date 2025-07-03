package ch.zli.studytrack

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun timer_startStop_behavesCorrectly() {
        onView(withId(R.id.minutePicker)).perform(setNumber(0))
        onView(withId(R.id.secondPicker)).perform(setNumber(10))

        onView(withId(R.id.startStopButton)).perform(click())
        onView(withText("Stop Timer")).check(matches(isDisplayed()))

        onView(withId(R.id.startStopButton)).perform(click())
        onView(withText("Start Timer")).check(matches(isDisplayed()))
    }

    @Test
    fun navigateToStatsActivity() {
        onView(withId(R.id.statsButton)).perform(click())
        onView(withId(R.id.backButton)).check(matches(isDisplayed()))
    }
}
