package com.example.ecommerce

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class SQLiteHelper(context: Context):SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
 companion object{
     private const val DATABASE_VERSION =  1
     private const val DATABASE_NAME = "cartdatabase.db"
     private const val CART_TABLE="cart_table"
     private const val ID ="id"
     private const val BOOK_NAME ="name"
     private const val BOOK_PRICE ="cost"
     private const val BOOK_COUNT ="count"
 }
    override fun onCreate(db: SQLiteDatabase?) {
        val carttable="CREATE TABLE "+ CART_TABLE +" ("+
                ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+ BOOK_NAME + " VARCHAR(100), "+
                BOOK_PRICE+ " INTEGER, "+ BOOK_COUNT + " INTEGER )"
        db?.execSQL(carttable)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
    fun insertbooks(bks:BooksModel): Long{
        val db=this.writableDatabase
        val contentValues= ContentValues()
      //  contentValues.put(ID,bks.id)
        contentValues.put(BOOK_NAME,bks.name)
        contentValues.put(BOOK_PRICE,bks.cost)
        contentValues.put(BOOK_COUNT,bks.count)
        val success=db.insert(CART_TABLE,null,contentValues)
        db.close()
        return success

    }
    //WHERE ${BOOK_COUNT}>0
    @SuppressLint("Range")
    fun getData():ArrayList<BooksModel>{
        val cartlist:ArrayList<BooksModel> =ArrayList()
        val query="SELECT * FROM $CART_TABLE WHERE $BOOK_COUNT>0"
        val db=this.readableDatabase
        val cursor:Cursor?
        try{
            cursor=db.rawQuery(query,null)
        }catch (e:Exception){
            e.printStackTrace()
            db.execSQL(query)
            return ArrayList()
        }
        var id:Int
        var name:String
        var cost:Int
        var count:Int
        if(cursor.moveToFirst()){
            do{
                id=cursor.getInt(cursor.getColumnIndex("id"))
                name=cursor.getString(cursor.getColumnIndex("name"))
                cost=cursor.getInt(cursor.getColumnIndex("cost"))
                count=cursor.getInt(cursor.getColumnIndex("count"))
                val bks =BooksModel(id=id,name=name,cost=cost,count=count)
                cartlist.add(bks)
            }while(cursor.moveToNext())
        }
        return cartlist
    }
    fun update(bks:BooksModel):Int{
        val db=this.writableDatabase
        val contentValues=ContentValues()
       // contentValues.put(BOOK_NAME,bks.name)
        contentValues.put(BOOK_PRICE,bks.cost)
        contentValues.put(BOOK_COUNT,bks.count)

        val success=db.update(CART_TABLE,contentValues,"name='"+bks.name+"'",null)
        db.close()
        return success
    }
    fun delete()
    {
        val db=this.writableDatabase
        val x="DROP TABLE IF EXISTS "+ CART_TABLE
        db?.execSQL(x)
        db.close()
    }
    fun createtable()
    {
        val db=this.writableDatabase
        val x="CREATE TABLE IF NOT EXISTS "+ CART_TABLE +" ("+
                ID+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+ BOOK_NAME + " VARCHAR(100), "+
                BOOK_PRICE+ " INTEGER, "+ BOOK_COUNT + " INTEGER )"
        db?.execSQL(x)
        db.close()
    }
}