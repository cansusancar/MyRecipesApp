package com.example.myrecipesapp.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrecipesapp.data.entity.Recipe
import com.example.myrecipesapp.data.repo.RecipeDaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(var  repo : RecipeDaRepository) : ViewModel() {

    var recipesList = MutableLiveData<List<Recipe>>()
    init {
        modelLoadRecipes()
       recipesList= repo.repoReturnRecipes()
    }

    fun modelLoadRecipes(){
        repo.repoGetRecipes()
    }

    fun modelSearch(searchWord:String){
        repo.repoSearchRecipe(searchWord)
    }




}
