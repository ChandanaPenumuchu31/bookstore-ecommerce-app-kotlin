package com.example.ecommerce

import kotlin.random.Random

data class BooksModel(
    var id:Int = 0,
    var name: String="",
    val cost: Int=0,
    var count:Int=0

) /*{
    companion object {
        fun getAutoId(): Int {
            val random = Random
            return random.nextInt(100)
        }

    }
}*/
