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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.taufik.adeptforms.R
import com.taufik.adeptforms.databinding.ActivityRegisterBinding
import java.util.*

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

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
                val companyName = etCompanyName.text.toString().trim()
                val jobPosition = etJobPosition.text.toString().trim()
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

                if (companyName.isEmpty()) {
                    etCompanyName.error = "Company name can't be blank"
                    etCompanyName.requestFocus()
                    return@setOnClickListener
                }

                if (jobPosition.isEmpty()) {
                    etJobPosition.error = "Job position can't be blank"
                    etJobPosition.requestFocus()
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

                registerUser(
                    fullName,
                    companyName,
                    jobPosition,
                    email,
                    password
                )
            }
        }
    }

    private fun registerUser(
        fullName: String,
        companyName: String,
        jobPosition: String,
        email: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    var userId = ""

                    if (firebaseUser != null) {
                        userId = firebaseUser.uid
                    }

                    reference = FirebaseDatabase.getInstance().reference.child("Users").child(userId)

                    val mHashMap = HashMap<String, Any>()
                    mHashMap["id"] = userId
                    mHashMap["imageUrl"] = "https://toppng.com/uploads/preview/instagram-default-profile-picture-11562973083brycehrmyv.png"
                    mHashMap["fullName"] = fullName
                    mHashMap["companyName"] = companyName
                    mHashMap["jobPosition"] = jobPosition
                    mHashMap["email"] = email
                    mHashMap["password"] = password

                    reference.setValue(mHashMap).addOnCompleteListener { task1 ->
                        if (task1.isSuccessful) {
                            auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                                if (it.isSuccessful) {
                                    confirmSignUp()
                                } else {
                                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(this, "${task1.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun confirmSignUp() {
        AlertDialog.Builder(this).apply {
            setTitle("Sign up is successful!")
            setMessage("Sign up is successful. Please check your email to verify.")
            setPositiveButton("Sign In") { _: DialogInterface?, _: Int ->
                Intent(this@RegisterActivity, LoginActivity::class.java).also {
                    it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(it)
                }
            }
            setCancelable(false)
            create()
            show()
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