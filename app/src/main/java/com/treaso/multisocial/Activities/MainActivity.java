package com.treaso.multisocial.Activities;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.treaso.multisocial.Fragments.Dribbble;
import com.treaso.multisocial.Fragments.Facebook;
import com.treaso.multisocial.Fragments.GooglePlus;
import com.treaso.multisocial.Fragments.LinkedIn;
import com.treaso.multisocial.Fragments.Pinterest;
import com.treaso.multisocial.Fragments.Quora;
import com.treaso.multisocial.Fragments.Twitter;
import com.treaso.multisocial.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    Handler mHandler = new Handler();
    boolean isRunning = true;
    int tabPosition = 0;
    private int[] tabIcons = {
            R.drawable.facebook,
            R.drawable.twitter,
            R.drawable.googleplus,
            R.drawable.linkedin,
            R.drawable.dribbble,
            R.drawable.pinterest,
            R.drawable.quora,
    };

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
        tabLayout.getTabAt(4).setIcon(tabIcons[4]);
        tabLayout.getTabAt(5).setIcon(tabIcons[5]);
        tabLayout.getTabAt(6).setIcon(tabIcons[6]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Facebook(), "ONE");
        adapter.addFragment(new Twitter(), "THREE");
        adapter.addFragment(new GooglePlus(), "FOUR");
        adapter.addFragment(new LinkedIn(), "FIVE");
        adapter.addFragment(new Dribbble(), "EIGHT");
        adapter.addFragment(new Pinterest(), "NINE");
        adapter.addFragment(new Quora(), "TEN");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setOffscreenPageLimit(7);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabPosition = position;
                if (position == 0) {
                    updateStatusBarColor("#3b5998");
                } else if (position == 1) {
                    updateStatusBarColor("#00aced");
                } else if (position == 2) {
                    updateStatusBarColor("#d34836");
                } else if (position == 3) {
                    updateStatusBarColor("#0077B5");
                } else if (position == 4) {
                    updateStatusBarColor("#ea4c89");
                } else if (position == 5) {
                    updateStatusBarColor("#c8232c");
                } else {
                    updateStatusBarColor("#AA2200");
                }
            }

            @Override
            public void onPageSelected(int position) {


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
    }

    public boolean updateStatusBarColor(String color) {// Color must be in hexadecimal fromat
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
            return true;
        } else {
            return false;
        }

    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<android.support.v4.app.Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public android.support.v4.app.Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(android.support.v4.app.Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        if (tabPosition == 0) {
            if (Facebook.webView.canGoBack()) {
                Facebook.webView.goBack();
            }
        }  else if (tabPosition == 1) {
            if (Twitter.webView.canGoBack()) {
                Twitter.webView.goBack();

            }
        } else if (tabPosition == 2) {
            if (GooglePlus.webView.canGoBack()) {
                GooglePlus.webView.goBack();

            }
        } else if (tabPosition == 3) {
            if (LinkedIn.webView.canGoBack()) {
                LinkedIn.webView.goBack();

            }
        }  else if (tabPosition == 4) {
            if (Dribbble.webView.canGoBack()) {
                Dribbble.webView.goBack();

            }
        } else if (tabPosition == 5) {
            if (Pinterest.webView.canGoBack()) {
                Pinterest.webView.goBack();

            }
        } else {
            if (Quora.webView.canGoBack()) {
                Quora.webView.goBack();

            }
        }
    }
}
