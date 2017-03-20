package hu.unideb.smartcampus.activity;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.calendar.fragment.CalendarFragment;
import hu.unideb.smartcampus.fragment.ChatFragment;
import hu.unideb.smartcampus.fragment.ConsultingHoursFragment;
import hu.unideb.smartcampus.fragment.HomeFragment;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;

public class MainActivity_SmartCampus extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawer;
    public Toolbar toolbar;

    public static int navItemIndex = 0;

    private static final String TAG_HOME = "home";
    private static final String TAG_CALENDAR = "calendar";
    private static final String TAG_OFFICEHOURS = "officeHours";
    private static final String TAG_CHAT = "chat";
    public static String CURRENT_TAG = TAG_HOME;

    private String[] activityTitles;

    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHandler = new Handler();

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_HOME;
            loadHomeFragment();
        }
    }

    private void loadHomeFragment() {
        selectNavMenu();

        setToolbarTitle();

        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                Fragment fragment = getHomeFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commitAllowingStateLoss();
            }
        };

        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        drawer.closeDrawers();

        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                CalendarFragment calendarFragment = new CalendarFragment();
                return calendarFragment;
            case 2:
                ConsultingHoursFragment consultingHoursFragment = new ConsultingHoursFragment();
                return consultingHoursFragment;
            case 3:
                ChatFragment chatFragment = new ChatFragment();
                return chatFragment;
            default:
                return new HomeFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
                    case R.id.home:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_HOME;
                        break;
                    case R.id.nav_calendar:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_CALENDAR;
                        break;
                    case R.id.nav_officeHours:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_OFFICEHOURS;
                        break;
                    case R.id.nav_chat:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_CHAT;
                        break;
                    case R.id.nav_about_us:
                        startActivity(new Intent(MainActivity_SmartCampus.this, AboutUsActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        //  each fragment will have a OnBackPressedListener.
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();
        if (fragmentList != null) {
            //TODO: Perform your logic to pass back press here
            for (Fragment fragment : fragmentList) {
                if (fragment instanceof OnBackPressedListener) {
                    ((OnBackPressedListener) fragment).onBackPressed();
                }
            }
        }
    }
}
