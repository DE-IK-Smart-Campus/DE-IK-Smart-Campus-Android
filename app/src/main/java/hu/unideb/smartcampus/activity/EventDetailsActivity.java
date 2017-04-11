package hu.unideb.smartcampus.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
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

    private ArrayAdapter<String> eventDetailsAdapter;
    private List<String> eventDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.event_details);

        eventDetailsListView = (ListView) findViewById(R.id.event_details_listview);

        getDataAnotherScreen();

        eventDetailsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventDetailsListSetUp());

        eventDetailsListView.setAdapter(eventDetailsAdapter);

//        eventDetailsListViewClick();
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

        eventDetails = new ArrayList<>();

        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(eventDate);

        Calendar fromTime = Calendar.getInstance();
        fromTime.setTimeInMillis(eventStartTime);

        Calendar toTime = Calendar.getInstance();
        toTime.setTimeInMillis(eventEndTime);

        eventDetails.add(eventName);
        eventDetails.add(eventDescription);
        eventDetails.add(eventPlace);
        Toast.makeText(context, eventDate.toString(), Toast.LENGTH_SHORT).show();
//        eventDetails.add(getResources().getString(R.string.date) + "\t " + DateFormat.getDateInstance(DateFormat.SHORT).format(date.getTime()));
//        eventDetails.add(getResources().getString(R.string.textViewStart) + "\t " + DateFormat.getTimeInstance(DateFormat.SHORT).format(fromTime.getTime()));
//        eventDetails.add(getResources().getString(R.string.textViewEnd) + "\t " + DateFormat.getTimeInstance(DateFormat.SHORT).format(toTime.getTime()));

//        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
//
//            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
//                Calendar newDate = Calendar.getInstance();
//                newDate.set(year, monthOfYear, dayOfMonth);
//            }
//
//        }, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));

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

//    public void eventDetailsListViewClick() {
//
//        eventDetailsListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            @Override
//            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
//                if (eventDetailsListView.getItemAtPosition(position).toString().contains(getResources().getString(R.string.date))) {
//                    datePickerDialog.show();
//                } else if (eventDetailsListView.getItemAtPosition(position).toString().contains(getResources().getString(R.string.textViewStart))) {
//                    fromTimePickerDialog.show();
//                } else if (eventDetailsListView.getItemAtPosition(position).toString().contains(getResources().getString(R.string.textViewEnd))) {
//                    toTimePickerDialog.show();
//                } else {
//                    showInputBox(eventDetailsListView.getItemAtPosition(position).toString(), position);
//                }
//                return true;
//            }
//        });
//    }
//
//    public void showInputBox(String oldItem, final int index) {
//        final Dialog dialog = new Dialog(EventDetailsActivity.this);
//        dialog.setContentView(R.layout.event_details_editing_dialog);
//        TextView txtMessage = (TextView) dialog.findViewById(R.id.event_details_edit_textview);
//        txtMessage.setText(getResources().getText(R.string.textEventEdit));
//        final EditText editText = (EditText) dialog.findViewById(R.id.editTextInput);
//        editText.setText(oldItem);
//        Button buttonDone = (Button) dialog.findViewById(R.id.btdone);
//        Button buttonCancel = (Button) dialog.findViewById(R.id.btnCancel);
//        buttonDone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                eventDetails.set(index, editText.getText().toString());
//                eventDetailsAdapter.notifyDataSetChanged();
//                dialog.dismiss();
//            }
//        });
//        buttonCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), getResources().getText(R.string.notEditEventText), Toast.LENGTH_SHORT).show();
//                dialog.dismiss();
//            }
//        });
//
//        dialog.show();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.event_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_edit_event) {
            Intent i = new Intent(getApplicationContext(), EditCustomEventActivity.class);
            startActivity(i);
            Toast.makeText(getApplicationContext(), "Szerkeszt", Toast.LENGTH_SHORT).show();
        }

        if (id == R.id.action_delete_event) {
            Toast.makeText(getApplicationContext(), "Töröl", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}
