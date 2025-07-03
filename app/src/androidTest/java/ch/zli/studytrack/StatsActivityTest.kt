package ch.zli.studytrack

import androidx.test.espresso.Espresso.*
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class StatsActivityTest {

    @get:Rule
    val rule = ActivityScenarioRule(StatsActivity::class.java)

    @Test
    fun showTodayStats_displaysCorrectly() {
        onView(withId(R.id.toggleView)).perform(click()) // sicherstellen dass auf „Heute“ steht
        onView(withId(R.id.todayStats)).check(matches(isDisplayed()))
    }

    @Test
    fun showWeeklyStats_displaysCorrectly() {
        onView(withId(R.id.toggleView)).perform(click()) // auf Wochenübersicht schalten
        onView(withId(R.id.barChart)).check(matches(isDisplayed()))
    }

    @Test
    fun backButton_returnsToMainActivity() {
        onView(withId(R.id.backButton)).perform(click())
        onView(withId(R.id.startStopButton)).check(matches(isDisplayed()))
    }
}
