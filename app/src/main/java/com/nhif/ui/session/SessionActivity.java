package com.nhif.ui.session;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.nhif.R;



public class SessionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);


        ViewPager viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);

        // Create an adapter to manage the fragments for each tab
        SessionPagerAdapter adapter = new SessionPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        // Connect the TabLayout with the ViewPager
        tabLayout.setupWithViewPager(viewPager);

        // Optionally, set icons and text for each tab
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_home).setText("Dashboard");

        tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_attach_money).setText("Billings");
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_action_chat).setText("Consultation");
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_action_date_range).setText("Appointments");
//        tabLayout.getTabAt(4).setIcon(R.drawable.ic_action_people).setText("Dependants");
        tabLayout.getTabAt(5).setIcon(R.drawable.ic_action_account_circle).setText("Profile");






    }



    @Override
    public void onBackPressed() {
        // Do nothing (disable the back button)
        super.onBackPressed();
    }


}