package com.taufik.adeptforms.ui.activity

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
import com.taufik.adeptforms.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setWindowNotificationBackground()

        initFirebaseAuth()

        setSignIn()

        setSignUp()

        setForgotPasswordActivity()
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

    private fun setSignIn() {
        binding.apply {
            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

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

                if (password.isEmpty() || password.length < 6) {
                    etPassword.error = "Password can't be blank & must be more than 6 characters"
                    etPassword.requestFocus()
                    return@setOnClickListener
                }

                loginUser(email, password)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    if (auth.currentUser!!.isEmailVerified){
                        Intent(this, MainActivity::class.java).also { intent ->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    } else {
                        confirmSignIn("Please verify your email before login")
                        Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    confirmSignIn("Email or password is invalid")
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun confirmSignIn(message: String) {
        AlertDialog.Builder(this).also { builder ->
            builder.setTitle("Sign in confirm!")
                .setMessage(message)
                .setPositiveButton("Close", null)
                .setCancelable(false)
                .create()
                .show()
        }
    }

    private fun setSignUp() {
        binding.apply {
            tvSignUp.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    private fun setForgotPasswordActivity() {
        binding.apply {
            tvForgotPassword.setOnClickListener {
                startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
            }
        }
    }
}