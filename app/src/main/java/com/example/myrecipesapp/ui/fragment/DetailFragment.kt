package com.example.myrecipesapp.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.myrecipesapp.R
import com.example.myrecipesapp.databinding.FragmentDetailBinding
import com.example.myrecipesapp.ui.viewModel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var design: FragmentDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        design = FragmentDetailBinding.inflate(inflater,container,false)

        val bundle: DetailFragmentArgs by navArgs()
        val getRecipe = bundle.id

            val recipeId = getRecipe
            viewModel.modelGetRecipeDetail(recipeId)


           viewModel.recipeDetail.observe(viewLifecycleOwner){ recipeDetail->//Recipe type
               //bu kısım eğer birden fazla detay gelirse?

            // design.recipeDetailIdName.text= "${recipeDetail.id} - ${recipeDetail.name}"
               design.toolbar.title= "${recipeDetail.id}-${recipeDetail.name}"
             design.recipeDetailDescription.text=  recipeDetail.description

           }
          //  design.recipeDetailDescription.text=  viewModel.modelGetRecipeDetail(recipeId)



        return design.root
    }



}