package com.androidlearning.recyclerview.advanced.multiitemselection;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.databinding.AdvRvSingleSelItemEmployeeBinding;
import com.androidlearning.recyclerview.advanced.singleitemselection.Employee;
import com.androidlearning.recyclerview.advanced.singleitemselection.EmployeeAdapter;

import java.util.List;
import java.util.Map;

public class MultiSelectedAdapter extends RecyclerView.Adapter<MultiSelectedAdapter.ViewHolder> {

    private AdvRvSingleSelItemEmployeeBinding binding;
    private List<Employee> employees;
    private RVMultiSelectedClickedListener clickedListener;

    public MultiSelectedAdapter(List<Employee> employees) {
        this.employees = employees;
    }

    public void setMultiSelectedClickedListener(RVMultiSelectedClickedListener clickedListener) {
        this.clickedListener = clickedListener;
    }

    public void getSelectedEmployees(Map<Integer, Employee> selected, int position) {
        Employee current = employees.get(position);
        current.setChecked(!current.isChecked());
        if(selected.containsKey(position)) {
            selected.remove(position);
        } else {
            selected.put(position, current);
        }
        notifyDataSetChanged();
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
        Employee employee = employees.get(position);
        holder.binding.advRvSingleSelEmpName.setText(employee.getEmpName());
        if(employee.isChecked()) {
            holder.binding.advRvSingleSelImageview.setVisibility(View.VISIBLE);
        } else {
            holder.binding.advRvSingleSelImageview.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return employees.size();
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
            if(clickedListener != null) {
                clickedListener.onRVItemClickedListener(view, getBindingAdapterPosition());
            }
        }
    }
}
