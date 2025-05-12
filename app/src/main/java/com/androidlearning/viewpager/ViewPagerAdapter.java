package com.androidlearning.viewpager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//typically use fragment pager adapter or fragment state pager adapter
public class ViewPagerAdapter extends FragmentStateAdapter {

    private List<Fragment> fragments;

    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        this.fragments = new ArrayList<>();
    }

    /**
     * responsible for creating and returning a fragment for a specific
     * position within the ViewPager2 -> List of Fragments
     *
     */
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return fragments.get(position);
    }

    @Override
    public int getItemCount() {
        return fragments.size();
    }

    public void addFragments(Fragment... fragment) {
        fragments.addAll(Arrays.asList(fragment));
    }
}
