package com.androidlearning.recyclerview.advanced.multiitemselection;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;
import com.androidlearning.recyclerview.advanced.singleitemselection.Employee;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RVMultiSelActivity extends AppCompatActivity implements RVMultiSelectedClickedListener{

    private RecyclerView recyclerView;
    private List<Employee> employees;
    private MultiSelectedAdapter adapter;
    private Map<Integer, Employee> selectedEmps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rvmulti_sel);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        selectedEmps = new HashMap<>();

        recyclerView = findViewById(R.id.adv_rv_multi_sel_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        employees = createEmployeeList();
        adapter = new MultiSelectedAdapter(employees);
        adapter.setMultiSelectedClickedListener(this);
        recyclerView.setAdapter(adapter);

        Button selected = findViewById(R.id.adv_rv_multi_sel_btn);
        selected.setOnClickListener(v -> {
            if(selectedEmps.isEmpty()) {
                Toast.makeText(this, "No Employee Selected!", Toast.LENGTH_SHORT).show();
            } else {
                StringBuilder sb = new StringBuilder();
                for(Map.Entry<Integer, Employee> entry : selectedEmps.entrySet()) {
                    sb.append(entry.getValue().getEmpName());
                    sb.append(System.lineSeparator());
                }
                Toast.makeText(this, "Selected Employees: " + sb.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Employee> createEmployeeList() {
        List<Employee> employeeList = new ArrayList<>();
        for(int i=0; i<10; i++) {
            Employee employee = Employee.builder().empName("Employee " + i).build();
            employeeList.add(employee);
        }
        return employeeList;
    }

    @Override
    public void onRVItemClickedListener(View view, int position) {
        adapter.getSelectedEmployees(selectedEmps, position);
    }
}