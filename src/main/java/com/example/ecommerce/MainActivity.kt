package com.example.ecommerce

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.set
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var database : DatabaseReference
    lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("ObsoleteSdkInt")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
            window.statusBarColor = Color.TRANSPARENT
        }
    }
    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }
    fun login(view: View)
    {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }
    @SuppressLint("SetTextI18n")
    fun validate(view: View){
        val username =findViewById<EditText>(R.id.etUsername).text.toString()
        val password =findViewById<EditText>(R.id.etPassword).text.toString()
        if(username.isEmpty() || password.isEmpty()) {

                Toast.makeText(this, "Username or Password empty", Toast.LENGTH_LONG).show()

        }
        else{
            database=FirebaseDatabase.getInstance().getReference("Userdata")
            database.child(username).get().addOnSuccessListener {
                if(it.exists())
                {
                    val contact=it.child("contact").value
                    val passwordd=it.child("password").value
                    if(password==passwordd)
                    {
                        sharedPreferences=getSharedPreferences("user", Context.MODE_PRIVATE)
                        val editor=sharedPreferences.edit()
                        editor.putString("username",username)
                        editor.putString("contact",contact.toString())
                        editor.apply()

                        val intent = Intent(this, HomePage::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        Toast.makeText(this, "wrong password", Toast.LENGTH_LONG).show()
                   etPassword
                    }
                }else{
                    Toast.makeText(this, "Username don't exits", Toast.LENGTH_LONG).show()

                }
            }.addOnFailureListener {
                Toast.makeText(this, "failed", Toast.LENGTH_LONG).show()

            }
            /*if(username=="chandana" && password=="chandu"){
                val intent = Intent(this, HomePage::class.java)
                startActivity(intent)
            }
            else{
                val error = findViewById<TextView>(R.id.tvError).apply {
                    text = "Incorrect username or Password !"
                }*/
            }
        }
    }
