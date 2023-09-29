package com.example.myrecipesapp.ui.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.myrecipesapp.data.entity.Recipe
import com.example.myrecipesapp.databinding.RecipeCardDesignBinding
import com.example.myrecipesapp.ui.fragment.HomePageFragmentDirections
import com.example.myrecipesapp.ui.viewModel.HomePageViewModel
import com.example.recipe_app.util.pass
import com.google.android.material.snackbar.Snackbar


class RecipesAdapter(var mContext: Context,
                         var recipesList:List<Recipe>,
                         var viewModel: HomePageViewModel
    )
        : RecyclerView.Adapter<RecipesAdapter.CardDesignHolder>() {

        inner class CardDesignHolder(design: RecipeCardDesignBinding) : RecyclerView.ViewHolder(design.root) {

            var design: RecipeCardDesignBinding

            init {
              this.design= design
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesAdapter.CardDesignHolder {
            val layoutInflater = LayoutInflater.from(mContext)
            val design = RecipeCardDesignBinding.inflate(LayoutInflater.from(parent.context),parent,false)
            return CardDesignHolder(design)
        }

        override fun getItemCount(): Int {
            return recipesList.size
        }

        override fun onBindViewHolder(holder: RecipesAdapter.CardDesignHolder, position: Int) {
            val recipe = recipesList.get(position)

            val t = holder.design


            t.textViewRecipeInfo.text= "${recipe.id}- ${recipe.name}"


            t.satirCard.setOnClickListener {
                Log.e ("Recipe Adapter", "satırCard tıklandı")
                val passing = HomePageFragmentDirections.homePageFragmentToDetailFragment(id = recipe.id)
                Navigation.pass(passing,it)
               Log.e ("Recipe Adapter", "id nesnesi gönderildi")
                //putekstra ie recipe yi recipeFragment a gönder

            }
                holder.design.updateImage.setOnClickListener {
                    val passing = HomePageFragmentDirections.homePageFragmentToUpdateFragment(recipeId = recipe.id )
                    Navigation.pass(passing,it)
                }// buradan güncelleme ekranına gönderdin ve güncellemeden de repository classına göndereceksin

            t.deleteImage.setOnClickListener{
                /*
                val alert = AlertDialog.Builder(mContext)
                alert.setTitle("Silme")
                alert.setMessage("${recipe.name} silinsin mi ?")
                alert.setPositiveButton("Yes") {dialog, which ->
                    viewModel.modelDelete(recipe.id,recipe.name)
                }
                alert.setNegativeButton("No") {dialog, which ->
                    Toast.makeText(mContext,"${recipe.name} Silinmedi",Toast.LENGTH_LONG).show()
                }
                alert.show()

                 */
                Toast.makeText(mContext,"${recipe.name} Silinemez", Toast.LENGTH_LONG).show()
            }


        }
    }


