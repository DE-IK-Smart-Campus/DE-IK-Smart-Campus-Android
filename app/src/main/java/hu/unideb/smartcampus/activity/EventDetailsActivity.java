package hu.unideb.smartcampus.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import hu.unideb.smartcampus.R;

public class EventDetailsActivity extends AppCompatActivity {

    private List<String> eventDetails;
    private final Context context = this;

    private DatePickerDialog datePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_details);

        ListView eventDetailsListView = (ListView) findViewById(R.id.event_details_listview);

        eventDetails = new ArrayList<>();

        String eventName = getIntent().getExtras().getString("eventName");
        String eventDescription = getIntent().getExtras().getString("eventDescription");
        String eventP = getIntent().getExtras().getString("eventPlace");
        Long eventDate = getIntent().getExtras().getLong("eventDate");
        String eventS = getIntent().getExtras().getString("eventStartTime");
        String eventE = getIntent().getExtras().getString("eventEndTime");

        eventDetails.add(eventName);
        eventDetails.add(eventDescription);
        eventDetails.add(eventP);
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(TimeZone.getDefault());
        cal.setTimeInMillis(eventDate);
        eventDetails.add("Dátum: " + "\t" + DateFormat.getDateInstance(DateFormat.SHORT).format(cal.getTime()));
        eventDetails.add(getResources().getString(R.string.textViewStart) + "\t" + eventS);
        eventDetails.add(getResources().getString(R.string.textViewEnd) + "\t" + eventE);


     /*   datePickerDialog = new DatePickerDialog(this, (view, year, monthOfYear, dayOfMonth) -> {
            Calendar newDate = Calendar.getInstance();
            newDate.set(year, monthOfYear, dayOfMonth);
//            startDate.setText(dateFormatter.format(newDate.getTime()));
//            endDate.setText(startDate.getText());
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        eventDetailsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getApplicationContext(), eventDetailsListView.getItemAtPosition(position).toString(), Toast.LENGTH_LONG).show();

                if (eventDetailsListView.getItemAtPosition(position).toString().contains("Dátum")) {
                    datePickerDialog.show();
                } else if (eventDetailsListView.getItemAtPosition(position).toString().contains(getResources().getString(R.string.textViewStart))) {

                } else if (eventDetailsListView.getItemAtPosition(position).toString().contains(getResources().getString(R.string.textViewEnd))) {

                } else {
                    editEventShowDialog(view);
                }
                return true;
            }
        });
*/
        ArrayAdapter<String> a = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventDetails);

        eventDetailsListView.setAdapter(a);

    }

    private void editEventShowDialog(View view) {

        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View promptsView = li.inflate(R.layout.event_details_editing_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextInput);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();


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

        if (id == R.id.action_delete_event) {
            Toast.makeText(getApplicationContext(), "Töröl", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
