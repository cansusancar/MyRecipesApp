package com.example.myrecipesapp.ui.fragment

import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.myrecipesapp.data.entity.Recipe
import com.example.myrecipesapp.databinding.FragmentUpdateBinding
import com.example.myrecipesapp.ui.viewModel.UpdatePageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private lateinit var viewModel: UpdatePageViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUpdateBinding.inflate(inflater,container,false)
        binding.toolbarUpdateRecipe.title = "Tarif Güncelle"
        val bundle:UpdateFragmentArgs by navArgs()
        val getRecipeId=bundle.recipeId //id yi view modelle repoya göndermek için bundle ile id aldık
        viewModel.modelDetail(getRecipeId)


        viewModel.recipeDetail.observe(viewLifecycleOwner) { recipeDetail ->//Recipe type
            binding.editTextRecipeName.setText(recipeDetail.name)
            binding.editTextRecipeDiscription.setText(recipeDetail.description)
        }
            binding.ButtonUpdate.setOnClickListener {
            val alert = AlertDialog.Builder(requireContext())
            alert.setTitle("Güncelle")
            val recipeName=binding.editTextRecipeName.text?.trim().toString()
            val recipeDiscription=binding.editTextRecipeDiscription.text?.trim().toString()
            if(!recipeName.equals("") && !recipeDiscription.equals("") ) {
                alert.setMessage("${recipeName.uppercase()} Güncellensin mi ?")
                alert.setPositiveButton("EVET") { dialog, which ->
                    val recipe=Recipe(getRecipeId,recipeName,recipeDiscription)
                    update(recipe)// id yi view modelle repoya gönder
                    Toast.makeText(
                        requireContext(),
                        "${recipeName.uppercase()} güncellendi.",
                        Toast.LENGTH_LONG
                    ).show()
                    //OnClick Listener
                }
                alert.setNegativeButton("HAYIR") { dialog, which ->
                    //OnClick Listener
                    Toast.makeText(
                        requireContext(),
                        "${recipeName.uppercase()} Güncellenmedi!",
                        Toast.LENGTH_LONG
                    ).show()
                }
                alert.show()
            }
        }
        return binding.root
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempView: UpdatePageViewModel by viewModels ()
        viewModel=tempView
    }
    fun update(request:Recipe){
        viewModel.modelUpdate(request)
    }
// bu yaptığımız gelen veriyi düzenleyip push etme aşaması henüz o yemek tarifini güncelleme ekranına getirmedik

}