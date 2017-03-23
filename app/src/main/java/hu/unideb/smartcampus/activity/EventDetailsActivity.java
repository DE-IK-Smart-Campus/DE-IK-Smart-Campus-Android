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

    private final Context context = this;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog fromTimePickerDialog;
    private TimePickerDialog toTimePickerDialog;
    private ListView eventDetailsListView;

    private String eventName;
    private String eventDescription;
    private String eventPlace;
    private Long eventDate;
    private Long eventStartTime;
    private Long eventEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_details);

        eventDetailsListView = (ListView) findViewById(R.id.event_details_listview);

        getDataAnotherScreen();

        ArrayAdapter<String> eventDetailsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventDetailsListSetUp());

        eventDetailsListView.setAdapter(eventDetailsAdapter);

        eventDetailsListViewClick();

    }

    public void getDataAnotherScreen() {
        Bundle bundle = getIntent().getExtras();

        eventName = getIntent().getExtras().getString("eventName");
        eventDescription = bundle.getString("eventDescription");
        eventPlace = bundle.getString("eventPlace");
        eventDate = bundle.getLong("eventDate");
        eventStartTime = bundle.getLong("eventStartTime");
        eventEndTime = bundle.getLong("eventEndTime");
    }

    public List<String> eventDetailsListSetUp() {

        List<String> eventDetails = new ArrayList<>();

        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(eventDate);

        Calendar fromTime = Calendar.getInstance();
        fromTime.setTimeInMillis(eventStartTime);

        Calendar toTime = Calendar.getInstance();
        toTime.setTimeInMillis(eventEndTime);

        eventDetails.add(getResources().getString(R.string.textEventName) + "\t " + eventName);
        eventDetails.add(getResources().getString(R.string.textEventDescription) + "\t " + eventDescription);
        eventDetails.add(getResources().getString(R.string.textEventPlace) + "\t " + eventPlace);
        eventDetails.add(getResources().getString(R.string.date) + "\t " + DateFormat.getDateInstance(DateFormat.SHORT).format(date.getTime()));
        eventDetails.add(getResources().getString(R.string.textViewStart) + "\t " + DateFormat.getTimeInstance(DateFormat.SHORT).format(fromTime.getTime()));
        eventDetails.add(getResources().getString(R.string.textViewEnd) + "\t " + DateFormat.getTimeInstance(DateFormat.SHORT).format(toTime.getTime()));

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
            }

        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));

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

        return eventDetails;
    }

    public void eventDetailsListViewClick() {

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
                    if (eventDetailsListView.getItemAtPosition(position).toString().contains(getResources().getString(R.string.textEventName))) {
                        editEventShowDialog(view, getResources().getString(R.string.textEventName));
                    } else if (eventDetailsListView.getItemAtPosition(position).toString().contains(getResources().getString(R.string.textEventDescription))) {
                        editEventShowDialog(view, getResources().getString(R.string.textEventDescription));
                    } else if (eventDetailsListView.getItemAtPosition(position).toString().contains(getResources().getString(R.string.textEventPlace))) {
                        editEventShowDialog(view, getResources().getString(R.string.textEventPlace));
                    }
                }
                return true;
            }
        });
    }

    private void editEventShowDialog(View view, String title) {

        LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
        View eventDetailsDialog = layoutInflater.inflate(R.layout.event_details_editing_dialog, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setView(eventDetailsDialog);
        alertDialogBuilder.setTitle(title);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton(getResources().getText(R.string.ok_button),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                            }
                        })
                .setNegativeButton(getResources().getText(R.string.cancel_button),
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
