package com.example.ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_cart_view2.*
import kotlinx.android.synthetic.main.activity_main.*

class Register : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    private lateinit var data : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }
    fun validate(view: View){
        val usernaame=findViewById<EditText>(R.id.etUsername).text.toString()
        val contact=findViewById<EditText>(R.id.etContact).text.toString()
        val password=findViewById<EditText>(R.id.etPassword).text.toString()
        data=FirebaseDatabase.getInstance().getReference("Userdata")
        data.child(usernaame).get().addOnSuccessListener {
            if (it.exists()) {
                Toast.makeText(this, "Username exits", Toast.LENGTH_LONG).show()
            }else{
                if (contact.length== 10 && usernaame.length!= 0 && password.length!= 0 &&  contact.all { c: Char ->
                        c.isDigit()
                    }) {
                    database = FirebaseDatabase.getInstance().getReference("Userdata")
                    val users = Userdata(usernaame, contact, password)
                    database.child(usernaame).setValue(users).addOnSuccessListener {
                        Toast.makeText(this, "registered successfully", Toast.LENGTH_LONG).show()
                    }.addOnFailureListener {
                        Toast.makeText(this, "Not registered", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Invalid details", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
    fun login(view: View)
    {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}