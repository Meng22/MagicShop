package com.example.magicshop

data class MagicItem(val image : Int, val name: String, val price: Int, val level: Int, var isPurchased: Boolean = false)