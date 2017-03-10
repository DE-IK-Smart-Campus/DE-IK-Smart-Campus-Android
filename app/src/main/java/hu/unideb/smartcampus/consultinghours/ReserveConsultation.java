package hu.unideb.smartcampus.consultinghours;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jivesoftware.smack.SmackException;

import java.text.DateFormat;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.xmpp.Connection;
import hu.unideb.smartcampus.xmpp.pojos.CHFromToDatesPojo;
import hu.unideb.smartcampus.xmpp.pojos.MessageTypeConsultingHourIdReasonDuration;

import static hu.unideb.smartcampus.xmpp.listeners.ConsultingHoursHandler.SIGNUPFORCONSULTINGHOURPROCESSMESSAGE;

/**
 * Created by Headswitcher on 2017. 02. 27..
 */

public class ReserveConsultation extends AppCompatActivity {

    private Long consultingHourId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_consultation);
        Intent intent = getIntent();
        consultingHourId = intent.getExtras().getLong("CONSULTINGHOURID");
        CHFromToDatesPojo fromUntilDates = intent.getExtras().getParcelable(getString(R.string.extra_from_until_dates));
        TextView fromUntilDatesTextView = (TextView) findViewById(R.id.selected_consulting_hour_editText);

        fromUntilDatesTextView.
                setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(fromUntilDates.getFrom())
                        + "-"
                        + DateFormat.getTimeInstance(DateFormat.SHORT).format(fromUntilDates.getTo()));

    }

    public void onReserveClick(View v) {

        TextView reasonView = (TextView) findViewById(R.id.consulting_hours_reason_editText);
        TextView durationView = (TextView) findViewById(R.id.consulting_hours_duration_editText);
        MessageTypeConsultingHourIdReasonDuration reasonDuration = new
                MessageTypeConsultingHourIdReasonDuration(
                SIGNUPFORCONSULTINGHOURPROCESSMESSAGE, consultingHourId.toString(), reasonView.getText().toString(), durationView.getText().toString(), Connection.getInstance().getUserJID());
        ObjectMapper objectMapper = new ObjectMapper();
        String request = null;
        try {
            request = objectMapper.writeValueAsString(reasonDuration);
            Connection.getInstance().createLoadingDialog(request, getSupportFragmentManager(), new Bundle());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

}
