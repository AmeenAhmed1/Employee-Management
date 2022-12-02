package com.github.ameen.emplyeemanagement.presentation.fragment.create_edit_fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.ameen.emplyeemanagement.R
import com.github.ameen.emplyeemanagement.databinding.FragmentAddEditEmployeeBinding
import com.github.ameen.emplyeemanagement.domain.model.EmployeeDomainData
import com.github.ameen.emplyeemanagement.presentation.util.SelectImageUtil
import com.github.ameen.emplyeemanagement.presentation.util.loadEmployeeImage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber

@AndroidEntryPoint
class CreateEditFragment : Fragment() {

    private var _binding: FragmentAddEditEmployeeBinding? = null
    val binding get() = _binding

    private val createEditViewModel: CreateEditViewModel by viewModels()

    private val args by navArgs<CreateEditFragmentArgs>()

    private val employeeData: EmployeeDomainData? by lazy {
        args.employeeData
    }

    private val imageFileData = MutableLiveData<Uri>()
    lateinit var imageUtil: SelectImageUtil

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        imageUtil = SelectImageUtil(this, imageFileData)
        _binding = FragmentAddEditEmployeeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.saveEmployeeButton?.setOnClickListener {
            getInputData()
        }

        binding?.employeeImage?.setOnClickListener {
            handleEmployeeImage()
        }

        lifecycleScope.launchWhenStarted {
            createEditViewModel.employeeDataInsertionState.collectLatest {
                Timber.e("$it")

                if (it > 0)
                    findNavController().popBackStack()
            }
        }
    }

    private lateinit var inputData: EmployeeDomainData
    private fun getInputData() {

        var name: String = ""
        var skills: ArrayList<Int> = arrayListOf()

        val email: String? = if (binding?.employeeEmailInput?.text?.isNotEmpty() == true)
            binding?.employeeEmailInput?.text?.toString()
        else
            null

        val image: String? = if (imageFileData.value.toString().isBlank())
            imageFileData.value.toString()
        else
            null

        if (binding?.employeeNameInput?.text?.isNotEmpty() == true) {
            name = binding?.employeeNameInput?.text?.toString() ?: ""

            inputData = EmployeeDomainData(
                employeeName = name,
                employeeEmail = email,
                employeeImage = image
            )

            createEditViewModel.addNewEmployee(inputData)

        } else
            binding?.employeeName?.error = getString(R.string.name_is_required_error)


    }

    private fun handleEmployeeImage() {
        imageUtil.selectImage()

        imageFileData.observe(viewLifecycleOwner) {
            binding?.employeeImage?.loadEmployeeImage(it.toString())
        }
    }
}