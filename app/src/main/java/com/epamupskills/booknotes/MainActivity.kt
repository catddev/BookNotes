package com.epamupskills.booknotes

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.epamupskills.booknotes.databinding.ActivityMainBinding
import com.epamupskills.core.Navigate
import com.epamupskills.core.NavigateTo
import com.epamupskills.core.NavigateToGraph
import com.epamupskills.core.NavigateUp
import com.epamupskills.core.NavigateWithNestedNavHost
import com.epamupskills.core.NavigationEvent
import com.epamupskills.core.Navigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Navigator {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val navController by lazy { getNavHostFragment(R.id.nav_host_container).navController }
    private val viewModel: MainViewModel by viewModels()

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
        initViews()
        setListeners()
        initObservers()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun getRootNavController(): NavController = navController

    private fun initObservers() {
        viewModel.isAuth.observe(this, ::renderViews)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.navEvents.collect { event -> onNavigationEvent(event) }
            }
        }
    }

    private fun setListeners() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            navController.run {
                when (item.itemId) {
                    R.id.books -> navigate(R.id.to_book_notes)
                    R.id.search -> navigate(R.id.to_search)
                    R.id.profile -> navigate(R.id.to_profile)
                }
            }
            return@setOnItemSelectedListener true
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            binding.bottomNavigation.run {
                when (destination.parent?.id) {
                    com.epamupskills.book_notes.R.id.search_nav -> {
                        menu.findItem(R.id.search).isChecked = true
                    }

                    com.epamupskills.authorization.R.id.profile_nav -> {
                        menu.findItem(R.id.profile).isChecked = true
                    }

                    com.epamupskills.book_notes.R.id.book_notes_nav -> {
                        menu.findItem(R.id.books).isChecked = true
                    }
                }
            }
        }
    }

    private fun onNavigationEvent(event: NavigationEvent) {
        when (event) {
            is Navigate -> navController.navigate(event.destinationId)
            is NavigateToGraph -> navController.setGraph(event.graphId)
            is NavigateTo -> navController.navigate(event.direction)
            is NavigateUp -> navController.navigateUp()
            is NavigateWithNestedNavHost ->
                getNavHostFragment(event.navHostId).navController.navigate(event.direction)
        }
    }

    private fun getNavHostFragment(id: Int): NavHostFragment =
        supportFragmentManager.findFragmentById(id) as NavHostFragment

    private fun initViews() {
        with(binding.bottomNavigation) {
            setupWithNavController(navController)
            NavigationUI.setupWithNavController(this, navController)
        }
    }

    private fun renderViews(isVisible: Boolean) {
        binding.bottomNavigation.isVisible = isVisible
    }
}