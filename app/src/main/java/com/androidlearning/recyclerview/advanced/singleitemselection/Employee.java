package com.androidlearning.recyclerview.advanced.singleitemselection;

import androidx.annotation.NonNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class Employee {

    @Getter
    @Setter
    private String empName;
    private Boolean isChecked;

    @Builder
    public Employee(String empName) {
        this.empName = empName;
        setChecked(false);
    }

    public Boolean isChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    @NonNull
    @Override
    public String toString() {
        return "Employee=(empName: " + getEmpName() + ", isChecked: " + isChecked() + ")";
    }
}
