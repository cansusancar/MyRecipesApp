package com.example.myrecipesapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrecipesapp.data.entity.Recipe
import com.example.myrecipesapp.data.repo.RecipeDaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdatePageViewModel @Inject constructor (var repo : RecipeDaRepository): ViewModel() {
    var recipeDetail= MutableLiveData<Recipe>()
    init {
        recipeDetail= repo.repoReturnRecipeDetail()
    }
    fun modelDetail(id:Int){
        repo.repoGetRecipeDetail(id)
    }
    fun modelUpdate(request: Recipe){
        repo.repoUpdateRecipes(request)
    }



}