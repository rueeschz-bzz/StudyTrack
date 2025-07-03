package ch.zli.studytrack

import android.Manifest
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.*
import android.widget.Toast
import androidx.annotation.RequiresPermission
import androidx.appcompat.app.AppCompatActivity
import ch.zli.studytrack.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: SharedPreferences

    private var durationMillis: Long = 0
    private var elapsedMillis: Long = 0
    private var timerRunning = false
    private var animator: ValueAnimator? = null
    private lateinit var timerHandler: Handler
    private lateinit var timerRunnable: Runnable
    private var startTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefs = getSharedPreferences("StudyPrefs", MODE_PRIVATE)
        timerHandler = Handler(Looper.getMainLooper())

        setupPickers()

        binding.startStopButton.setOnClickListener {
            if (timerRunning) {
                stopTimer()
            } else {
                startTimer()
            }
        }

        binding.statsButton.setOnClickListener {
            startActivity(Intent(this, StatsActivity::class.java))
        }
    }

    private fun setupPickers() {
        binding.minutePicker.minValue = 0
        binding.minutePicker.maxValue = 59
        binding.secondPicker.minValue = 0
        binding.secondPicker.maxValue = 59
    }

    private fun startTimer() {
        val minutes = binding.minutePicker.value
        val seconds = binding.secondPicker.value
        durationMillis = (minutes * 60 + seconds) * 1000L

        if (durationMillis == 0L) {
            Toast.makeText(this, "Bitte Zeit auswählen", Toast.LENGTH_SHORT).show()
            return
        }

        startTime = SystemClock.elapsedRealtime()
        timerRunning = true
        binding.startStopButton.text = "Stop Timer"

        binding.progressBar.max = durationMillis.toInt()
        elapsedMillis = 0

        startColorAnimation()
        updateTimerDisplay(durationMillis)

        timerRunnable = object : Runnable {
            @RequiresPermission(Manifest.permission.VIBRATE)
            override fun run() {
                elapsedMillis = SystemClock.elapsedRealtime() - startTime
                val remaining = durationMillis - elapsedMillis
                updateTimerDisplay(remaining)
                binding.progressBar.progress = elapsedMillis.toInt()

                if (elapsedMillis >= durationMillis) {
                    endTimer()
                } else {
                    timerHandler.postDelayed(this, 100L)
                }
            }
        }
        timerHandler.post(timerRunnable)
    }

    private fun stopTimer() {
        timerRunning = false
        timerHandler.removeCallbacks(timerRunnable)
        animator?.cancel()
        binding.startStopButton.text = "Start Timer"
        Toast.makeText(this, "Timer gestoppt", Toast.LENGTH_SHORT).show()
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    private fun endTimer() {
        stopTimer()
        vibrate()
        saveTime()
        Toast.makeText(this, "Zeit abgelaufen!", Toast.LENGTH_SHORT).show()
    }

    private fun updateTimerDisplay(remainingMillis: Long) {
        val totalSeconds = remainingMillis / 1000
        val minutes = totalSeconds / 60
        val seconds = totalSeconds % 60
        binding.timerDisplay.text = String.format("%02d:%02d", minutes, seconds)
    }

    private fun startColorAnimation() {
        animator = ValueAnimator.ofObject(ArgbEvaluator(), Color.RED, Color.GREEN).apply {
            duration = durationMillis
            addUpdateListener {
                val color = it.animatedValue as Int
                binding.progressBar.progressDrawable.setTint(color)
            }
            start()
        }
    }

    private fun saveTime() {
        val today = System.currentTimeMillis() / (1000 * 60 * 60 * 24)
        val previous = prefs.getLong(today.toString(), 0L)
        prefs.edit().putLong(today.toString(), previous + durationMillis).apply()
    }

    @RequiresPermission(Manifest.permission.VIBRATE)
    private fun vibrate() {
        val vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
    }
}
