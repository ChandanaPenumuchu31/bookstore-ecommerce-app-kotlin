package com.example.ecommerce

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_cart_view2.view.*
import kotlinx.android.synthetic.main.final_cart_layout.view.*

class CartAdapter(val cartlist: ArrayList<BooksModel>):RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private var tcost=0
    private var bookdetails=""
   lateinit var sharedPreferences:SharedPreferences
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
    var bookname:TextView
    var bookcount:TextView
    var totalPrice:TextView
    init{

        bookname=itemView.tvBookname
        bookcount=itemView.item_count
        totalPrice=itemView.item_tcost
        sharedPreferences=itemView.context.getSharedPreferences("cost",Context.MODE_PRIVATE)
    }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.final_cart_layout,parent,false)
        return ViewHolder(v)
    }


    override fun getItemCount(): Int {
    return cartlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.bookname.text=cartlist[position].name
       holder.bookcount.text=cartlist[position].count.toString()
        var x=cartlist[position].cost * cartlist[position].count
        holder.totalPrice.text=x.toString()
        bookdetails=bookdetails+"book name :"+cartlist[position].name.toString()+"\n"
        bookdetails=bookdetails+"book count :"+cartlist[position].count.toString()+"\n"
        tcost=tcost+x
        val editor=sharedPreferences.edit()
        editor.putString("totalcost",tcost.toString())
        editor.putString("bookdetails",bookdetails.toString())
        editor.apply()



    }
}