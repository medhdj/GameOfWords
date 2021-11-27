package com.medhdj.gow.features.fizzbuzz

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.medhdj.gow.databinding.FragmentResultBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResultFragment : Fragment() {
    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    private val resultViewModel by viewModels<ResultViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.bindResultList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun FragmentResultBinding.bindResultList() {
        resultList.layoutManager = LinearLayoutManager(requireContext())
        val fizzbuzzAdapter = FizzBuzzResultAdapter()
        resultList.adapter = fizzbuzzAdapter

        resultViewModel.pagingData.observe(viewLifecycleOwner) { pagingDataFlow ->
            lifecycleScope.launch {
                pagingDataFlow.collectLatest(fizzbuzzAdapter::submitData)
            }
        }
    }
}
