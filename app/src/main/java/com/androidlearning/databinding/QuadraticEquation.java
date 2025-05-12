package com.androidlearning.databinding;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class QuadraticEquation extends BaseObservable {

    //always use String with data binding, use String even for numeric value to avoid any possible errors
    @Bindable
    private String a;
    @Bindable
    private String b;
    @Bindable
    private String c;

    //to exclude from lombok getter/setter
    @Getter(value=AccessLevel.NONE)
    @Setter(value= AccessLevel.NONE)
    ActivityQuadraticEquationCalcBinding binding;

    public QuadraticEquation(ActivityQuadraticEquationCalcBinding binding) {
        this.binding = binding;
    }

    public void calcEquation(View view) {

        int a = Integer.parseInt(getA());
        int b = Integer.parseInt(getB());
        int c = Integer.parseInt(getC());

        //calculate the discriminant
        double discriminant = b * b - 4 * a * c;

        //calculate the root
        double root1, root2;

        if(discriminant > 0) {
            //two real and distinct root
            root1 = (-b + Math.sqrt(discriminant)) / (2*a);
            root2 = (-b - Math.sqrt(discriminant)) / (2*a);

            //display the result in TextView
            binding.quadraticResult.setText("Root1 = " + root1 + ", Root2 = " + root2);
        } else if(discriminant > 0) {
            binding.quadraticResult.setText("No Real Roots");
        } else {
            // one real root
            root1 = -b / (2*a);
            binding.quadraticResult.setText("Root = " + root1);
        }
    }

}
