package com.example.myrecipesapp.ui.fragment

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import com.airbnb.lottie.LottieAnimationView
import com.example.myrecipesapp.R
import com.example.myrecipesapp.databinding.FragmentHomePageBinding
import com.example.myrecipesapp.databinding.RecipeCardDesignBinding
import com.example.myrecipesapp.ui.adapter.RecipesAdapter
import com.example.myrecipesapp.ui.viewModel.HomePageViewModel
import com.example.recipe_app.util.pass
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomePageFragment : Fragment() ,SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentHomePageBinding

    private lateinit var viewModel: HomePageViewModel
    private lateinit var lottieAnimationView: LottieAnimationView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding =FragmentHomePageBinding.inflate(inflater,container,false)
        binding.passingAnimationLayout.visibility=View.INVISIBLE
        binding.toolbarHomepage.title = "Tariflerim"
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarHomepage)

       // binding.rv.layoutManager=LinearLayoutManager(requireContext())
        viewModel.recipesList.observe(viewLifecycleOwner){
            if(it!=null){
                val adapter = RecipesAdapter(requireContext(),it,viewModel)
                binding.rv.adapter = adapter
            }else {
                Snackbar.make(requireView(),"liste boş", Snackbar.LENGTH_SHORT).show()
            }
        }

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

}

