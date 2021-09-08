package com.taufik.adeptforms.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.adeptforms.databinding.ActivityForgotPasswordBinding
import kotlinx.android.synthetic.main.activity_login.*

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSignIn()

        setSignUp()
    }

    private fun setSignIn() {
        binding.apply {
            tvSignIn.setOnClickListener {
                startActivity(Intent(this@ForgotPasswordActivity, LoginActivity::class.java))
            }
        }
    }

    private fun setSignUp() {
        binding.apply {
            tvSignUp.setOnClickListener {
                startActivity(Intent(this@ForgotPasswordActivity, RegisterActivity::class.java))
            }
        }
    }
}