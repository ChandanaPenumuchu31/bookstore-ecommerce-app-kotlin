package com.example.ecommerce

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.text.Html
import android.text.SpannableString
import android.text.style.StrikethroughSpan
import android.view.ContextMenu
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class BookViewHolder(view: View,var context: Context) :RecyclerView.ViewHolder(view){
    private lateinit var sqLiteHelper: SQLiteHelper
    val count:TextView=view.findViewById(R.id.etItemCount)
    val bookimage:ImageView=view.findViewById(R.id.ivBook)
    val discount:TextView=view.findViewById(R.id.tvBeforediscount)
    val bookname:TextView=view.findViewById(R.id.tvBookname)
    val bookprice:TextView=view.findViewById(R.id.tvBookCost)
    val addButton:Button=view.findViewById(R.id.btAdditem)
    val minusButton:Button=view.findViewById(R.id.btRemoveitem)
    fun bindData(textData:Books)
    {
        bookname.text=textData.book_title.toString()
        bookprice.text=textData.book_price
       var x=textData.book_price.toString().toDouble()
        x=x+200
        var y=x.toString()
        //val strike = SpannableString(y)
       // strike.setSpan(StrikethroughSpan(),0,strike.length,0)
        val text = "<strike>"+y+"</strike>"
        discount.text = Html.fromHtml(text)
        //discount.text=strike.toString()
       count.text="${textData.itemcount}"
        Glide.with(bookimage)
            .load(textData.book_image)
            .centerCrop()
            .placeholder(R.drawable.placeholder)
            .into(bookimage)

sqLiteHelper=SQLiteHelper(context)
        sqLiteHelper.createtable()
        val list=sqLiteHelper.getData()
        var check=0
        for(i in list)
        {
            if(i.name == textData.book_title.toString())
            {
                check=check+1
            }
        }
        val bks = BooksModel(
            1,
            name = textData.book_title.toString(),
            cost = bookprice.text.toString().toFloat().toInt(),
            count = count.text.toString().toInt()
        )
if(count.text.toString().toInt()>0 && check ==0) {


    val status = sqLiteHelper.insertbooks(bks)
    if (status > -1) {
        Toast.makeText(context, "added", Toast.LENGTH_SHORT).show()
    } else {
        Toast.makeText(context, "not added", Toast.LENGTH_SHORT).show()
    }
}
        else if(check > 0)
{
            val result=sqLiteHelper.update(bks)
}
       // }
    }
}