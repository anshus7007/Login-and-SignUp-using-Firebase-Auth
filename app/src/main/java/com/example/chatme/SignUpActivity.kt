package com.example.chatme

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.chatme.databinding.ActivityLoginBinding
import com.example.chatme.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email=""
    private var password=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        actionBar=supportActionBar!!
//        actionBar.title="Sign Up"

        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Signing Up...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth=FirebaseAuth.getInstance()
        binding.SignUpBt.setOnClickListener {
            validateData()
        }

    }

    private fun validateData()
    {
        email=binding.etEmailSignUp.text.toString().trim()
        password=binding.etPassword.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            binding.etEmailSignUp.error="Invalid email"
        }
        else if(TextUtils.isEmpty(password))
        {
            binding.etPassword.error="Please enter password"
        }
        else if(password.length<6)
        {
            binding.etPassword.error="Password must be atleast of length 6"

        }
        else
        {
            firebaseLogin()
        }


    }
    private fun firebaseLogin()
    {
        progressDialog.show()
        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
            progressDialog.dismiss()
            val firebaseUser=firebaseAuth.currentUser
            val email =firebaseUser!!.email
            Toast.makeText(this,"Accounted Created with $email ", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,ProfileActivity::class.java))
            finish()

        }.addOnFailureListener{e->
            progressDialog.dismiss()
            Toast.makeText(this,"Sign Up failed due to ${e.message} ", Toast.LENGTH_SHORT).show()

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}