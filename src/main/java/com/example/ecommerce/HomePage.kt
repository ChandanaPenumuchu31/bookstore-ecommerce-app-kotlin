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
import kotlinx.android.synthetic.main.activity_home_page.*
import org.json.JSONArray
import java.io.IOException
import java.io.InputStream

class HomePage : AppCompatActivity(),StoreAdapter.OnItemClickListener {
    private lateinit var newRecycleview: RecyclerView
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var newArrayList:ArrayList<Store>
    private lateinit var CategoryArrayList:ArrayList<categories>
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<StoreAdapter.ViewHolder>? = null
     var array_name= arrayListOf<String>()
    var array_image= arrayListOf<String>()
    var store_name= arrayOf<String>()
    var store_image= arrayOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sqLiteHelper=SQLiteHelper(this)
       sqLiteHelper.delete()
        setContentView(R.layout.activity_home_page)
        newRecycleview=findViewById(R.id.recyclerView)
        newRecycleview.layoutManager=LinearLayoutManager(this)
        newRecycleview.setHasFixedSize(true)
        newArrayList= arrayListOf<Store>()
        CategoryArrayList= arrayListOf<categories>()
        getMyData()
    }
    private fun getMyData() {
 var json:String?=null
       try{
            val inputStream:InputStream=assets.open("Bookstore.json")
            json=inputStream.bufferedReader().use{it.readText()}
           var store_array=JSONArray(json)
           var category_array=JSONArray(json)
           for(i in 0..store_array.length()-1){
             var store_object=store_array.getJSONObject(i)
               array_name.add(store_object.getString("store_name"))
               array_image.add(store_object.getString("store_image"))
           }
           store_name=array_name.toTypedArray()
           store_image=array_image.toTypedArray()
           var stores:MutableList<String> = ArrayList()
           stores=store_name.toMutableList()
           etSearch.setAdapter(ArrayAdapter(this,android.R.layout.simple_list_item_1,stores))
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
                       "Selcted Store Exits : " + parent?.getItemAtPosition(position),
                       Toast.LENGTH_LONG
                   ).show()
               }
           }
           for(i in 0..(store_name.size-1)){
               val store=Store(store_name[i], store_image[i])
               newArrayList.add(store)
           }
           newRecycleview.adapter=StoreAdapter(newArrayList,this)

        }
        catch (e: IOException){

        }


    }

    override fun onItemClick(position: Int) {
        Toast.makeText(this,"Item $position clicked",Toast.LENGTH_SHORT).show()
        val intent = Intent(this, StoreItems::class.java)
        intent.putExtra("position",position.toString())
        intent.putExtra("storename",store_name[position])
        startActivity(intent)

    }
}