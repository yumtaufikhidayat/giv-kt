package com.taufik.adeptforms.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.taufik.adeptforms.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSignIn()

        setForgotPassword()
    }

    private fun setSignIn() {
        binding.apply {
            tvSignIn.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }
        }
    }

    private fun setForgotPassword() {
        binding.apply {
            tvForgotPassword.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, ForgotPasswordActivity::class.java))
            }
        }
    }
}