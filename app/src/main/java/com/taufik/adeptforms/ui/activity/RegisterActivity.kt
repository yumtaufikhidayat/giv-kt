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
import com.taufik.adeptforms.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setWindowNotificationBackground()

        initFirebaseAuth()

        setSignUp()

        setSignIn()

        setForgotPassword()
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

    private fun setSignUp() {
        binding.apply {
            btnSignUp.setOnClickListener {
                val fullName = etFullName.text.toString().trim()
                val username = etUsername.text.toString().trim()
                val email = etEmail.text.toString().trim()
                val password = etPassword.text.toString().trim()

                if (fullName.isEmpty()) {
                    etFullName.error = "Fullname can't be blank"
                    etFullName.requestFocus()
                    return@setOnClickListener
                }

                if (username.isEmpty()) {
                    etUsername.error = "Username can't be blank"
                    etUsername.requestFocus()
                    return@setOnClickListener
                }

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

                registerUser(email, password)
            }
        }
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                        if (it.isSuccessful) {
                            confirmSignUp()
                        } else {
                            Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun confirmSignUp() {
        AlertDialog.Builder(this).also { builder ->
            builder.setTitle("Sign up is successful!")
                .setMessage("Sign up is successful. Please check your email to verify.")
                .setPositiveButton("Sign In") { _: DialogInterface?, _: Int ->
                    Intent(this@RegisterActivity, LoginActivity::class.java).also {
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
                Intent(this@RegisterActivity, LoginActivity::class.java).also { intent ->
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }

    private fun setForgotPassword() {
        binding.apply {
            tvForgotPassword.setOnClickListener {
                Intent(this@RegisterActivity, ForgotPasswordActivity::class.java).also { intent ->
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
            }
        }
    }
}