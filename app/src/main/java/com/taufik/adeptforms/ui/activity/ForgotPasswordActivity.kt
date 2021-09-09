package com.taufik.adeptforms.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.taufik.adeptforms.R
import com.taufik.adeptforms.databinding.ActivityForgotPasswordBinding
import kotlinx.android.synthetic.main.activity_login.*

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setWindowNotificationBackground()

        setSignIn()

        setSignUp()
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

    private fun setSignIn() {
        binding.apply {
            tvSignIn.setOnClickListener {
                Intent(this@ForgotPasswordActivity, LoginActivity::class.java).also { intent ->
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setSignUp() {
        binding.apply {
            tvSignUp.setOnClickListener {
                Intent(this@ForgotPasswordActivity, RegisterActivity::class.java).also { intent ->
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }
}