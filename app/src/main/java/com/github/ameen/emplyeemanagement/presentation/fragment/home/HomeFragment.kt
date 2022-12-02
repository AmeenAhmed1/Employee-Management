package com.github.ameen.emplyeemanagement.presentation.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.ameen.emplyeemanagement.R
import com.github.ameen.emplyeemanagement.databinding.FragmentHomeBinding
import com.github.ameen.emplyeemanagement.domain.model.EmployeeDomainData
import com.github.ameen.emplyeemanagement.presentation.util.hide
import com.github.ameen.emplyeemanagement.presentation.util.show
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var employeeAdapter: EmployeeAdapter

    private var employeeSearchName: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()
        handleSearchEmployee()

        binding?.addEmployeeButton?.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToCreateEditFragment()
            findNavController().navigate(action)
        }
    }

    private fun initRecycler() {
        employeeAdapter = EmployeeAdapter()
        binding?.employeeRecycler?.apply {
            adapter = employeeAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }

        employeeAdapter.onItemClicked { selectedEmployeeData, isDelete ->
            if (isDelete) handleDeleteEmployee(selectedEmployeeData)
            else handleEditEmployee(selectedEmployeeData)
        }
    }

    private fun handleEditEmployee(employeeData: EmployeeDomainData) {
        val action = HomeFragmentDirections.actionHomeFragmentToCreateEditFragment(employeeData)
        findNavController().navigate(action)
    }

    private fun handleSearchEmployee() {
        binding?.searchEditText?.setOnEditorActionListener { textView, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (binding?.searchEditText?.text?.toString()?.isNotEmpty() == true) {
                    employeeSearchName = binding?.searchEditText?.text?.toString() ?: ""
                    getEmployees()
                }
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }

        binding?.searchEditText?.doOnTextChanged { text, start, before, count ->
            if (before == 1) text?.let {
                if (it.isEmpty()) {
                    employeeSearchName = ""
                    getEmployees()
                }
            }
        }
    }

    private fun handleDeleteEmployee(employeeDomainData: EmployeeDomainData) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Are you sure to delete ${employeeDomainData.employeeName}?")
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                lifecycleScope.launchWhenStarted {
                    homeViewModel.deleteEmployee(employeeDomainData).collectLatest {
                        getEmployees()
                    }
                }
            }
            .setNegativeButton(resources.getString(R.string.cancel)) { dialog, which ->
                dialog.cancel()
            }
            .show()
    }

    private fun getEmployees() {
        homeViewModel.getAllEmployees(employeeSearchName)

        lifecycleScope.launchWhenStarted {
            homeViewModel.employeeData.collectLatest {

                Timber.e("$it")

                if (it.isEmpty()) {
                    binding?.employeeRecycler?.hide()
                    binding?.noDataText?.show()
                } else {
                    binding?.employeeRecycler?.show()
                    binding?.noDataText?.hide()
                    employeeAdapter.diff.submitList(it)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        getEmployees()
    }

}