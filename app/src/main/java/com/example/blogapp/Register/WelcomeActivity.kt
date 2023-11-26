package com.example.blogapp.Register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.blogapp.Register.RegistrationActivity


import com.example.blogapp.databinding.ActivityWelcomeBinding

class WelcomeActivity : AppCompatActivity() {
    private val binding:ActivityWelcomeBinding by lazy {
        ActivityWelcomeBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.login.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            intent.putExtra("action","login")
            startActivity(intent)

        }
//        binding.register.setOnClickListener {
//            val intent = Intent(this,RegistrationActivity::class.java)
//            intent.putExtra("action","register")
//            startActivity(intent)
//        }
    }

}