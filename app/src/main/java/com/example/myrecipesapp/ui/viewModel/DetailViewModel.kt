package com.example.myrecipesapp.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrecipesapp.data.entity.Recipe
import com.example.myrecipesapp.data.repo.RecipeDaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor (var repo : RecipeDaRepository): ViewModel() {
    var recipeDetail= MutableLiveData<Recipe>()
    init {
       recipeDetail= repo.repoReturnRecipeDetail()
    }
    fun modelGetRecipeDetail(id:Int) {
        Log.d("RecipeFragment", "getRecipeDetail() function called")
        repo.repoGetRecipeDetail(id)
    }

}