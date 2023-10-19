package com.example.myrecipesapp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipesapp.R
import com.example.myrecipesapp.data.entity.Recipe
import com.example.myrecipesapp.databinding.FragmentTatlilarBinding
import com.example.myrecipesapp.databinding.FragmentYemeklerBinding
import com.example.myrecipesapp.ui.adapter.TatlilarAdapter
import com.example.myrecipesapp.ui.adapter.YemeklerAdapter
import com.example.myrecipesapp.ui.viewModel.SharedViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken



class YemeklerFragment : Fragment() {

        /*

        val sharedPreferences = requireActivity().getSharedPreferences("My AppPreferences2", Context.MODE_PRIVATE)
        val gson = Gson()

        val selectedRecipesJson = sharedPreferences.getString("seciliTarifler2", null)
        Log.d("YemeklerFragment", "Veri: $selectedRecipesJson")
        val selectedRecipes = if (selectedRecipesJson != null) {
            val type = object : TypeToken<List<Recipe>>() {}.type
            gson.fromJson(selectedRecipesJson, type)
        } else {
            ArrayList<Recipe>()
        }

        val adapter = YemeklerAdapter(requireContext(), selectedRecipes)
        favoriteRecyclerView.adapter = adapter
        return rootView
    }

         */

        private val sharedViewModel: SharedViewModel by activityViewModels()
        private lateinit var favoriteRecyclerView: RecyclerView
        private lateinit var binding: FragmentYemeklerBinding

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            val rootView = inflater.inflate(R.layout.fragment_yemekler, container, false)
            favoriteRecyclerView = rootView.findViewById(R.id.rvYemekler)
            favoriteRecyclerView.layoutManager = LinearLayoutManager(requireContext())

            binding = FragmentYemeklerBinding.inflate(inflater, container, false)

            // Seçilen tarifleri alın
            // val selectedRecipes = sharedViewModel.selectedRecipeList.value ?: emptyList()

            //   val adapter =
            //     TatlilarAdapter(requireContext(), emptyList()) // Boş bir başlangıç liste ile başla

            val sharedPreferences =
                requireActivity().getSharedPreferences("My AppPreferences2", Context.MODE_PRIVATE)
            val gson = Gson()

            val selectedRecipesJson = sharedPreferences.getString("seciliTarifler2", null)
            Log.d("TatlilarFragment", "Veri: $selectedRecipesJson")
            val selectedRecipes0 = if (selectedRecipesJson != null) {
                val type = object : TypeToken<List<Recipe>>() {}.type
                gson.fromJson(selectedRecipesJson, type)
            } else {
                ArrayList<Recipe>()
            }

            val selectedRecipes = loadFavoriteRecipesFromStorage().toMutableList()
            val selectedRecipes2 = loadFavoriteRecipesFromStorage2().toMutableList()

                    val adapter = YemeklerAdapter(requireContext(),
                        selectedRecipes0,
                        onFavoriteClickListener = { clickedRecipe ->
                            // Favorilere tatlı eklemek için işlemleri gerçekleştir
                            //  var selectedRecipes = sharedViewModel.selectedRecipeList.value?.toMutableList() ?: mutableListOf()
                            selectedRecipes.add(clickedRecipe)
                            sharedViewModel.updateSelectedRecipeList(selectedRecipes)
                            Log.d("Home", "VeriAddTatli: $selectedRecipes")
                            saveFavoriteRecipesToStorage(selectedRecipes)


                        },
                        onFavoriteClickListener2 = { clickedRecipe ->
                            // Favorilerden çıkarmak için işlemleri gerçekleştir
                            if (selectedRecipes.contains(clickedRecipe)) {
                                selectedRecipes.remove(clickedRecipe)
                            } else {
                                selectedRecipes2.remove(clickedRecipe)
                            }

                            val x = sharedViewModel.updateSelectedRecipeList(selectedRecipes)
                            Log.d("Home", "VeriRemove: $selectedRecipes")
                            saveFavoriteRecipesToStorage2(x) // Favorileri güncelleyin
                        },

                        onFavoriteClickListener3 = { clickedRecipe ->
                            // Favorilere yemek eklemek için işlemleri gerçekleştir
                            selectedRecipes2.add(clickedRecipe)
                            val x = sharedViewModel.updateSelectedRecipeList(selectedRecipes2)
                            Log.d("Home", "VeriAddYemek: $selectedRecipes2")
                            saveFavoriteRecipesToStorage2(x) // Favorileri güncelleyin
                        }
                    )
                    favoriteRecyclerView.adapter = adapter
                    /*

            sharedViewModel.selectedRecipeList.observe(viewLifecycleOwner) { recipes ->

                Log.d("TatlilarFragment", "LiveData Güncellendi. Yeni Liste: ${recipes}")
                if (recipes != null) {
                    adapter.updateRecipes(recipes) // TatlilarAdapter içindeki verileri güncelle
                    binding.rvTatlilar.adapter = adapter
                    favoriteRecyclerView.adapter = adapter
                    //  }
                }
             */
                    //   val adapter = TatlilarAdapter(requireContext(), selectedRecipes)
                    // favoriteRecyclerView.adapter = adapter
            return rootView
                }

        fun loadFavoriteRecipesFromStorage(): List<Recipe> {
            val sharedPreferences = context?.getSharedPreferences("My AppPreferences", Context.MODE_PRIVATE)
            val json = sharedPreferences?.getString("seciliTarifler", "")
            val type = object : TypeToken<List<Recipe>>() {}.type
            return Gson().fromJson(json, type) ?: mutableListOf()
        }
        fun loadFavoriteRecipesFromStorage2(): List<Recipe> {
            val sharedPreferences = context?.getSharedPreferences("My AppPreferences2", Context.MODE_PRIVATE)
            val json = sharedPreferences?.getString("seciliTarifler2", "")
            val type = object : TypeToken<List<Recipe>>() {}.type
            return Gson().fromJson(json, type) ?: mutableListOf()
        }


        fun saveFavoriteRecipesToStorage(selectedRecipes: List<Recipe>) {
            /*
            val sharedPreferences = requireActivity().getSharedPreferences(
               "My AppPreferences",
               Context.MODE_PRIVATE
            )//mode private: herhangi bir silme işlemi olduğunda bu veriyi en son  sil anlamına gelir. Önemli veri olduğu için
            val editor = sharedPreferences.edit()//veri kaydı değişikliği yetkisi almak için

            val gson = Gson()
            val selectedRecipesJson = gson.toJson(selectedRecipes)
            editor.putString("seciliTarifler", selectedRecipesJson)
            editor.apply()

             */
            Log.d("Home", "Veri3: $selectedRecipes")
            val sharedPreferences = context?.getSharedPreferences("My AppPreferences", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            val json = Gson().toJson(selectedRecipes)
            editor?.putString("seciliTarifler", json)
            editor?.apply()
            Log.d("Home", "VeriSavedTatlı: $selectedRecipes")

        }

        fun saveFavoriteRecipesToStorage2(selectedRecipes: List<Recipe>) {
            Log.d("Home", "Veri4: $selectedRecipes")
            val sharedPreferences = context?.getSharedPreferences("My AppPreferences2", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            val json = Gson().toJson(selectedRecipes)
            editor?.putString("seciliTarifler2", json)
            editor?.apply()
            Log.d("Home", "VeriSavedYemek: $selectedRecipes")
        }


    }



