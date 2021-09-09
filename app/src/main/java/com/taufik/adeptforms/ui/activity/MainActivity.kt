package com.taufik.adeptforms.ui.activity

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.taufik.adeptforms.R
import com.taufik.adeptforms.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setWindowNotificationBackground()

        setNavHost()
    }

    /**
     * Method to set notification bar
     */
    private fun setWindowNotificationBackground() {
        val windowNotificationBackground = this.window
        windowNotificationBackground.statusBarColor = ContextCompat.getColor(this, R.color.colorYellow1)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowNotificationBackground.setDecorFitsSystemWindows(true)
        }
    }

    private fun setNavHost() {

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHostFragment.findNavController()

        binding.apply {
            navBottom.menu.getItem(2).isEnabled = false
            navBottom.setupWithNavController(navController)
        }
    }
}