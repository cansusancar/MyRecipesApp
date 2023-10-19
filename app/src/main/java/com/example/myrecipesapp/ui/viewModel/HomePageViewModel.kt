package com.example.myrecipesapp.ui.viewModel

import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myrecipesapp.data.entity.Recipe
import com.example.myrecipesapp.data.repo.RecipeDaRepository
import com.example.myrecipesapp.databinding.FragmentHomePageBinding
import com.example.myrecipesapp.databinding.FragmentTatlilarBinding
import com.example.myrecipesapp.databinding.RecipeCardDesignBinding
import com.example.myrecipesapp.ui.adapter.TatlilarAdapter
import com.example.myrecipesapp.ui.fragment.TatlilarFragment
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.FieldPosition
import javax.inject.Inject


@HiltViewModel
class HomePageViewModel @Inject constructor(var  repo : RecipeDaRepository) : ViewModel() {

    var recipesList = MutableLiveData<List<Recipe>>()


    init {
        modelLoadRecipes()
        recipesList = repo.repoReturnRecipes()

    }

    fun modelLoadRecipes() {
        repo.repoGetRecipes()
    }

    fun modelSearch(searchWord: String) {
        repo.repoSearchRecipe(searchWord)
    }


    fun updateData(newData: List<Recipe>):List<Recipe> {
        recipesList.value = newData
        return recipesList.value!!
        //  notifyDataSetChanged()
    }
}