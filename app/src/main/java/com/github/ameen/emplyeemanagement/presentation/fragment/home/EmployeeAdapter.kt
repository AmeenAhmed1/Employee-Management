package com.github.ameen.emplyeemanagement.presentation.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.github.ameen.emplyeemanagement.databinding.ItemEmployeeBinding
import com.github.ameen.emplyeemanagement.domain.model.EmployeeDomainData
import com.github.ameen.emplyeemanagement.presentation.util.loadEmployeeImage
import com.github.ameen.emplyeemanagement.presentation.util.show

class EmployeeAdapter() : RecyclerView.Adapter<EmployeeAdapter.EmployeeItemViewHolder>() {

    inner class EmployeeItemViewHolder(val binding: ItemEmployeeBinding) :
        RecyclerView.ViewHolder(binding.root)

    private var _binding: ItemEmployeeBinding? = null

    private val differCallBack = object : DiffUtil.ItemCallback<EmployeeDomainData>() {
        override fun areItemsTheSame(
            oldItem: EmployeeDomainData,
            newItem: EmployeeDomainData
        ): Boolean {
            return oldItem.employeeId == newItem.employeeId
        }

        override fun areContentsTheSame(
            oldItem: EmployeeDomainData,
            newItem: EmployeeDomainData
        ): Boolean {
            return oldItem == newItem
        }
    }

    val diff = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeItemViewHolder {
        _binding = ItemEmployeeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EmployeeItemViewHolder(_binding!!)
    }

    override fun onBindViewHolder(holder: EmployeeItemViewHolder, position: Int) {
        val currentEmployee = diff.currentList[position]

        holder.binding.employeeName.text = currentEmployee.employeeName

        if (currentEmployee.employeeEmail != null) {
            holder.binding.employeeEmail.show()
            holder.binding.employeeEmail.text = currentEmployee.employeeEmail
        }

        currentEmployee.employeeImage?.let {
            holder.binding.employeeImage.loadEmployeeImage(currentEmployee.employeeImage)
        }

    }

    override fun getItemCount(): Int = diff.currentList.size

    private var onItemClickListener: ((EmployeeDomainData) -> Unit)? = null
    fun onItemClicked(listener: (EmployeeDomainData) -> Unit) {
        onItemClickListener = listener
    }
}