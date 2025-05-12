package com.androidlearning.adapter.gridview;

import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.androidlearning.R;

public class VolCalcResultActivity extends AppCompatActivity {

    TextView title, result;
    EditText editText1, editText2, editText3;
    Button calc;

    private String shapeName;
    private Double volume;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vol_calc_result);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initView();

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            shapeName = bundle.getString("shapeName");

            if("Sphere".equalsIgnoreCase(shapeName)) {
                title.setText("Sphere Volume");
                editText1.setHint("Please Enter The Radius");
                editText2.setVisibility(View.GONE);
                editText3.setVisibility(View.GONE);
            } else if("Cylinder".equalsIgnoreCase(shapeName)) {
                title.setText("Cylinder Volume");
                editText1.setHint("Please Enter The Radius");
                editText2.setVisibility(View.VISIBLE);
                editText2.setHint("Please Enter The Height");
                editText3.setVisibility(View.GONE);
            } else if("Cube".equalsIgnoreCase(shapeName)) {
                title.setText("Cube Volume");
                editText1.setHint("Please Enter The Length of Side");
                editText2.setVisibility(View.GONE);
                editText3.setVisibility(View.GONE);
            } else if("Prism".equalsIgnoreCase(shapeName)) {
                title.setText("Prism Volume");
                editText1.setHint("Please Enter The Length");
                editText2.setVisibility(View.VISIBLE);
                editText2.setHint("Please Enter The Height");
                editText3.setVisibility(View.VISIBLE);
                editText3.setHint("Please Enter The Width");
            }
        }


    }

    private void initView() {
        title = findViewById(R.id.vol_calc_result_title);
        result = findViewById(R.id.vol_calc_result);
        editText1 = findViewById(R.id.vol_calc_result_edidtext1);
        editText1.setInputType(TYPE_CLASS_NUMBER | TYPE_NUMBER_FLAG_DECIMAL);
        editText2 = findViewById(R.id.vol_calc_result_edidtext2);
        editText2.setInputType(TYPE_CLASS_NUMBER | TYPE_NUMBER_FLAG_DECIMAL);
        editText2.setVisibility(View.GONE);
        editText3 = findViewById(R.id.vol_calc_result_edidtext3);
        editText3.setInputType(TYPE_CLASS_NUMBER | TYPE_NUMBER_FLAG_DECIMAL);
        editText3.setVisibility(View.GONE);
        calc = findViewById(R.id.vol_calc_result_btn);
        calc.setOnClickListener(l -> {
            if(editText1.getText().toString().trim().isEmpty() ||
                    (editText2.getVisibility() == View.VISIBLE && editText2.getText().toString().trim().isEmpty()) ||
                    (editText3.getVisibility() == View.VISIBLE && editText3.getText().toString().trim().isEmpty())) {
                Toast.makeText(getApplicationContext(), "Please Enter All Shape Specs", Toast.LENGTH_SHORT).show();
            } else {
                if("Sphere".equalsIgnoreCase(shapeName)) {
                    double s_radius = Double.parseDouble(editText1.getText().toString());
                    volume = (4/3) * Math.PI * Math.pow(s_radius, 3);
                    result.setText("Sphere Volume: " + volume + " m^3");
                } else if("Cylinder".equalsIgnoreCase(shapeName)) {
                    double c_radius = Double.parseDouble(editText1.getText().toString());
                    double c_height = Double.parseDouble(editText2.getText().toString());
                    volume = Math.PI * Math.pow(c_radius, 2) * c_height;
                    result.setText("Cylinder Volume: " + volume + " m^3");
                } else if("Cube".equalsIgnoreCase(shapeName)) {
                    double c_length = Double.parseDouble(editText1.getText().toString());
                    volume = Math.pow(c_length, 3);
                    result.setText("Cube Volume: " + volume + " m^3");
                } else if("Prism".equalsIgnoreCase(shapeName)) {
                    double p_length = Double.parseDouble(editText1.getText().toString());
                    double p_height = Double.parseDouble(editText2.getText().toString());
                    double p_width = Double.parseDouble(editText3.getText().toString());
                    volume = p_length * p_height * p_width;
                    result.setText("Prism Volume: " + volume + " m^3");
                }
            }
        });
    }
}