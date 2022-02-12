package com.example.chatme

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.ActionBar
import com.example.chatme.databinding.ActivityLoginBinding
import com.example.chatme.databinding.ActivityProfileBinding
import com.example.chatme.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email=""
    private var password=""
    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)

        setContentView(binding.root)

//        actionBar=supportActionBar!!
//        actionBar.title="Profile"
        firebaseAuth=FirebaseAuth.getInstance()
        checkUser()
        binding.LogOutBt.setOnClickListener {

            firebaseAuth.signOut()
            checkUser()
        }

    }
    private fun checkUser()
    {
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser!=null){
            val email=firebaseUser.email
            binding.EmailProfile.text=email
        }
        else

        {
            startActivity(Intent(this,LogInActivity::class.java))
            finish()

        }
    }
}