
package com.example.ecommerce


import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.category_items_layout.view.*
import kotlinx.android.synthetic.main.stores_genre_layout.view.*


class BooksAdapter(var context: Context, val bookslist: ArrayList<Books>,private val itemclick:ItemclickListener) : RecyclerView.Adapter<BookViewHolder>() {

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var book_name: TextView
        var book_price:TextView
        var book_image:ImageView
        var add:Button
        var minus:Button

        init {
            book_name = itemView.tvBookname
            book_image=itemView.ivBook
            book_price=itemView.tvBookCost
            add=itemView.btAdditem
            minus=itemView.btRemoveitem
            //itemView.setOnClickListener(this)
        }


        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder{
      //  val v= LayoutInflater.from(parent.context).inflate(R.layout.category_items_layout,parent,false)
        return BookViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.category_items_layout,parent,false),context)
    }

    @SuppressLint("RestrictedApi")


    override fun getItemCount(): Int {
        return bookslist.count()
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bindData(bookslist[position])
        holder.addButton.setOnClickListener{
           itemclick.add(bookslist[position],position)

        }
        holder.minusButton.setOnClickListener {
            itemclick.remove(bookslist[position],position)
        }
    }



}


