package com.androidlearning.recyclerview.advanced.multipleviewtype;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlearning.R;

import java.util.ArrayList;
import java.util.List;

public class RVMultiViewTypeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MultiViewTypeAdapter adapter;
    private List<EmployeeMultiType> employees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rvmulti_view_type);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.adv_rv_multi_viewtype_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        employees = createEmployees();
        adapter = new MultiViewTypeAdapter(employees);

        recyclerView.setAdapter(adapter);
    }

    private List<EmployeeMultiType> createEmployees() {
        List<EmployeeMultiType> employees = new ArrayList<>();

        EmployeeWithEmail emp1 = EmployeeWithEmail.builder()
                .empName("Jack").address("Toronto").email("jack@gmail.com")
                .build();

        EmployeeWithPhone emp2 = EmployeeWithPhone.builder()
                .empName("John").address("New York").phone("+123423453456")
                .build();

        EmployeeWithEmail emp3 = EmployeeWithEmail.builder()
                .empName("Joe").address("Calgary").email("joe@hotmail.com")
                .build();

        EmployeeWithPhone emp4 = EmployeeWithPhone.builder()
                .empName("Ryan").address("London").phone("+0123456789")
                .build();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);
        employees.add(emp4);
        return employees;
    }
}