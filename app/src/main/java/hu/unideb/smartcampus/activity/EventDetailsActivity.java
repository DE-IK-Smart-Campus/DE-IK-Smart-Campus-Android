package hu.unideb.smartcampus.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import hu.unideb.smartcampus.R;

public class EventDetailsActivity extends AppCompatActivity {

    private List<String> eventDetails;
    private final Context context = this;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog fromTimePickerDialog;
    private TimePickerDialog toTimePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_details);

        Bundle bundle = getIntent().getExtras();

        final ListView eventDetailsListView = (ListView) findViewById(R.id.event_details_listview);

        eventDetails = new ArrayList<>();

        String eventName = getIntent().getExtras().getString("eventName");
        String eventDescription = bundle.getString("eventDescription");
        String eventPlace = bundle.getString("eventPlace");
        Long eventDate = bundle.getLong("eventDate");
        Long eventStartTime = bundle.getLong("eventStartTime");
        Long eventEndTime = bundle.getLong("eventEndTime");

        Calendar fromTime = Calendar.getInstance();
        fromTime.setTimeInMillis(eventStartTime);

        Calendar toTime = Calendar.getInstance();
        toTime.setTimeInMillis(eventEndTime);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(eventDate);

        eventDetails.add(eventName);
        eventDetails.add(eventDescription);
        eventDetails.add(eventPlace);

        eventDetails.add(getResources().getString(R.string.date) + "\t" + DateFormat.getDateInstance(DateFormat.SHORT).format(cal.getTime()));
        eventDetails.add(getResources().getString(R.string.textViewStart) + "\t" + DateFormat.getTimeInstance(DateFormat.SHORT).format(fromTime.getTime()));
        eventDetails.add(getResources().getString(R.string.textViewEnd) + "\t" + DateFormat.getTimeInstance(DateFormat.SHORT).format(toTime.getTime()));

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
            }

        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        fromTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                newTime.set(0, 0, 0, hourOfDay, minute);
//                startTime.setText(timeFormatter.format(newTime.getTime()));
            }
        }, fromTime.get(Calendar.HOUR_OF_DAY), fromTime.get(Calendar.MINUTE), true);

        toTimePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
//                newTime.set(0, 0, 0, hourOfDay, minute);
//                startTime.setText(timeFormatter.format(newTime.getTime()));
            }
        }, toTime.get(Calendar.HOUR_OF_DAY), toTime.get(Calendar.MINUTE), true);

        eventDetailsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                if (eventDetailsListView.getItemAtPosition(position).toString().contains(getResources().getString(R.string.date))) {
                    datePickerDialog.show();
                } else if (eventDetailsListView.getItemAtPosition(position).toString().contains(getResources().getString(R.string.textViewStart))) {
                    fromTimePickerDialog.show();
                } else if (eventDetailsListView.getItemAtPosition(position).toString().contains(getResources().getString(R.string.textViewEnd))) {
                    toTimePickerDialog.show();
                } else {
                    editEventShowDialog(view);
                }
                return true;
            }
        });

        ArrayAdapter<String> eventDetailsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventDetails);

        eventDetailsListView.setAdapter(eventDetailsAdapter);

    }

    public void getDataAnotherScreen(){

    }

    private void editEventShowDialog(View view) {

        LayoutInflater li = LayoutInflater.from(getApplicationContext());
        View eventDetailsDialog = li.inflate(R.layout.event_details_editing_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setView(eventDetailsDialog);

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
