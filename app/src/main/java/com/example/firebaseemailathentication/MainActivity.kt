package com.example.firebaseemailathentication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var edtEmail : EditText
    private lateinit var edtPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var btnSignUp : Button
    private lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtEmail= findViewById(R.id.edt_email)
        edtPassword =findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignup)

        mAuth = Firebase.auth

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            login(email,password)
        }

        btnSignUp.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            signUp(email,password)
        }
    }

    private fun login(email : String, password:String){

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                         startActivity(
                             Intent(this, HomeActivity::class.java)
                         )
                        Toast.makeText(this,"Signed  In Successfully", Toast.LENGTH_SHORT)
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(this,"Some Error Occurred", Toast.LENGTH_SHORT)

                    }
                }

    }
    private fun signUp(email : String, password:String){

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    startActivity(
                        Intent(this, HomeActivity::class.java)
                    )
                    Toast.makeText(this,"Signed  In Successfully", Toast.LENGTH_SHORT)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this,"Some Error Occurred", Toast.LENGTH_SHORT)

                }
            }

    }
}