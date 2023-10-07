package com.manoj.sneakersshopping.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.manoj.sneakersshopping.R
import com.manoj.sneakersshopping.databinding.ActivityMainBinding
import com.manoj.sneakersshopping.ui.models.SneakersModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    companion object{
        var cartItems: MutableList<SneakersModel> = ArrayList<SneakersModel>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController() ?: return
        binding.bottomNavigationView.setupWithNavController(navController)

    }

    private fun findNavController(): NavController? {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        return navHostFragment?.navController
    }

}