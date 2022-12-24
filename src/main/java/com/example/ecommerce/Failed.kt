package com.example.ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class Failed : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_failed)
    }
    fun openstore(view: View)
    {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
    fun openfeedback(view: View)
    {
        val intent = Intent(this, feedback::class.java)
        startActivity(intent)
    }
}