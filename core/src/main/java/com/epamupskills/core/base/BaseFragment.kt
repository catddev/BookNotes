package com.epamupskills.core.base

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.epamupskills.core.Navigate
import com.epamupskills.core.NavigateTo
import com.epamupskills.core.NavigateToGraph
import com.epamupskills.core.NavigateUp
import com.epamupskills.core.NavigateWithNestedNavHost
import com.epamupskills.core.NavigationEvent
import com.epamupskills.core.Navigator
import com.epamupskills.core.presentation.InputManagerImpl
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    @Inject
    lateinit var inputManager: InputManagerImpl

    protected fun initBaseObservers(
        viewModel: BaseViewModel,
        loader: View? = null,
        errorView: View? = null
    ) {
        lifecycleScope.launch {
            viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                renderLoader(loader, isLoading)
            }

            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    viewModel.navEvents.collect { event -> onNavigateByEvent(event) }
                }
                launch {
                    viewModel.errorMessage.collect { message ->
                        message?.let { showSnackBar(it) }
                    }
                }
                launch {
                    viewModel.hasError.collect { hasError ->
                        renderErrorView(hasError, errorView)
                    }
                }
            }
        }
    }

    protected fun hideKeyboard() {
        inputManager.hideKeyboard(view?.windowToken, 0)
    }

    private fun renderLoader(loader: View?, isLoading: Boolean) {
        loader?.isVisible = isLoading
    }

    private fun renderErrorView(hasError: Boolean, errorView: View?) {
        errorView?.isVisible = hasError
    }

    private fun showSnackBar(message: String) = view?.let { root ->
        Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun onNavigateByEvent(event: NavigationEvent) {
        when (event) {
            is Navigate -> getNavController(event.isRoot).navigate(event.destinationId)
            is NavigateTo -> getNavController(event.isRoot).navigate(event.direction)
            is NavigateUp -> getNavController(event.isRoot).navigateUp()
            is NavigateToGraph -> getNavController(true).setGraph(event.graphId)
            is NavigateWithNestedNavHost -> getNestedNavController(event.navHostId).navigate(event.direction)
        }
    }

    private fun getNavController(rootGraph: Boolean): NavController =
        if (rootGraph) (requireActivity() as Navigator).getRootNavController() else findNavController()

    private fun getNestedNavController(containerId: Int) =
        (childFragmentManager.findFragmentById(containerId) as NavHostFragment).navController
}