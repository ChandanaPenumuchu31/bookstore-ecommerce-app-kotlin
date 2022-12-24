/*package com.example.ecommerce

class CategoryAdapter {
}*/
package com.example.ecommerce


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.stores_genre_layout.view.*


class CategoryAdapter( val categorylist: ArrayList<categories>,val listener:OnItemClickListener) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView),View.OnClickListener {
        var category_name: TextView

        init {
            category_name = itemView.tvGenre
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                 listener.onItemClick(position)
            }

        }
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
   /* interface OnItemClickListener{
        fun onItemClick(position: Int)
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val v= LayoutInflater.from(parent.context).inflate(R.layout.stores_genre_layout,parent,false)
        return ViewHolder(v)
    }

    @SuppressLint("RestrictedApi")


    override fun getItemCount(): Int {
        return categorylist.count()
    }

   override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.category_name.text=categorylist[position].category_name

    }
//  holder.category_name.text=categorylist[position].category_name



}


