package com.bharathmg.irctc.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bharathmg.irctc.PNRFragment;
import com.bharathmg.irctc.TrainScheduleFragment;
import com.bharathmg.irctc.TrainStatusFragment;

public class TabHomeAdapter extends FragmentPagerAdapter {

    public TabHomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Top Rated fragment activity
                return new PNRFragment();
            case 1:
                // Games fragment activity
                return new TrainStatusFragment();
            case 2:
                // Movies fragment activity
                return new TrainScheduleFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }

}