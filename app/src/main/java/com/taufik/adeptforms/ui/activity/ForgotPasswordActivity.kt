package com.taufik.adeptforms.ui.activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth
import com.taufik.adeptforms.R
import com.taufik.adeptforms.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setWindowNotificationBackground()

        initFirebaseAuth()

        setResetPassword()

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

    private fun initFirebaseAuth() {
        auth = FirebaseAuth.getInstance()
    }

    private fun setResetPassword() {
        binding.apply {
            btnResetPassword.setOnClickListener {

                val email = etEmail.text.toString().trim()

                if (email.isEmpty()) {
                    etEmail.error = "Email can't be blank"
                    etEmail.requestFocus()
                    return@setOnClickListener
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    etEmail.error = "Insert valid email"
                    etEmail.requestFocus()
                    return@setOnClickListener
                }

                resetPassword(email)
            }
        }
    }

    private fun resetPassword(email: String) {
        auth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    confirmResetPassword()
                } else {
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun confirmResetPassword() {
        AlertDialog.Builder(this).also { builder ->
            builder.setTitle("Reset email confirm!")
                .setMessage("Url to reset password has sent. Check your email to reset password.")
                .setPositiveButton("Sign In") { _: DialogInterface?, _: Int ->
                    Intent(this, LoginActivity::class.java).also {
                        it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(it)
                    }
                }
                .setCancelable(false)
                .create()
                .show()
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