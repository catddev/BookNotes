package com.epamupskills.book_notes.presentation.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.epamupskills.book_notes.databinding.FragmentBooksBinding
import com.epamupskills.core.base.BaseFragment

class BooksFragment : BaseFragment() {

    private var _binding: FragmentBooksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentBooksBinding.inflate(layoutInflater).let {
        _binding = it
        binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initBaseObservers()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
        //todo for master details - navigate with navcontroller with container ID!
        //todo sw600!
        //todo for landscape todo stagged grid, span 2 or 3?
    }
}