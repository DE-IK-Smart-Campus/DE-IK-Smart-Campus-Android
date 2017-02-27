package hu.unideb.smartcampus.consultinghours;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import hu.unideb.smartcampus.R;

/**
 * Created by Headswitcher on 2017. 02. 27..
 */

public class ReserveConsultation extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_consultation);
        Intent intent = getIntent();
        String fromUntilDates = intent.getStringExtra("FROMUNTILDATES");
        TextView fromUntilDatesTextView = (TextView) findViewById(R.id.selected_consulting_hour_editText);
        fromUntilDatesTextView.setText(fromUntilDates);


    }

}
