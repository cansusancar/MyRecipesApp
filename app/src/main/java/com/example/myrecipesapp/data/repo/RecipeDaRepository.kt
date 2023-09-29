package com.example.myrecipesapp.data.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.myrecipesapp.data.entity.CRUDresponse
import com.example.myrecipesapp.data.entity.Recipe
import com.example.myrecipesapp.data.entity.RecipeDetailResponse
import com.example.myrecipesapp.data.entity.RecipeResponse
import com.example.myrecipesapp.refrofit.RecipesDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeDaRepository(var rDao: RecipesDao) {


    var recipeList: MutableLiveData<List<Recipe>>

    var recipeDetail:MutableLiveData<Recipe>

    init {
      recipeList= MutableLiveData()
        recipeDetail=MutableLiveData()
    }

    fun repoReturnRecipes():MutableLiveData<List<Recipe>>{
        return recipeList
    }

    fun repoReturnRecipeDetail():MutableLiveData<Recipe>{
        return recipeDetail
    }



    fun repoGetRecipes(){
            rDao.daoAllRecipes().enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(call: Call<RecipeResponse>?, response: Response<RecipeResponse>) {
                if (response.isSuccessful) {
                    val list=response.body()?.recipes
                    if (list!=null){
                        recipeList.value=list!!
                }else {
                    Log.e("repoGetRecipes", "request response is empty")
                }
                }else {
                    Log.e("repoGetRecipes", "response is not successful")
                }
                }
            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                Log.e("repoGetRecipes", "onFailure called. Error: ${t.message}")
            }
        })
    }



    fun repoUpdateRecipes(request: Recipe){
        rDao.daoUpdateRecipe(request).enqueue(object :Callback<CRUDresponse>{
            override fun onResponse(call: Call<CRUDresponse>, response: Response<CRUDresponse>) {

                if (response.isSuccessful) {
                    val message=response.body()?.message
                    if (message != null) {
                        Log.e("repoUpdateRecipes","response is ${message.toString()}")
                    } else {
                        Log.e("repoUpdateRecipes", "request response is empty")
                    }
                } else {
                    Log.e("repoUpdateRecipes", "response is not successful")
                }
            }
            override fun onFailure(call: Call<CRUDresponse>, t: Throwable) {
                Log.e("repoUpdateRecipes", "onFailure called. Error: ${t.message}")
            }
        })
    }



    fun repoGetRecipeDetail(id:Int)  {// id parametre si olacak
        try {
            rDao.daoGetRecipeDetail(id).enqueue(object : Callback<RecipeDetailResponse> {
                override fun onResponse(
                    call: Call<RecipeDetailResponse>,
                    response: Response<RecipeDetailResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("repoGetRecipeDetail", "response received")
                        val detail = response.body()!!.recipe
                        if (detail!= null) {
                            recipeDetail.value = detail
                        } else {
                            Log.e("repoGetRecipeDetail", "request response is empty")
                        }
                    } else {
                        Log.e("repoGetRecipeDetail", "response is not successful. Error: ${response.code()}")
                    }
                }
                override fun onFailure(call: Call<RecipeDetailResponse>, t: Throwable) {
                    Log.e("repoGetRecipeDetail", "onFailure called. Error: ${t.message}", t)
                }
            })
        } catch (e: Exception) {
            Log.e("repoGetRecipeDetail", "Unknown error occurred: ${e.message}", e)
        }
    }



    fun repoAddRecipe(request: Recipe){
        rDao.daoAddRecipe(request).enqueue(object  : Callback<CRUDresponse>{
            override fun onResponse(call: Call<CRUDresponse>, response: Response<CRUDresponse>) {
                if (response.isSuccessful) {
                    if (response!= null) {
                        val succes = response.body()!!.status
                        val mesege = response.body()!!.message
                        Log.e("repoAddRecipe"," $succes - $mesege")
                        repoGetRecipes()
                    } else {
                        Log.e("repoAddRecipe", "request response is empty")
                    }
                } else {
                    Log.e("repoAddRecipe", "response is not successful")
                }
            }
            override fun onFailure(call: Call<CRUDresponse>, t: Throwable) {
                Log.e("repoAddRecipe", "onFailure called. Error: ${t.message}", t)
            }

        })
    }

    fun repoSearchRecipe(query: String) {
        rDao.daoSearchRecipe(query).enqueue(object : Callback<RecipeResponse> {
            override fun onResponse(call: Call<RecipeResponse>, response: Response<RecipeResponse>) {
                if (response.isSuccessful) {
                    val list = response.body()!!.recipes
                    if (list != null) {
                        recipeList.value = list
                        //  recipeList.postValue(list)
                    } else {

                        Log.e("repoSearchRecipe", "request response is empty")
                    }
                } else {
                    Log.e("repoSearchRecipe", "response is not successful")
                }
            }
            override fun onFailure(call: Call<RecipeResponse>, t: Throwable) {
                Log.d("repoSearchRecipe", "onFailure called. Error: ${t.message}")
            }
        })

    }









}