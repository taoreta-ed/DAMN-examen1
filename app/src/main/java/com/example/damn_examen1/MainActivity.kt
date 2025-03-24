package com.example.damn_examen1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.damn_examen1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Aplicar tema antes de cualquier vista
        applySelectedTheme()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupNavigation()
        setupSettingsButton()
    }

    override fun onResume() {
        super.onResume()
        if (ThemeActivity.themeChanged) {
            ThemeActivity.themeChanged = false
            recreate()
        }
    }

    private fun applySelectedTheme() {
        when (ThemeActivity.selectedTheme) {
            "IPN" -> setTheme(R.style.Base_Theme_IPNTheme)
            "ESCOM" -> setTheme(R.style.Base_Theme_ESCOMTheme)
        }
    }

    private fun setupNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = navHostFragment?.findNavController()

        if (navController != null) {
            binding.bottomNavigation.setupWithNavController(navController)
        }
    }

    private fun setupSettingsButton() {
        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, ThemeActivity::class.java))
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
    }
}