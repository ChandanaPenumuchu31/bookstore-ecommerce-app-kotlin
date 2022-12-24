package com.example.ecommerce

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.remoteMessage

const val channelId="notification channel"
const val channelName="login notification"
class MyFirebaseMessagingService:FirebaseMessagingService() {
    lateinit var ntitle:String
    lateinit var ndescription:String
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if(remoteMessage.getNotification()!= null){

            generateNotification(remoteMessage.notification!!.title!!,remoteMessage.notification!!.body!!)
//ntitle=remoteMessage.notification!!.title!!.toString()
            //   ndescription=remoteMessage.notification!!.body!!.toString()
        }
    }
    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title: String, description: String): RemoteViews{


        val remoteView=RemoteViews("com.example.login",R.layout.notification)
        remoteView.setTextViewText(R.id.tvtitle,title)
        remoteView.setTextViewText(R.id.tvdescription,description)
        return remoteView
    }
    fun generateNotification(title:String,description:String)
    {

        ntitle=title
        ndescription=description
        //db.insertData(insert)
        val intent= Intent(this,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent= PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT)
        var builder: NotificationCompat.Builder=NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.bookbackground)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder= builder.setContent(getRemoteView(title,description))




        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            val notificationChannel=NotificationChannel(channelId, channelName,NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)

        }
        notificationManager.notify(0,builder.build())
    }
}