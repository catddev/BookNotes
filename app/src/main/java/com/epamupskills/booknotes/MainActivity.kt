package com.epamupskills.booknotes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.epamupskills.booknotes.databinding.ActivityMainBinding
import com.epamupskills.core.presentation.Navigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigator {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { initNavController() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.nav_host_container)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setListeners()
        //todo set navigation gone when destination is Authorization nav
        //todo enable SavedBackstack
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun getRootNavController(): NavController = navController

    private fun setListeners() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            navController.run {
                when (item.itemId) {
                    R.id.books -> navigate(R.id.to_book_notes)
                    R.id.profile -> navigate(R.id.profileFragment)
                    R.id.search -> navigate(R.id.searchBooksFragment)
                }
            }
            return@setOnItemSelectedListener true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.menu.run {
                when {
                    destination.id == R.id.searchBooksFragment -> {
                        findItem(R.id.search).isChecked = true
                    }

                    destination.id == R.id.profileFragment -> {
                        findItem(R.id.profile).isChecked = true
                    }

                    destination.parent?.id == com.epamupskills.book_notes.R.id.book_notes_nav -> {
                        findItem(R.id.books).isChecked = true
                    }
                }
            }
        }
    }

    private fun initNavController(): NavController =
        (supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment).navController

    private fun renderLoader(isLoading: Boolean) {
        binding.loader.isVisible = isLoading
    }

    private fun renderError(hasError: Boolean) {
        binding.errorView.isVisible = hasError
    }
}