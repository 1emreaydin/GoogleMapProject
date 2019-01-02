package com.kodaskina.mapproject.adapter;

import android.support.v4.view.ViewPager;

/**
 * Created by EA on 7.7.2017.
 */

public abstract class CustomViewPagerOnChangeListener implements ViewPager.OnPageChangeListener {

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        onViewPagerPageSelected(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public abstract void onViewPagerPageSelected(int position);
}
