package hu.unideb.smartcampus.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.base.BaseActivity;
import hu.unideb.smartcampus.activity.settings.SettingsActivity;
import hu.unideb.smartcampus.fragment.about.AboutUsFragment;
import hu.unideb.smartcampus.fragment.attendance.AttendanceFragment;
import hu.unideb.smartcampus.fragment.calendar.CalendarFragment;
import hu.unideb.smartcampus.fragment.chat.ChatFragment;
import hu.unideb.smartcampus.fragment.home.HomeFragment;
import hu.unideb.smartcampus.interfaces.OnBackPressedListener;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displaySelectedScreen(R.id.nav_home);
        navigationView.getMenu().getItem(0).setChecked(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings_action:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void displaySelectedScreen(int itemId) {

        Fragment fragment = null;

        switch (itemId) {
            case R.id.nav_home:
                fragment = new HomeFragment();
                break;
            case R.id.nav_calendar:
                fragment = new CalendarFragment();
                break;
            case R.id.nav_attendance:
                fragment = new AttendanceFragment();
                break;
            case R.id.nav_office_hours:
                break;
            case R.id.nav_chat:
                fragment = new ChatFragment();
                break;
            case R.id.nav_about_us:
                fragment = new AboutUsFragment();
                break;
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    @Override
    public void onBackPressed() {
        //  each fragment will have a OnBackPressedListener.
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
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
