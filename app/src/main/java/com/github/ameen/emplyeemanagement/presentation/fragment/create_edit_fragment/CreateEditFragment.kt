package com.github.ameen.emplyeemanagement.presentation.fragment.create_edit_fragment

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.ameen.emplyeemanagement.R
import com.github.ameen.emplyeemanagement.databinding.FragmentAddEditEmployeeBinding
import com.github.ameen.emplyeemanagement.domain.model.EmployeeDomainData
import com.github.ameen.emplyeemanagement.domain.model.SkillData
import com.github.ameen.emplyeemanagement.presentation.util.SelectImageUtil
import com.github.ameen.emplyeemanagement.presentation.util.loadEmployeeImage
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
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

    private val skills: ArrayList<SkillData> = arrayListOf()
    private val selectedSkills: ArrayList<Int> = arrayListOf()

    private var employeeImage: String? = null

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

        handleSkills()

        employeeData?.let {
            initViewForEdit(it)
        }


        binding?.saveEmployeeButton?.setOnClickListener {
            getInputData()

            Timber.e("Id: $selectedSkills")
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

    private fun initViewForEdit(selectedEmployeeData: EmployeeDomainData) {
        selectedEmployeeData.employeeImage?.let {
            binding?.employeeImage?.loadEmployeeImage(it)
            employeeImage = it
        }

        selectedEmployeeData.employeeName.let {
            binding?.employeeNameInput?.setText(it)
        }

        selectedEmployeeData.employeeEmail?.let {
            binding?.employeeEmailInput?.setText(it)
        }

        selectedEmployeeData.employeeSkills.forEach {
            getSkill(it)
        }


        inputData = selectedEmployeeData
    }

    private fun getSkill(skillId: Int) {
        lifecycleScope.launchWhenStarted {
            createEditViewModel.getSpecificSkill(skillId).collectLatest {
                skills.add(it)
                addSkillShip(it.skillName)
            }
        }
    }

    private lateinit var inputData: EmployeeDomainData
    private fun getInputData() {

        var name: String = ""

        val email: String? = if (binding?.employeeEmailInput?.text?.isNotEmpty() == true)
            binding?.employeeEmailInput?.text?.toString()
        else
            null

//        val image: String? = imageFileData.value.toString().ifBlank { null }

        if (binding?.employeeNameInput?.text?.isNotEmpty() == true) {
            name = binding?.employeeNameInput?.text?.toString() ?: ""

            if (employeeData != null)
                inputData = inputData.copy(
                    employeeName = name,
                    employeeEmail = email,
                    employeeImage = employeeImage,
                    employeeSkills = selectedSkills
                )
            else
                inputData = EmployeeDomainData(
                    employeeName = name,
                    employeeEmail = email,
                    employeeImage = employeeImage,
                    employeeSkills = selectedSkills
                )

            createEditViewModel.addNewEmployee(inputData)

        } else
            binding?.employeeName?.error = getString(R.string.name_is_required_error)


    }

    private fun handleSkills() {

        val skillsName: ArrayList<String> = arrayListOf()

        createEditViewModel.getAllSkills()

        lifecycleScope.launchWhenCreated {
            createEditViewModel.skillsDataState.collectLatest {
                if (it.isNotEmpty()) {
                    skills.addAll(it)

                    skills.forEach {
                        skillsName.add(it.skillName)
                    }
                }
            }
        }


        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            skillsName
        )

        binding?.skillsInput?.setAdapter(adapter)
        binding?.skillsInput?.setOnItemClickListener { parent, _, position, _ ->
            val selectedSkillPosition = parent.getItemAtPosition(position) as String
            addSkillShip(selectedSkillPosition)
        }
    }

    private fun addSkillShip(selectedSkill: String) {

        val selectedSkillIndex = skills.filter {
            it.skillName == selectedSkill
        }

        selectedSkills.add(selectedSkillIndex[0].skillId ?: 0)

        binding?.skillShips?.addView(getChip(selectedSkill))
    }

    private fun getChip(name: String): Chip {
        return Chip(requireContext()).apply {
            text = name
            isCloseIconVisible = true
            chipBackgroundColor = resources.getColorStateList(android.R.color.holo_blue_dark)
            setTextColor(resources.getColor(R.color.white))
            setOnCloseIconClickListener {
                (it.parent as ChipGroup).removeView(it)
            }
        }
    }

    private fun handleEmployeeImage() {
        imageUtil.selectImage()

        imageFileData.observe(viewLifecycleOwner) {
            binding?.employeeImage?.loadEmployeeImage(it.toString())
            employeeImage = it.toString()
        }
    }
}