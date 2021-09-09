package com.taufik.adeptforms.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.taufik.adeptforms.R
import com.taufik.adeptforms.databinding.ActivitySplashscreenBinding

class SplashscreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashscreenBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashscreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initFirebaseAuth()

        setWindowNotificationBackground()

        setSplashScreen()
    }

    private fun initFirebaseAuth() {
        auth = FirebaseAuth.getInstance()
    }

    /**
     * Method to set notification bar
     */
    private fun setWindowNotificationBackground() {
        val windowNotificationBackground = this.window
        windowNotificationBackground.statusBarColor = ContextCompat.getColor(this, R.color.colorStatusBar)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            windowNotificationBackground.setDecorFitsSystemWindows(true)
        }
    }

    private fun setSplashScreen() {
        handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            if (auth.currentUser == null) {
                Intent(this, LoginActivity::class.java).also { intent ->
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            } else if (auth.currentUser!!.isEmailVerified){
                Intent(this, MainActivity::class.java).also { intent ->
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }, 1000)
    }
}