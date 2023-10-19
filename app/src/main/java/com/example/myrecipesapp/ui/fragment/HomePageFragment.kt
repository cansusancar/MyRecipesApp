package com.example.myrecipesapp.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import com.airbnb.lottie.LottieAnimationView
import com.example.myrecipesapp.R
import com.example.myrecipesapp.RecipesActivity
import com.example.myrecipesapp.data.entity.Recipe
import com.example.myrecipesapp.databinding.FragmentHomePageBinding
import com.example.myrecipesapp.ui.adapter.RecipesAdapter
import com.example.myrecipesapp.ui.viewModel.HomePageViewModel
import com.example.myrecipesapp.ui.viewModel.SharedViewModel
import com.example.recipe_app.util.pass
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import com.example.myrecipesapp.databinding.RecipeCardDesignBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


@AndroidEntryPoint
class HomePageFragment : Fragment()
    ,SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentHomePageBinding
    private lateinit var binding2: RecipeCardDesignBinding
    private lateinit var viewModel: HomePageViewModel
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var lottieAnimationView: LottieAnimationView
    private var arrayList = ArrayList<Recipe>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomePageBinding.inflate(inflater, container, false)
        binding2 = RecipeCardDesignBinding.inflate(inflater, container, false)
        binding.passingAnimationLayout.visibility = View.INVISIBLE
        binding.toolbarHomepage.title = "Tariflerim"
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarHomepage)
      //  val sharedPreferences = requireActivity().getSharedPreferences(
        //    "My AppPreferences",
          //  Context.MODE_PRIVATE
       //mode private: herhangi bir silme işlemi olduğunda bu veriyi en son  sil anlamına gelir. Önemli veri olduğu için
        //val editor = sharedPreferences.edit()//veri kaydı değişikliği yetkisi almak için

        // AŞAĞIDAKİ KISIM APİDEN ALINAN VERİLERİN TAMAMINI HOMEPAGEFRAGMENT'TAKİ RECYCLERVIEW'E AKTARMAK İÇİN:

        /*
viewModel.recipesList.observe(viewLifecycleOwner) { recipes ->
    if (recipes != null) {
        val adapter = RecipesAdapter(
            requireContext(),
            recipes,
            viewModel,
            onFavoriteClickListener = { clickedRecipe ->
                // Favorilere eklemek için clickedRecipe'yi kullanarak işlemleri gerçekleştir
                var selectedRecipes = sharedViewModel.selectedRecipeList.value?.toMutableList() ?: mutableListOf()
                selectedRecipes.add(clickedRecipe)

                Log.d(
                    "HomePageFragment",
                    "Tıklamadan Sonra selectedRecipes: $selectedRecipes"
                )
                sharedViewModel.updateSelectedRecipeList(selectedRecipes)
                val gson = Gson()
                val selectedRecipesJson = gson.toJson(selectedRecipes)
                editor.putString("seciliTarifler", selectedRecipesJson)
                editor.apply()
            },
            onFavoriteClickListener2 = { clickedRecipe ->
                // Favorilerden çıkarmak için clickedRecipe'yi kullanarak işlemleri gerçekleştir
                var selectedRecipes = sharedViewModel.selectedRecipeList.value?.toMutableList() ?: mutableListOf()
                selectedRecipes.remove(clickedRecipe)
                Log.d(
                    "HomePageFragment",
                    "Tıklamadan Sonra selectedRecipes: $selectedRecipes"
                )
                sharedViewModel.updateSelectedRecipeList(selectedRecipes)
                val gson = Gson()
                val selectedRecipesJson = gson.toJson(selectedRecipes)
                editor.putString("seciliTarifler", selectedRecipesJson)
                editor.apply()

            }
        )
        binding.rv.adapter = adapter
    } else {
        Snackbar.make(requireView(), "liste boş", Snackbar.LENGTH_SHORT).show()
    }
}


         */


        val selectedRecipes = loadFavoriteRecipesFromStorage().toMutableList()
        val selectedRecipes2 = loadFavoriteRecipesFromStorage2().toMutableList()
        Log.d("Home", "Veri1: $selectedRecipes")
        viewModel.recipesList.observe(viewLifecycleOwner) { recipes ->
            if (recipes != null) {
                val adapter = RecipesAdapter(
                    requireContext(),
                    recipes,
                    viewModel,
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

                       val x= sharedViewModel.updateSelectedRecipeList(selectedRecipes)
                        Log.d("Home", "VeriRemove: $selectedRecipes")
                        saveFavoriteRecipesToStorage(x) // Favorileri güncelleyin
                    },

                    onFavoriteClickListener3 = { clickedRecipe ->
                        // Favorilere yemek eklemek için işlemleri gerçekleştir
                        selectedRecipes2.add(clickedRecipe)
                        val x= sharedViewModel.updateSelectedRecipeList(selectedRecipes2)
                        Log.d("Home", "VeriAddYemek: $selectedRecipes2")
                        saveFavoriteRecipesToStorage2(x) // Favorileri güncelleyin
                    }
                )
                binding.rv.adapter = adapter
            } else {
                Snackbar.make(requireView(), "liste boş", Snackbar.LENGTH_SHORT).show()
            }
        }






//val adapter2 = FavRecipesAdapter(requireContext(),it,viewModel)
        //binding2.rv2.adapter= adapter2
        //FavRecipesAdapter oluşturma ve düzenleme
        lottieAnimationView = binding.myAnimation

        binding.fab.setOnClickListener{
            /*
            val parentView = view?.parent as ViewGroup
            for (i in 0 until parentView.childCount) {
                val child = parentView.getChildAt(i)
                if (child != lottieAnimationView && child != waitText) {
                    child.visibility = View.GONE
                }
            }
             */
            lottieAnimationView.playAnimation()
            binding.recipesLayout.visibility=View.INVISIBLE
            binding.passingAnimationLayout.visibility =View.VISIBLE

           // lottieAnimationView.visibility = View.VISIBLE
            //waitText.visibility = View.VISIBLE




            Handler(Looper.getMainLooper()).postDelayed({
                Navigation.pass(R.id.homePageFragmentToAddFragment,it)
              //  Navigation.findNavController(binding)
                 //   .navigate(R.id.homePageFragmentToAddFragment)
            }, 1000)
           // Navigation.pass(R.id.homePageFragmentToAddFragment,it)
        }

        requireActivity().addMenuProvider(object :MenuProvider{
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu,menu)
                val item=menu.findItem(R.id.action_search)
                val searchView=item.actionView as SearchView
                searchView.setOnQueryTextListener(this@HomePageFragment)


                }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        },viewLifecycleOwner,Lifecycle.State.RESUMED)


        return binding.root
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : HomePageViewModel by viewModels ()
        viewModel = tempViewModel
    }


    override fun onResume() {
        super.onResume()
        viewModel.modelLoadRecipes()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        viewModel.modelSearch(query.toString())
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.modelSearch(newText.toString())
        return true
    }
    // Favori tarifleri SharedPreferences'ten yükleme
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

    // Favori tarifleri SharedPreferences'e kaydetme
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



