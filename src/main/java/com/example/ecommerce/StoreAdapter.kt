package com.example.ecommerce


import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.internal.ContextUtils.getActivity
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.stores_layout.view.*
import java.lang.System.load
import kotlin.coroutines.coroutineContext

class StoreAdapter(val storelist: ArrayList<Store>, val listener:OnItemClickListener) : RecyclerView.Adapter<StoreAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener {
      var store_image:ImageView
      var store_name:TextView
      init{
       store_image=itemView.ivBookstore
        store_name=itemView.tvStorename
          itemView.setOnClickListener(this)

      }
        override fun onClick(v:View?){
            val position=adapterPosition
            if(position!=RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreAdapter.ViewHolder {
            val v= LayoutInflater.from(parent.context).inflate(R.layout.stores_layout,parent,false)
            return ViewHolder(v)
    }

    @SuppressLint("RestrictedApi")
    override fun onBindViewHolder(holder: StoreAdapter.ViewHolder, position: Int) {
       //Picasso.get().load(storelist[position].store_image).into(holder.store_image)

        Glide.with(holder.store_image)
            .load(storelist[position].store_image)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(holder.store_image)
        holder.store_name.text=storelist[position].store_name

    }

    override fun getItemCount(): Int {
return storelist.count()
    }
}


