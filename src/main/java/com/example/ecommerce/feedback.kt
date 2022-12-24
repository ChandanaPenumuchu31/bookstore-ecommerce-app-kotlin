package com.example.ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.core.text.set
import com.example.ecommerce.databinding.ActivityFeedbackBinding
import com.example.ecommerce.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_feedback.*

class feedback : AppCompatActivity() {
    private lateinit var binding : ActivityFeedbackBinding
    private lateinit var database : DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding=ActivityFeedbackBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_feedback)
    }
    fun submit(view: View)
    {
         val feedback =findViewById<EditText>(R.id.etfeedback).text.toString()

        database= FirebaseDatabase.getInstance().getReference("Feedbackdata")
         val feedbackdata=Feedbackdata(feedback)
         database.child("feebackdata").setValue(feedback).addOnSuccessListener {
             Toast.makeText(this,"feedback given successfully",Toast.LENGTH_LONG).show()
         }.addOnFailureListener {
             Toast.makeText(this,"feedback not saved.Please try again",Toast.LENGTH_LONG).show()
         }
    }
    fun openstore(view:View)
    {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }


}