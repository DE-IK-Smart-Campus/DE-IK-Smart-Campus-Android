package hu.unideb.smartcampus.activity;


import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.EntityFullJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.AboutUsFragment;
import hu.unideb.smartcampus.fragment.HomeFragment;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.attendance.fragment.AttendanceFragment;
import hu.unideb.smartcampus.main.activity.calendar.fragment.CalendarFragment;
import hu.unideb.smartcampus.main.activity.chat.fragment.ChatMainMenuFragment;
import hu.unideb.smartcampus.main.activity.officehours.handler.OfficeHourHandler;
import hu.unideb.smartcampus.shared.iq.request.UserLocationIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static android.support.v4.content.PermissionChecker.PERMISSION_DENIED;
import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;
import static com.google.android.gms.location.LocationServices.FusedLocationApi;
import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;
import static java.lang.Thread.sleep;


public class MainActivity_SmartCampus extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    public static final int MY_REQUEST_CODE = 115;
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

    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    public boolean isGooglePlayServicesAvailable(Context context) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(context);
        return resultCode == ConnectionResult.SUCCESS;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(getApplicationContext(), LocationSender.class);
        alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

        alarmMgr = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                //SystemClock.elapsedRealtime() + (15 * 60 * 1000),
                SystemClock.elapsedRealtime() + (1 * 60 * 1000),
                1 * 60 * 1000, alarmIntent);

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
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

    protected void onStart() {
        mGoogleApiClient.connect();
        Log.e(TAG_HOME, "mGoogleApiClient.connect();");

        super.onStart();
    }

    protected void onStop() {
        Log.e(TAG_HOME, "mGoogleApiClient.dc();");
        mGoogleApiClient.disconnect();

        super.onStop();
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


            switch (navItemIndex) {

                case 0:
                    HomeFragment homeFragment = new HomeFragment();
                    break;
                case 1:
                    CalendarFragment fragment = new CalendarFragment();
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                    fragmentTransaction.commitAllowingStateLoss();
                    //adminChat.addMessageListener(new CalandarHandler(getSupportFragmentManager()));
                    break;
                case 2:
                    AttendanceFragment fragment1 = new AttendanceFragment();
                    FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction1.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction1.replace(R.id.frame, fragment1, CURRENT_TAG);
                    fragmentTransaction1.commitAllowingStateLoss();
                    //adminChat.addMessageListener(new CalandarHandler(getSupportFragmentManager()));
                    break;
                case 3:
                    OfficeHourHandler officeHour = new OfficeHourHandler(getSupportFragmentManager(), getApplicationContext());
                    officeHour.sendDefaultMsg();

                    break;


                case 4:
                    ChatMainMenuFragment chatFragment = new ChatMainMenuFragment();
                    fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, chatFragment, CURRENT_TAG);
                    fragmentTransaction.commitAllowingStateLoss();
                    //adminChat.addMessageListener(new ChatHandler(getSupportFragmentManager()));
                    break;
                case 5:
                    AboutUsFragment aboutUsFragment = new AboutUsFragment();
                    FragmentTransaction fragmentTransactiona = getSupportFragmentManager().beginTransaction();
                    fragmentTransactiona.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransactiona.replace(R.id.frame, aboutUsFragment, CURRENT_TAG);
                    fragmentTransactiona.commitAllowingStateLoss();
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
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

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

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.e(TAG_HOME, "mGoogleApiClient is connected:");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, MY_REQUEST_CODE);
            return;
        }
        mLastLocation = FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        getLocation();
    }

    private void getLocation() {
        if (mLastLocation != null) {

            try {
                Log.e(TAG_HOME, "onConnected: LastLocation != null");
                sleep(SmackConfiguration.getDefaultReplyTimeout());
                UserLocationIqRequest userLocationIqRequest = new UserLocationIqRequest();
                final EntityFullJid user = Connection.getInstance().getXmppConnection().getUser();
                userLocationIqRequest.setUsername(user.getLocalpartOrThrow().toString());
                userLocationIqRequest.setAccuracy((double) mLastLocation.getAccuracy());
                userLocationIqRequest.setLatitude(mLastLocation.getLatitude());
                userLocationIqRequest.setLongitude(mLastLocation.getLongitude());
                userLocationIqRequest.setTimeStamp(mLastLocation.getTime());

                userLocationIqRequest.setTo(JidCreate.from(ADMINJID));
                userLocationIqRequest.setType(IQ.Type.set);
                userLocationIqRequest.setFrom(user);

                final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(userLocationIqRequest);
                stanzaCollectorAndSend.nextResultOrThrow(5000);
                Log.e(TAG_HOME, "onConnected: Succes ");

            } catch (XmppStringprepException | InterruptedException | SmackException.NotConnectedException | SmackException.NoResponseException | XMPPException.XMPPErrorException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {

        if (grantResults[0] == PERMISSION_GRANTED) {
            Toast.makeText(this, "PERMISSION_GRANTED", Toast.LENGTH_SHORT).show();
            Log.e(TAG_HOME, "onRequestPermissionsResult: Granted");
            //Will be 100% granted
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            getLocation();
        }
        if (grantResults[0] == PERMISSION_DENIED) {
            Toast.makeText(this, "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
            Log.e(TAG_HOME, "onRequestPermissionsResult: Denied");
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e("Letsee", "onConnectionFailed: " + connectionResult.getErrorCode());
        Toast.makeText(this, "Failed to make connection with google services", Toast.LENGTH_SHORT).show();
    }
}
