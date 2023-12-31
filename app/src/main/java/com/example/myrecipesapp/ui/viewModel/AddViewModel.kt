package com.example.myrecipesapp.ui.viewModel

import androidx.lifecycle.ViewModel
import com.example.myrecipesapp.data.entity.Recipe
import com.example.myrecipesapp.data.repo.RecipeDaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(var repo : RecipeDaRepository): ViewModel() {

    fun modelAdd(request: Recipe){
        repo.repoAddRecipe(request)
    }
}