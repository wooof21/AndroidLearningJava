package com.androidlearning.recyclerview.advanced.multipleviewtype;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.databinding.AdvRvMultiTypeItemCallBinding;
import com.androidlearning.databinding.AdvRvMultiTypeItemEmailBinding;

import java.util.List;

/**
 * user RecyclerView.ViewHolder instead of the specific class ViewHolder
 * inorder to switch between different ViewHolder with different view types
 */
public class MultiViewTypeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private AdvRvMultiTypeItemCallBinding phoneBinding;
    private AdvRvMultiTypeItemEmailBinding emailBinding;
    private List<EmployeeMultiType> employees;

    private static int TYPE_PHONE = 1;
    private static int TYPE_EMAIL = 2;

    public MultiViewTypeAdapter(List<EmployeeMultiType> employees) {
        this.employees = employees;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        phoneBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adv_rv_multi_type_item_call,
                parent, false
        );

        emailBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.adv_rv_multi_type_item_email,
                parent, false
        );

        if(viewType == TYPE_PHONE) {
            return new ViewHolderPhone(phoneBinding);
        }

        return new ViewHolderEmail(emailBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == TYPE_PHONE) {
            ((ViewHolderPhone) holder).phoneBinding.setEmployeePhone((EmployeeWithPhone) employees.get(position));
        } else {
            ((ViewHolderEmail) holder).emailBinding.setEmployeeEmail((EmployeeWithEmail) employees.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        EmployeeMultiType employee = employees.get(position);
        return employee instanceof EmployeeWithEmail ? TYPE_EMAIL : TYPE_PHONE;
    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class ViewHolderPhone extends RecyclerView.ViewHolder {

        private AdvRvMultiTypeItemCallBinding phoneBinding;

        public ViewHolderPhone(AdvRvMultiTypeItemCallBinding phoneBinding) {
            super(phoneBinding.getRoot());
            this.phoneBinding = phoneBinding;
        }
    }

    public class ViewHolderEmail extends RecyclerView.ViewHolder {

        private AdvRvMultiTypeItemEmailBinding emailBinding;

        public ViewHolderEmail(AdvRvMultiTypeItemEmailBinding emailBinding) {
            super(emailBinding.getRoot());
            this.emailBinding = emailBinding;
        }
    }
}
