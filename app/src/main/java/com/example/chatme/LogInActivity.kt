package com.example.chatme

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.chatme.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    private lateinit var binding:ActivityLoginBinding
    private lateinit var actionBar:ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email=""
    private var password=""
    private lateinit var noAccount:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        noAccount=findViewById(R.id.NoAccount)
//        actionBar=supportActionBar!!
//        actionBar.title="LogIn"

        progressDialog=ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging in...")
        progressDialog.setCanceledOnTouchOutside(false)

        firebaseAuth=FirebaseAuth.getInstance()
        checkUser()


        binding.NoAccount.setOnClickListener{
            startActivity(Intent(this,SignUpActivity::class.java))

        }
        binding.LoginBt.setOnClickListener {
            validateData()
        }
    }

    private fun validateData()
    {
        email=binding.etEmail.text.toString().trim()
        password=binding.etPassword.text.toString().trim()

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            binding.etEmail.error="Invalid email"
        }
        else if(TextUtils.isEmpty(password))
        {
            binding.etPassword.error="Please enter password"
        }
        else
        {
            firebaseLogin()
        }


    }
    private fun firebaseLogin()
    {
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
            progressDialog.dismiss()
            val firebaseUser=firebaseAuth.currentUser
            val email =firebaseUser!!.email
            Toast.makeText(this,"Logged in  as $email ",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,ProfileActivity::class.java))
            finish()

        }.addOnFailureListener{e->
            progressDialog.dismiss()
            Toast.makeText(this,"Logging failed due to ${e.message} ",Toast.LENGTH_SHORT).show()

        }
    }
    private fun checkUser()
    {
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser!=null){
            startActivity(Intent(this,ProfileActivity::class.java))
        }
    }

}