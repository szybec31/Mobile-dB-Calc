package com.example.db_calc

import android.os.Bundle
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.db_calc.databinding.ActivityInfoBinding

class Info_Activity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)

        setContentView(R.layout.activity_info)

        val tableLayout = findViewById<TableLayout>(R.id.table)

        // Dodanie nagłówka
        val headerRow = TableRow(this)

        val border = GradientDrawable()
        border.setStroke(2, Color.BLACK)
        border.cornerRadius = 8f  // Zaokrąglone rogi
        headerRow.background = border
        // Lista danych (każdy wiersz zawiera 4 wartości)
        val data = listOf(
            listOf("1 W", "0 dBW", "1 V", "0 dBV"),
            listOf("2 W", "3 dBW", "2 V", "6 dBV"),
            listOf("4 W", "6 dBW", "4 V", "12 dBV"),
            listOf("8 W", "9 dBW", "8 V", "18 dBV"),
            listOf("10 W", "10 dBW", "10 V", "20 dBV"),
            listOf("1000 W", "30 dBW", "1000 V", "60 dBV")
        )

        // Dodawanie wierszy dynamicznie
        for (rowValues in data) {
            val row = TableRow(this)
            rowValues.forEach { value ->
                row.addView(createTextView(value))
            }
            tableLayout.addView(row)
        }
    }

    // Funkcja do tworzenia komórek tabeli
    private fun createTextView(text: String): TextView {
        val textView = TextView(this)
        textView.text = text
        textView.setPadding(16, 8, 16, 8)
        textView.gravity = Gravity.CENTER
        textView.height = 100
        textView.textSize = 20f
        textView.setTextColor(Color.WHITE)

        val border = GradientDrawable()
        border.setStroke(4, Color.BLACK)  // Czarna ramka 2dp
        border.setColor(Color.GRAY)  // szare tło
        border.cornerRadius = 1f  // Zaokrąglone rogi

        textView.background = border

        return textView
    }
    }