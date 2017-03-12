package hu.unideb.smartcampus.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.R;

public class EventDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_details);

        ListView eventDetailsListView = (ListView) findViewById(R.id.event_details_listview);

        List<String> eventDetails = new ArrayList<>();
        
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,eventDetails);

        eventDetailsListView.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_edit_event) {
            Toast.makeText(getApplicationContext(), "Szerkeszt", Toast.LENGTH_LONG).show();
        } else if (id == R.id.action_delete_event) {
            Toast.makeText(getApplicationContext(), "Töröl", Toast.LENGTH_LONG).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
