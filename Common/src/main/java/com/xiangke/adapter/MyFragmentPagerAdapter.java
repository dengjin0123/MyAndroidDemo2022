package com.xiangke.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @Date:2022/8/2 11:30
 * @Author:dengjin
 * @Name:
 */
public class MyFragmentPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> fragmentList;

    public MyFragmentPagerAdapter(@NonNull @NotNull FragmentActivity fragmentActivity, List<Fragment> fragmentList) {
        super(fragmentActivity);
        this.fragmentList = fragmentList;
    }
    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getItemCount() {
        return fragmentList.size();
    }
}
