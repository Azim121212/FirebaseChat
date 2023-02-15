package com.example.firebasechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.firebasechat.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()


    binding.signUpBtn.setOnClickListener {
    val userName = binding.fullNameET.text.toString()
    val  email = binding.emailET.text.toString()
    val password = binding.PasswordET.toString()
    val confirmPassword =  binding.conPasswordET.text.toString()

    if (TextUtils.isEmpty(userName)) {
        Toast.makeText(applicationContext, "username is required", Toast.LENGTH_SHORT).show()
    }
    if (TextUtils.isEmpty(email)) {
        Toast.makeText(applicationContext, "email is required", Toast.LENGTH_SHORT).show()
    }
    if (TextUtils.isEmpty(password)) {
        Toast.makeText(applicationContext, "password is required", Toast.LENGTH_SHORT).show()
    }
    if (TextUtils.isEmpty(confirmPassword)) {
        Toast.makeText(applicationContext, "confirm password is required", Toast.LENGTH_SHORT).show()
    }
        if (password != confirmPassword){
            Toast.makeText(applicationContext, "password not match", Toast.LENGTH_SHORT).show()
        }
        registerUser(userName,email,password)
    }
}

    private fun registerUser(userName: String, email: String, password: String) {
        TODO("Not yet implemented")
    }

    private fun  register(userName:String, email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    val user: FirebaseUser? = auth.currentUser
                    val
                            userId: String = user!!.uid

                    databaseReference = FirebaseDatabase.getInstance()
                        .getReference("Users").child(userId)


                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap.put("userId", userId)
                    hashMap.put("userName", userName)
                    hashMap.put("userImage", "")


                    databaseReference.setValue(hashMap).addOnCompleteListener(
                        this
                    ){
                        if (it.isSuccessful){
                            //open HomePage
                            val intent = Intent(this@Login,HomePage::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
    }
}