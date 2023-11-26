package com.example.blogapp.Register


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.blogapp.Model.UserData
import com.example.blogapp.databinding.ActivityRegistrationBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class RegistrationActivity : AppCompatActivity() {
    val binding:ActivityRegistrationBinding by lazy {
        ActivityRegistrationBinding.inflate(layoutInflater)
    }
    private lateinit var auth:FirebaseAuth
    private lateinit var database:FirebaseDatabase
    private lateinit var storage:FirebaseStorage
    private val PICK_IMAGE_REQUEST = 1
    private var imageUri:Uri? = null

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()

        val action = intent.getStringExtra("action")
        if (action == "login") {
            binding.email.visibility = View.VISIBLE
            binding.password.visibility = View.VISIBLE
            binding.login.visibility = View.VISIBLE


            binding.register.isEnabled = false

            binding.register.alpha = 0.5f
            binding.cardView.visibility = View.GONE
            binding.registerEmailAddress.visibility = View.GONE
            binding.registerName.visibility = View.GONE
            binding.registerPassword.visibility = View.GONE
            binding.newHereRegister.isEnabled = false
            binding.newHereRegister.alpha = 0.5f

        } else if (action == "register") {
            binding.login.isEnabled = false
            binding.login.alpha = 0.5f
        }



            binding.register.setOnClickListener {
                val registername:String = binding.registerName.text.toString()
                val registeremail:String = binding.registerEmailAddress.text.toString()
                val registerpassword:String = binding.registerPassword.text.toString()
                if (registername.isEmpty() || registeremail.isEmpty() || registerpassword.isEmpty()) {
                    Toast.makeText(this, "Please fill all the details", Toast.LENGTH_SHORT).show()
                } else {
                    auth.createUserWithEmailAndPassword(registeremail, registerpassword)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val user:FirebaseUser? = auth.currentUser
                                user?.let {
                                    val userReference:DatabaseReference =
                                        database.getReference("users")
                                    val userID:String = user.uid
                                    val userData = UserData(registername, registeremail)
                                    userReference.child(userID).setValue(userData)
                                    val storageReference:StorageReference =
                                        storage.reference.child("profile_image/$userID.jpg")


                                }

                            } else {

                            }
                        }
                }
            }
        

            binding.cardView.setOnClickListener {
                val intent = Intent()
                intent.type = "image/*"
                intent.action = Intent.ACTION_GET_CONTENT
                startActivityForResult(Intent.createChooser(intent, "selec image"),
                    PICK_IMAGE_REQUEST)
            }
        }

        @SuppressLint("CheckResult")
        override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {
                imageUri = data.data
                Glide.with(this)
                    .load(imageUri)
                    .apply(RequestOptions.circleCropTransform())
                    .into(binding.userImg)

            }
        }
    }



