package hu.unideb.smartcampus.main.activity.officehours.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.packet.Message;

import java.text.DateFormat;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.HomeFragment;
import hu.unideb.smartcampus.main.activity.officehours.json.MessageTypeConsultingHourIdReasonDuration;
import hu.unideb.smartcampus.main.activity.officehours.pojo.FromToDatesInLong;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.EXTRA_FROM_UNTIL_DATES;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.SELECTED_OFFICE_HOUR_ID;
import static hu.unideb.smartcampus.main.activity.officehours.handler.OfficeHourHandler.SIGNUPFORCONSULTINGHOURPROCESSMESSAGE;

/**
 * This activity responsible for the selected {@link hu.unideb.smartcampus.main.activity.officehours.pojo.OfficeHour}
 *
 * Created by Headswitcher on 2017. 02. 27..
 */

public class ReserveOfficeHourActivity extends AppCompatActivity {


    private Long selectedOfficeHourId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_consultation);
        Intent intent = getIntent();
        selectedOfficeHourId = intent.getExtras().getLong(SELECTED_OFFICE_HOUR_ID);
        FromToDatesInLong fromUntilDates = intent.getExtras().getParcelable(EXTRA_FROM_UNTIL_DATES);

        //Show selected office hour times
        TextView fromUntilDatesTextView = (TextView) findViewById(R.id.selected_consulting_hour_editText);
        if (fromUntilDates != null) {
            fromUntilDatesTextView.
                    setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(fromUntilDates.getFrom())
                            + "-"
                            + DateFormat.getTimeInstance(DateFormat.SHORT).format(fromUntilDates.getTo()));
        } else {
            throw new NullPointerException("intent.getExtras().getParcelable(EXTRA_FROM_UNTIL_DATES) IS NULL");
        }

    }

    public void onReserveClick(View v) {
        TextView reasonView = (TextView) findViewById(R.id.consulting_hours_reason_editText);
        TextView durationView = (TextView) findViewById(R.id.consulting_hours_duration_editText);

        //TODO mvc
        MessageTypeConsultingHourIdReasonDuration toJson = new
                MessageTypeConsultingHourIdReasonDuration(
                SIGNUPFORCONSULTINGHOURPROCESSMESSAGE, selectedOfficeHourId.toString(), reasonView.getText().toString(), durationView.getText().toString(), Connection.getInstance().getUserJID());

        ObjectMapper objectMapper = new ObjectMapper();
        try {

            String request = objectMapper.writeValueAsString(toJson);
            Connection.getInstance().getAdminChat().sendMessage(new Message(Connection.getInstance().getAdminJID(), request));
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.frame, new HomeFragment());
            fragmentTransaction.commit();
            getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        } catch (JsonProcessingException | SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
    }

}
