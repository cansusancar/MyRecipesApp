package com.example.recipe_app.util

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.Navigation

fun Navigation.pass(id:Int,it:View){
    findNavController(it).navigate(id)
}
fun Navigation.pass(id:NavDirections,it: View){
    findNavController(it).navigate(id)
}