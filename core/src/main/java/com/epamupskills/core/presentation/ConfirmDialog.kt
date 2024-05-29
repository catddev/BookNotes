package com.epamupskills.core.presentation

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.epamupskills.core.R

class ConfirmDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.confirm_dialog_title)
            setPositiveButton(R.string.confirm_button_title) { _, _ ->
                setFragmentResult(RESULT_KEY, bundleOf(RESULT_KEY to true))
            }
            setNegativeButton(R.string.cancel_button_title) { _, _ -> dismiss() }
        }.create()

    companion object {
        const val RESULT_KEY = "ConfirmDialogResultKey"
    }
}