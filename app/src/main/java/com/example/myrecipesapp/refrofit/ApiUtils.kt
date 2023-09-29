package com.example.myrecipesapp.refrofit


class ApiUtils {
    companion object{
        val BASE_URL="https://api.canerture.com/"
        fun getYemeklerDao(): RecipesDao{
            return RetrofitClient.getClient(BASE_URL).create(RecipesDao::class.java)
        }
    }
}