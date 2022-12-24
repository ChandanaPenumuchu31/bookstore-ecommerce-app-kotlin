package com.example.ecommerce

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.SmsManager
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.jar.Manifest

class Success : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var database : DatabaseReference
    var msg=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success)
        sharedPreferences=getSharedPreferences("user", Context.MODE_PRIVATE)
        var uname=sharedPreferences.getString("username","").toString()
        var contact=sharedPreferences.getString("contact","").toString()
        msg=intent.getStringExtra("message").toString()
         var pid=intent.getStringExtra("paymentid").toString()
        var location=intent.getStringExtra("deliverydetails").toString()
        var books=intent.getStringExtra("books").toString()
        var cost=intent.getStringExtra("cost").toString()
        checkpermission()
        database = FirebaseDatabase.getInstance().getReference("orderdetails")
        val users = orderdetails(uname, contact,msg,pid,books,cost,location)
        database.child(pid).setValue(users).addOnSuccessListener {
            Toast.makeText(this, "order details stored", Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(this, "order details not stored", Toast.LENGTH_LONG).show()
        }
        var obj=SmsManager.getDefault()
        obj.sendTextMessage("9603690777",null,msg,null,null)
        obj.sendTextMessage("9603690777",null,msg,null,null)
    }

    private fun checkpermission() {
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.SEND_SMS),101)
        }
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