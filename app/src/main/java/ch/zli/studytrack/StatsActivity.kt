package ch.zli.studytrack

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import ch.zli.studytrack.databinding.ActivityStatsBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.util.*

class StatsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toggleView.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showWeeklyStats()
            } else {
                showTodayStats()
            }
        }

        binding.toggleView.isChecked = false
        showTodayStats()

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun showTodayStats() {
        val prefs = getSharedPreferences("StudyPrefs", MODE_PRIVATE)
        val today = System.currentTimeMillis() / (1000 * 60 * 60 * 24)
        val minutes = prefs.getLong(today.toString(), 0L) / 60000
        binding.todayStats.text = "Heute: $minutes Minuten"
        binding.todayStats.visibility = View.VISIBLE
        binding.barChart.visibility = View.GONE
    }

    private fun showWeeklyStats() {
        val prefs = getSharedPreferences("StudyPrefs", MODE_PRIVATE)
        val entries = ArrayList<BarEntry>()
        val labels = listOf("Mo", "Di", "Mi", "Do", "Fr", "Sa", "So")

        val currentTimeMillis = System.currentTimeMillis()
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = currentTimeMillis

        // Android: Sonntag = 1, Montag = 2, ..., Samstag = 7
        val todayIsoDay = calendar.get(Calendar.DAY_OF_WEEK)
        val offsetToMonday = if (todayIsoDay == 1) -6 else 2 - todayIsoDay
        calendar.add(Calendar.DAY_OF_MONTH, offsetToMonday)

        val mondayDay = calendar.timeInMillis / (1000 * 60 * 60 * 24)

        for (i in 0..6) {
            val day = mondayDay + i
            val minutes = prefs.getLong(day.toString(), 0L) / 60000
            entries.add(BarEntry(i.toFloat(), minutes.toFloat()))
        }

        val dataSet = BarDataSet(entries, "Lernzeit (Minuten)").apply {
            color = getColor(R.color.teal_700)
            valueTextSize = 12f
        }

        val barData = BarData(dataSet).apply {
            barWidth = 0.9f
        }

        binding.barChart.apply {
            data = barData
            setFitBars(true)
            description.isEnabled = false
            axisRight.isEnabled = false
            xAxis.apply {
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter = IndexAxisValueFormatter(labels)
                granularity = 1f
                setDrawGridLines(false)
                textSize = 12f
            }
            animateY(800)
            invalidate()
        }

        binding.todayStats.visibility = View.GONE
        binding.barChart.visibility = View.VISIBLE
    }
}
