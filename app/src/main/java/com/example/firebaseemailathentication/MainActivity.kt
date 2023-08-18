package com.example.firebaseemailathentication

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {
    private lateinit var edtphoneNumber: EditText
    private lateinit var edtgetOtp: EditText
    private lateinit var btnOtp: Button
    private lateinit var btnVerify: Button
    private lateinit var mAuth: FirebaseAuth
    var verificationId: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        edtEmail= findViewById(R.id.edt_email)
//        edtPassword =findViewById(R.id.edt_password)
//        btnLogin = findViewById(R.id.btnLogin)
//        btnSignUp = findViewById(R.id.btnSignup)
        edtphoneNumber = findViewById(R.id.edt_phone_number)
        edtgetOtp = findViewById(R.id.edt_otp)
        btnOtp = findViewById(R.id.btn_get_otp)
        btnVerify = findViewById(R.id.btnVerifyOtp)

        mAuth = Firebase.auth
//

        btnOtp.setOnClickListener {
            val number = "+91${edtphoneNumber.text}"
            sendVerificationCode(number)

        }
        btnVerify.setOnClickListener {
            val otp = edtgetOtp.text.toString()
            verifyCode(otp)
        }
    }

    private fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithCredentials(credential)
    }

    private fun signInWithCredentials(phoneAuthCredential: PhoneAuthCredential) {
        mAuth.signInWithCredential(phoneAuthCredential)

            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast
                        .makeText(this, "Phone Number Verification Successfull", Toast.LENGTH_SHORT)
                        .show()
                    startActivity(
                        Intent(this, HomeActivity::class.java)
                    )
                } else {
                    Toast
                        .makeText(this, "Phone Number Verification Successfull", Toast.LENGTH_SHORT)
                        .show()
                }

            }
    }

    /*
        btnSignUp.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()
            signUp(email,password)
        }

 */

    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(number)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(verificationCallback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    val verificationCallback: OnVerificationStateChangedCallbacks =
            object : OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {

                }

                override fun onVerificationFailed(p0: FirebaseException) {

                }

                override fun onCodeSent(s: String, p1: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(s, p1)
                    verificationId = s
                }
            }
}
/*    private fun login(email : String, password:String){

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

 */
