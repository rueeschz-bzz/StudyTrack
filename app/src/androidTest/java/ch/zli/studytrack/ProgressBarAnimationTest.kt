package ch.zli.studytrack

import android.animation.ValueAnimator
import android.graphics.Color
import androidx.test.core.app.ActivityScenario
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProgressBarAnimationTest {

    @Test
    fun testProgressBarAnimation_runsWithoutError() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        scenario.onActivity { activity ->
            val progressBar = activity.findViewById<android.widget.ProgressBar>(R.id.progressBar)

            val animator = ValueAnimator.ofArgb(Color.RED, Color.GREEN).apply {
                duration = 1000
                addUpdateListener {
                    val color = it.animatedValue as Int
                    progressBar.progressDrawable.setTint(color)
                }
            }

            animator.start()

            assertThat(animator.isRunning).isTrue()

            animator.cancel()
        }
    }
}
