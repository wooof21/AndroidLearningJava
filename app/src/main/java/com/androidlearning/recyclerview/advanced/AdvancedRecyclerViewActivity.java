package com.androidlearning.recyclerview.advanced;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.databinding.DataBindingUtil;

import com.androidlearning.R;
import com.androidlearning.databinding.ActivityAdvancedRecyclerViewBinding;
import com.androidlearning.recyclerview.advanced.cardview.RVCardViewActivity;
import com.androidlearning.recyclerview.advanced.multiitemselection.RVMultiSelActivity;
import com.androidlearning.recyclerview.advanced.multipleviewtype.RVMultiViewTypeActivity;
import com.androidlearning.recyclerview.advanced.singleitemselection.RVSingleSelActivity;
import com.androidlearning.recyclerview.advanced.swipeitem.RVSwipeItemActivity;

public class AdvancedRecyclerViewActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityAdvancedRecyclerViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_advanced_recycler_view);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding = DataBindingUtil.setContentView(this, R.layout.activity_advanced_recycler_view);

        binding.advancedRvCardview.setOnClickListener(this);
        binding.advancedRvSingle.setOnClickListener(this);
        binding.advancedRvMulti.setOnClickListener(this);
        binding.advancedRvSwipe.setOnClickListener(this);
        binding.advancedRvMultiSel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        Intent intent = null;
        if(viewId == R.id.advanced_rv_cardview) {
            intent = new Intent(this, RVCardViewActivity.class);
        } else if(viewId == R.id.advanced_rv_single) {
            intent = new Intent(this, RVSingleSelActivity.class);
        } else if(viewId == R.id.advanced_rv_multi) {
            intent = new Intent(this, RVMultiViewTypeActivity.class);
        } else if(viewId == R.id.advanced_rv_multi_sel) {
            intent = new Intent(this, RVMultiSelActivity.class);
        } else if(viewId == R.id.advanced_rv_swipe) {
            intent = new Intent(this, RVSwipeItemActivity.class);
        }

        startActivity(intent);
    }
}