package hu.unideb.smartcampus.consultinghours;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.FromUntilDates;

/**
 * Created by Headswitcher on 2017. 02. 27..
 */

public class ReserveConsultation extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_consultation);
        Intent intent = getIntent();
        FromUntilDates fromUntilDates = intent.getExtras().getParcelable(getString(R.string.EXTRA_FROM_UNTIL_DATES));
        TextView fromUntilDatesTextView = (TextView) findViewById(R.id.selected_consulting_hour_editText);

        fromUntilDatesTextView.
                setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(fromUntilDates.getFrom())
                        + "-"
                        + DateFormat.getTimeInstance(DateFormat.SHORT).format(fromUntilDates.getUntil()));

    }

    public void onReserveClick(View v) {

        Toast.makeText(this, "TODO", Toast.LENGTH_SHORT).show(); // TODO

    }

}
