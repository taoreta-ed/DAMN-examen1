package com.example.damn_examen1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.damn_examen1.databinding.ActivityThemeBinding

class ThemeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThemeBinding

    companion object {
        var selectedTheme: String = "IPN"
        var themeChanged: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        applyThemeOnCreate()
        super.onCreate(savedInstanceState)
        binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        highlightSelectedTheme()
    }

    private fun applyThemeOnCreate() {
        when (selectedTheme) {
            "IPN" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                setTheme(R.style.Base_Theme_IPNTheme)
            }
            "ESCOM" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                setTheme(R.style.Base_Theme_ESCOMTheme)
            }
        }
    }

    private fun setupUI() {
        binding.ipnThemeCard.setOnClickListener {
            selectedTheme = "IPN"
            highlightSelectedTheme()
        }

        binding.escomThemeCard.setOnClickListener {
            selectedTheme = "ESCOM"
            highlightSelectedTheme()
        }

        binding.continueButton.setOnClickListener {
            themeChanged = true
            applySelectedTheme()
            finish() // Solo finaliza la actividad, no recrees
        }
    }

    private fun highlightSelectedTheme() {
        binding.ipnThemeCard.cardElevation = if (selectedTheme == "IPN") 12f else 4f
        binding.escomThemeCard.cardElevation = if (selectedTheme == "ESCOM") 12f else 4f

        binding.ipnThemeCard.alpha = if (selectedTheme == "IPN") 1f else 0.7f
        binding.escomThemeCard.alpha = if (selectedTheme == "ESCOM") 1f else 0.7f
    }

    private fun applySelectedTheme() {
        getSharedPreferences("AppPrefs", MODE_PRIVATE).edit()
            .putString("selected_theme", selectedTheme)
            .apply()
    }
}