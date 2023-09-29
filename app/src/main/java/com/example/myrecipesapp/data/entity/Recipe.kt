package com.example.myrecipesapp.data.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Recipe(@SerializedName("id") var id:Int,
                  @SerializedName("name") var name: String,
                  @SerializedName("description") var description:String?=""): Serializable



{}