package com.androidlearning.recyclerview.advanced.singleitemselection;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.databinding.AdvRvSingleSelItemEmployeeBinding;

import java.util.ArrayList;
import java.util.List;

public class RVSingleSelActivity extends AppCompatActivity implements RVSingleItemClickListener{

    private Button selected;
    private RecyclerView recyclerView;
    private List<Employee> employees;
    private EmployeeAdapter adapter;
    private Employee selectedEmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rvsingle_sel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        selected = findViewById(R.id.rv_single_sel_btn);

        recyclerView = findViewById(R.id.rv_single_sel_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        employees = createEmployeeList();
        adapter = new EmployeeAdapter(employees);
        recyclerView.setAdapter(adapter);

        adapter.setOnSingleItemClickedListener(this);

        selected.setOnClickListener(v -> {
            if(selectedEmp == null) {
                Toast.makeText(this, "No Employee Selected", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Selected Employee: " + selectedEmp.getEmpName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Employee> createEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();
        for(int i=0; i<5; i++) {
            Employee employee = Employee.builder().empName("Employee " + i).build();
            employeeList.add(employee);
        }
        return employeeList;
    }

    @Override
    public void onSingleItemClicked(View view, int position) {
        selectedEmp = adapter.getSelectedEmployeeItem(position);
    }
}