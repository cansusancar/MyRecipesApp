package com.example.myrecipesapp.data.entity

import com.google.gson.annotations.SerializedName

data class CRUDresponse(@SerializedName("status") var status:Int, @SerializedName("message") var message:String){}
