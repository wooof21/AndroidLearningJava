package com.androidlearning.recyclerview.advanced.singleitemselection;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.databinding.AdvRvSingleSelItemEmployeeBinding;

import java.util.List;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.ViewHolder> {

    private AdvRvSingleSelItemEmployeeBinding binding;
    private List<Employee> employeeList;
    private RVSingleItemClickListener clickListener;
    private int lastCheckPosition = -1;

    public EmployeeAdapter(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public void setOnSingleItemClickedListener(RVSingleItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public Employee getSelectedEmployeeItem(int position) {
        Employee selEmp = employeeList.get(position);
        selEmp.setChecked(!selEmp.isChecked());

        if(lastCheckPosition != -1 && lastCheckPosition != position) {
            Employee last = employeeList.get(lastCheckPosition);
            last.setChecked(!last.isChecked());
        }
        lastCheckPosition = position;
        notifyDataSetChanged();
        if(!selEmp.isChecked()) {
            lastCheckPosition = -1;
            return null;
        }
        return selEmp;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adv_rv_single_sel_item_employee,
                parent, false
        );
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Employee employee = employeeList.get(position);
        holder.binding.advRvSingleSelEmpName.setText(employee.getEmpName());
        if(employee.isChecked()) {
            holder.binding.advRvSingleSelImageview.setVisibility(View.VISIBLE);
        } else {
            holder.binding.advRvSingleSelImageview.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return employeeList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private AdvRvSingleSelItemEmployeeBinding binding;

        public ViewHolder(AdvRvSingleSelItemEmployeeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.binding.advRvSingleSelLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(clickListener != null) {
                Log.i("RVSingleItemClickListener BindingAdapterPosition: ", ""+getBindingAdapterPosition());
                Log.i("RVSingleItemClickListener AbsoluteAdapterPosition: ", ""+getAbsoluteAdapterPosition());
                clickListener.onSingleItemClicked(view, getBindingAdapterPosition());
            }
        }
    }
}
