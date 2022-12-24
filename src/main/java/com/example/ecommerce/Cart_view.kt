package com.example.ecommerce

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import kotlinx.android.synthetic.main.activity_cart_view2.*
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class Cart_view : AppCompatActivity(), PaymentResultListener {
    var cost=""
    var x=0
    lateinit var sharedPreferences: SharedPreferences
    lateinit var card:CardView
    lateinit var success:TextView
    lateinit var failed:TextView
    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var newRecycleview: RecyclerView
    private lateinit var cartlist:ArrayList<BooksModel>
    private var layoutManager: RecyclerView.LayoutManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart_view2)
        sqLiteHelper=SQLiteHelper(this)
         cartlist=sqLiteHelper.getData()
        newRecycleview=findViewById(R.id.CartList)
        newRecycleview.layoutManager= LinearLayoutManager(this)
        newRecycleview.setHasFixedSize(true)
        newRecycleview.adapter = CartAdapter(cartlist)



    }
    fun viewtotal(view: View){
        val intent = Intent(this, Cart_view::class.java)
        sharedPreferences=getSharedPreferences("cost", Context.MODE_PRIVATE)
        var tcost=sharedPreferences.getString("totalcost","")
        var y=tcost
        tcost="₹ "+tcost.toString()
        tvTotalCost.setText(tcost)

        cost=y.toString()
        x=cost.toInt()*100
        cost=x.toString()
        x=x/100


    }
    fun viewdiscount(view: View){
        AlertDialog.Builder(this)
            .setTitle("                 % Discount %")
            .setMessage("20% discount on total price if purchae above ₹2000\n" +
                    "\n10% discount on total price if purchase above ₹1500\n" +
                    "\n5% discount on total price if purchase above ₹1000\n")
            .setPositiveButton("Close"){_,_->}
            .show()


    }
    fun addiscount(view: View){
        sharedPreferences=getSharedPreferences("cost", Context.MODE_PRIVATE)
        var tcost=sharedPreferences.getString("totalcost","").toString()
        var c=tcost.toInt()
        if(c>=2000)
        {
            c=c-c*20/100
        }
        else if(c>=1500)
        {
            c=c-c*10/100
        }
        else if(c>=1000)
        {
            c=c-c*5/100
        }

        tvTotalCost.setText("₹ "+c.toString())
        cost=c.toString()
        x=cost.toInt()*100
        cost=x.toString()
        x=x/100

    }
    fun PayNow(view: View)
    {
        if(etCity.text.toString().isNotEmpty() && etName.text.toString().isNotEmpty()&& etContact.text.toString().isNotEmpty() &&
            etPincode.text.toString().isNotEmpty() && etStreet.text.toString().isNotEmpty() ) {
                if(etContact.length()!=10  && etContact.text.toString().all { c: Char -> c.isDigit() })
                {
                    Toast.makeText(this, "Please add the valid contact !", Toast.LENGTH_LONG).show()
                }
            else {
                    if (etPincode.length() != 6 && etPincode.text.toString().all { c: Char -> c.isDigit() }) {
                        Toast.makeText(this, "Please enter the valid pincode", Toast.LENGTH_LONG)
                            .show()
                    } else {
                        val co = Checkout()
//"https://s3.amazonaws.com/rzp-mobile/images/rzp.png" +
                        try {
                            val options = JSONObject()
                            options.put("name", "AURORA")
                            options.put("description", "Pay the amount")
                            //You can omit the image option to fetch the image from dashboard
                            options.put(
                                "image",
                                ""
                            )
                            options.put("theme.color", "#988547");
                            options.put("currency", "INR");
                            options.put("amount", cost)//pass amount in currency subunits
                            val prefill = JSONObject()
                            prefill.put("email", "")
                            prefill.put("contact", "")

                            options.put("prefill", prefill)
                            co.open(this, options)
                        } catch (e: Exception) {
                            Toast.makeText(
                                this,
                                "Error in payment: " + e.message,
                                Toast.LENGTH_LONG
                            )
                                .show()
                            e.printStackTrace()
                        }
                    }
                }
        }
        else{
            Toast.makeText(this, "Enter all location details ", Toast.LENGTH_LONG).show()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this,"Payment Successful $p0",Toast.LENGTH_LONG).show()
        sharedPreferences=getSharedPreferences("cost", Context.MODE_PRIVATE)
        var s=sharedPreferences.getString("bookdetails","").toString()
        var bookss=s
        s=s+"payment id"+p0+"\n"
        s=s+"cost :"+x.toString()+"\n"
        s=s+"DELIVERY DETAILS \n"
        s=s+"name :"+etName.text+"\n"
        s=s+"Street :"+etStreet.text+"\n"
        s=s+"City :"+etCity.text+"\n"
        s=s+"pincode :"+etPincode.text+"\n"
        s=s+"Contact :"+etContact.text+"\n"
        val intent = Intent(this, Success::class.java)
        intent.putExtra("message",s)
        intent.putExtra("paymentid",p0)
        intent.putExtra("books",s)
        intent.putExtra("cost",x.toString())
        intent.putExtra("deliverydetails",etName.text.toString()+"   "+etStreet.text.toString()+"    "+etCity.text.toString()+"   "+etPincode.text.toString()+"   "+etContact.text.toString())
        startActivity(intent)


    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this,"Error $p1",Toast.LENGTH_LONG).show()
        val intent = Intent(this,Failed::class.java)
        startActivity(intent)
    }


}
