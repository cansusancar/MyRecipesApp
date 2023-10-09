package com.example.myrecipesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.airbnb.lottie.LottieAnimationView
import com.example.myrecipesapp.databinding.ActivitySplashScreenBinding
import com.example.myrecipesapp.databinding.FragmentHomePageBinding

class SplashScreen : AppCompatActivity() {
    private lateinit var lottieAnimationView: LottieAnimationView
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        lottieAnimationView = binding.myAnimation
        lottieAnimationView.playAnimation()
        // Splash ekranını belirli bir süre sonra ana uygulama ekranına geçiş yapacak şekilde ayarlayabilirsiniz.
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 4000) // 3000 milisaniye (3 saniye) sonra geçiş yapacak
    }
}
