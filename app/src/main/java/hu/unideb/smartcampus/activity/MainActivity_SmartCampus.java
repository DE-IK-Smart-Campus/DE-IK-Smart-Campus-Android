package hu.unideb.smartcampus.activity;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import org.jivesoftware.smack.SmackException;

import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.AboutUsFragment;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.attendance.fragment.AttendanceFragment;
import hu.unideb.smartcampus.main.activity.chat.handler.ChatHandler;
import hu.unideb.smartcampus.main.activity.home.fragment.HomeFragment;
import hu.unideb.smartcampus.main.activity.officehours.handler.OfficeHourHandler;
import hu.unideb.smartcampus.scheduler.LocationAlarmReceiver;
import hu.unideb.smartcampus.xmpp.Connection;


public class MainActivity_SmartCampus extends AppCompatActivity {

    LocationAlarmReceiver locationAlarmReceiver = new LocationAlarmReceiver();

    private NavigationView navigationView;
    private DrawerLayout drawer;
    public Toolbar toolbar;
    public static int navItemIndex = 0;
    private static final String TAG_HOME = "home";
    private static final String TAG_CALENDAR = "calendar";
    private static final String TAG_CLASSATTENDANCE = "classattendance";
    private static final String TAG_OFFICEHOURS = "officeHours";
    private static final String TAG_CHAT = "chat";
    private static final String TAG_ABOUT = "about";
    public static String CURRENT_TAG = TAG_HOME;

    private String[] activityTitles;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            Log.i(TAG_HOME, "onCreate: Setalarm");
            locationAlarmReceiver.setAlarm(this);
        }


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
                setListenerForSelectedMenu();
            }
        };

        mHandler.post(mPendingRunnable);

        drawer.closeDrawers();

        invalidateOptionsMenu();
    }

    private void setListenerForSelectedMenu() {
        try {
            FragmentTransaction fragmentTransaction;
            switch (navItemIndex) {

                case 0:
                    HomeFragment homeFragment = new HomeFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, homeFragment, CURRENT_TAG);
                    fragmentTransaction.commitAllowingStateLoss();
                    break;
                case 1:

//                    CalendarFragment fragment = new CalendarFragment();
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                            android.R.anim.fade_out);
//                    fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
//                    fragmentTransaction.commitAllowingStateLoss();
//                    CustomEventHandler customEventHandler = new CustomEventHandler(getSupportFragmentManager(), getApplicationContext());
//                    customEventHandler.sendDefaultMesg1();
                    TimetableEventHandler timetableEventHandler = new TimetableEventHandler(getSupportFragmentManager(), getApplicationContext());
                    timetableEventHandler.sendDefaultMesg();
                    break;
                case 2:
                    AttendanceFragment fragment1 = new AttendanceFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, fragment1, CURRENT_TAG);
                    fragmentTransaction.commitAllowingStateLoss();
                    break;
                case 3:
                    Connection.getInstance().createLoadingDialogFragment(getSupportFragmentManager(), new Bundle());

                    new Thread(new Runnable() {
                        public void run() {
                            OfficeHourHandler officeHourHandler = OfficeHourHandler.getInstance();
                            officeHourHandler.askSubjects(getSupportFragmentManager(), getApplicationContext());
                        }
                    }).start();

                    /*new Thread(new Runnable() {
                        public void run() {
                            OfficeHourHandler officeHourHandler = OfficeHourHandler.getInstance();
                            officeHourHandler.askSubjectsFromDb(getSupportFragmentManager());
                        }
                    }).start();
                    */
                    break;
                case 4:
                    Connection.getInstance().createLoadingDialogFragment(getSupportFragmentManager(), new Bundle());
                    new Thread(new Runnable() {
                        public void run() {
                            ChatHandler chatHandler = ChatHandler.getInstance();
                            chatHandler.getAllChat(getSupportFragmentManager());
                        }
                    }).start();
                    break;
                case 5:
                    AboutUsFragment aboutUsFragment = new AboutUsFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, aboutUsFragment, CURRENT_TAG);
                    fragmentTransaction.commitAllowingStateLoss();
                    break;
                default:
                    break;
            }
        } catch (SmackException.NotConnectedException | InterruptedException e) {
            e.printStackTrace();
        }

    }

    private void setToolbarTitle() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(activityTitles[navItemIndex]);
        }
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
                    case R.id.nav_classAttendence:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_CLASSATTENDANCE;
                        break;
                    case R.id.nav_officeHours:
                        navItemIndex = 3;
                        CURRENT_TAG = TAG_OFFICEHOURS;
                        break;
                    case R.id.nav_chat:
                        navItemIndex = 4;
                        CURRENT_TAG = TAG_CHAT;
                        break;
                    case R.id.nav_about_us:
                        navItemIndex = 5;
                        CURRENT_TAG = TAG_ABOUT;
                        break;
                    case R.id.settingsActivity:
                        startActivity(new Intent(MainActivity_SmartCampus.this, SettingsActivity.class));
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

        drawer.addDrawerListener(actionBarDrawerToggle);

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

