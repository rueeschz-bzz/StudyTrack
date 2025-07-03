package ch.zli.studytrack

import android.widget.NumberPicker
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import org.hamcrest.Matcher

fun setNumber(value: Int): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<android.view.View> {
            return isAssignableFrom(NumberPicker::class.java)
        }

        override fun getDescription(): String {
            return "Set number on NumberPicker"
        }

        override fun perform(uiController: UiController, view: android.view.View) {
            val picker = view as NumberPicker
            picker.value = value
        }
    }
}
