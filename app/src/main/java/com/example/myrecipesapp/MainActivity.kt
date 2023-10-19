package com.example.myrecipesapp



import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.myrecipesapp.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar2.title = "Tariflerim Uygulaması"
        val toggle = ActionBarDrawerToggle(this, binding.drawer, binding.toolbar2, 0, 0)
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()

        val baslik = binding.navigationView.inflateHeaderView(R.layout.navigation_head)

        val textViewBaslik = baslik.findViewById(R.id.textViewHead) as TextView

        textViewBaslik.text = "Hoşgeldiniz"
        val navView = findViewById<NavigationView>(R.id.navigationView)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawer)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favoriteActivity -> {
                    // Activity 1'i başlat
                    startActivity(Intent(this, FavoriteActivity::class.java))
                }
                R.id.recipesActivity -> {
                    startActivity(Intent(this, RecipesActivity::class.java))
                }

            }

            // Drawer'ı kapat
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


    }
    //Menun açık geri gittiğimde uygulamayı kapatmak yerine ana fragmentım açılsın:
    override fun onBackPressed() {
        if(binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawer(GravityCompat.START)}
        else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}
