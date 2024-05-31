package com.epamupskills.core.base

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.epamupskills.core.Navigate
import com.epamupskills.core.NavigateTo
import com.epamupskills.core.NavigateToGraph
import com.epamupskills.core.NavigateUp
import com.epamupskills.core.NavigationEvent
import com.epamupskills.core.Navigator
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

open class BaseFragment : Fragment() {

    //todo for all fragments - clip to padding bottom - size of bottom navigation!!!

    protected fun initBaseObservers(
        baseViewModel: BaseViewModel,
        loader: View? = null,
        errorView: View? = null
    ) {
        lifecycleScope.launch {
            baseViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
                renderLoader(loader, isLoading)
            }

            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                launch {
                    baseViewModel.navEvents.collect { event -> onNavigateByEvent(event) }
                }
                launch {
                    baseViewModel.errorState.collect { state ->
                        renderError(state, errorView)
                    }
                }
            }
        }
    }

    private fun renderLoader(loader: View?, isLoading: Boolean) {
        loader?.isVisible = isLoading
    }

    private fun renderError(state: BaseErrorState, errorView: View?) {
        errorView?.isVisible = state.hasError
        if (state.hasError) showSnackBar(state.errorMessage)
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
        }
    }

    protected fun hideKeyboard() {
        val inputManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    private fun getNavController(rootGraph: Boolean): NavController =
        if (rootGraph) (requireActivity() as Navigator).getRootNavController() else findNavController()
}