package com.example.ecommerce

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_cart_view2.*
import kotlinx.android.synthetic.main.activity_cart_view2.tvcart
import kotlinx.android.synthetic.main.activity_home_page.*
import kotlinx.android.synthetic.main.activity_store_items.*
import kotlinx.android.synthetic.main.activity_store_items.etSearch
import kotlinx.android.synthetic.main.category_items_layout.*
import kotlinx.android.synthetic.main.stores_genre_layout.*
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream




class StoreItems : AppCompatActivity(),ItemclickListener{
    var key =7
    private lateinit var booksAdapter: BooksAdapter
    private lateinit var newRecycleview: RecyclerView
    private lateinit var bookRecycleview: RecyclerView
    private lateinit var newArrayList:ArrayList<Store>
    private lateinit var CategoryArrayList:ArrayList<Books>
    private lateinit var CategoryNameList:ArrayList<categories>
    private var layoutManager: RecyclerView.LayoutManager? = null
  //private var adapter: RecyclerView.Adapter<CategoryAdapter.ViewHolder>? = null
   // private var bookadapter: RecyclerView.Adapter<BooksAdapter.ViewHolder>? = null
    var array_name= arrayListOf<String>()
    var array_price= arrayListOf<String>()
    var category_name= arrayListOf<String>()
    var array_image= arrayListOf<String>()
    var store_name= arrayOf<String>()
    var store_price= arrayOf<String>()
    var store_image= arrayOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_items)

        newRecycleview=findViewById(R.id.recyclerView)

        newRecycleview.layoutManager= LinearLayoutManager(this)

        newRecycleview.setHasFixedSize(true)
        /*bookRecycleview=findViewById(R.id.rvCategorybooks)
        bookRecycleview.layoutManager= LinearLayoutManager(this)
        bookRecycleview.setHasFixedSize(true)*/

        CategoryArrayList= arrayListOf<Books>()
        //BooksArrayList= arrayListOf<books>()
        getCategories()
    }

    private fun getCategories() {
        tvStoreName.setText(intent.getStringExtra("storename").toString())

        var json: String? = null
        try {
            val inputStream: InputStream = assets.open("Bookstore.json")

            json = inputStream.bufferedReader().use { it.readText() }

            var category_array = JSONArray(json)
            for (i in 0..category_array.length() - 1) {
                if (intent.getStringExtra("position").toString() == i.toString()) {
                    val category_object =
                        category_array.getJSONObject(i).getJSONArray("categories")
                    for (j in 0..category_object.length() - 1) {
                        val category_nme = category_object.getJSONObject(j)
                        array_name.add(category_nme.getString("book_title"))
                        array_image.add(category_nme.getString("book_img"))
                        array_price.add(category_nme.getString("book_price"))


                    }
                }
            }
            store_name = array_name.toTypedArray()
            store_image=array_image.toTypedArray()
            store_price=array_price.toTypedArray()
            var books:MutableList<String> = ArrayList()
            books=store_name.toMutableList()
            etSearch.setAdapter(ArrayAdapter(this,android.R.layout.simple_list_item_1,books))
            etSearch.threshold = 1
            etSearch.onItemClickListener=object : AdapterView.OnItemClickListener {
                override fun onItemClick(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Toast.makeText(
                        applicationContext,
                        "Book Exits : " + parent?.getItemAtPosition(position),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            for (i in 0..(store_name.size - 1)) {
                val category = Books(store_name[i],store_price[i],store_image[i],0)
                CategoryArrayList.add(category)
            }
            newRecycleview.adapter = BooksAdapter(this,CategoryArrayList,this)

        } catch (e: IOException) {

        }

    }


    override fun add(data: Books, position: Int) {
        data.itemcount+=1
        newRecycleview.adapter?.notifyItemChanged(position)
        Toast.makeText(this,"item added",Toast.LENGTH_SHORT).show()

    }

    override fun remove(data: Books, position: Int) {
       data.itemcount-=1
        if(data.itemcount<=0)
        {
            data.itemcount=0
        }
        newRecycleview.adapter?.notifyItemChanged(position)
        Toast.makeText(this,"item removed",Toast.LENGTH_SHORT).show()

    }
    fun Cartpage(view: View){
        val intent = Intent(this, Cart_view::class.java)
        startActivity(intent)
    }

}

