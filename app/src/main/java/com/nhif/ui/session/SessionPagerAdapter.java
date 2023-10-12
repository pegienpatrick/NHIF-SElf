package com.nhif.ui.session;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.nhif.ui.session.appointments.AppointmentsFragment;
import com.nhif.ui.session.billings.BillingsFragment;
import com.nhif.ui.session.consultations.ConsultationFragment;
import com.nhif.ui.session.dashboard.DashboardFragment;
import com.nhif.ui.session.descendants.DescendantsFragment;
import com.nhif.ui.session.profile.ProfileFragment;

public class SessionPagerAdapter extends FragmentPagerAdapter {

    public SessionPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // Return the appropriate fragment for each tab
        switch (position) {
            case 0:
                return new DashboardFragment();
            case 1:
                return new BillingsFragment();
            case 2:
                return new ConsultationFragment();
            case 3:
                return new AppointmentsFragment();

            case 4:
                return new DescendantsFragment();
            case 5:
                return new ProfileFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Number of tabs
        return 6; // Adjust this based on the number of tabs
    }
}
