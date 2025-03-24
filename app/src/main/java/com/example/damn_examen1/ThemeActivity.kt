package com.example.damn_examen1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.damn_examen1.databinding.ActivityThemeBinding

class ThemeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThemeBinding
    private var selectedTheme: String = "IPN" // Tema por defecto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura los listeners de las tarjetas
        binding.ipnThemeCard.setOnClickListener {
            selectedTheme = "IPN"
            highlightSelectedTheme()
        }

        binding.escomThemeCard.setOnClickListener {
            selectedTheme = "ESCOM"
            highlightSelectedTheme()
        }

        // Configura el botón de continuar
        binding.continueButton.setOnClickListener {
            applySelectedTheme()
            finish() // Regresa a la actividad anterior
        }

        highlightSelectedTheme() // Resalta el tema actual al iniciar
    }

    private fun highlightSelectedTheme() {
        // Restablece todos los estilos primero
        binding.ipnThemeCard.cardElevation = 4f
        binding.escomThemeCard.cardElevation = 4f

        // Resalta el seleccionado
        when (selectedTheme) {
            "IPN" -> {
                binding.ipnThemeCard.cardElevation = 12f
                binding.ipnThemeCard.alpha = 1f
                binding.escomThemeCard.alpha = 0.7f
            }
            "ESCOM" -> {
                binding.escomThemeCard.cardElevation = 12f
                binding.escomThemeCard.alpha = 1f
                binding.ipnThemeCard.alpha = 0.7f
            }
        }
    }

    private fun applySelectedTheme() {
        when (selectedTheme) {
            "IPN" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                setTheme(R.style.Base_Theme_IPNTheme)
            }
            "ESCOM" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO) // Forzamos modo claro
                setTheme(R.style.Base_Theme_ESCOMTheme)
            }
        }
        // Reiniciar actividad para aplicar cambios
        recreate()

        // Guarda la preferencia (opcional)
        getSharedPreferences("AppPrefs", MODE_PRIVATE).edit()
            .putString("selected_theme", selectedTheme)
            .apply()
    }
}