package com.epamupskills.core.base

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import com.epamupskills.core.AppRouter
import com.epamupskills.core.NavigateWithConfig
import com.epamupskills.core.NavigationEvent
import com.epamupskills.core.presentation.InputManagerImpl
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    @Inject
    lateinit var router: AppRouter

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
        val navEvent = if (event is NavigateWithConfig) {
            when (requireContext().resources.getBoolean(event.configBoolRes)) {
                true -> event.onConfigTrueNavEvent
                false -> event.onConfigFalseNavEvent
            }
        } else event

        val childNavController =
            if (navEvent.needChildNavController) getNestedNavController(navEvent.navHostId) else null

        router.navigateOnEvent(
            event = navEvent,
            childNavController = childNavController,
        )
    }

    private fun getNestedNavController(containerId: Int) =
        (childFragmentManager.findFragmentById(containerId) as NavHostFragment).navController
}