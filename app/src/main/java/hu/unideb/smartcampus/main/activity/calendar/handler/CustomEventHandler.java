package hu.unideb.smartcampus.main.activity.calendar.handler;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.jivesoftware.smack.SmackException;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.main.activity.calendar.fragment.CalendarFragment;
import hu.unideb.smartcampus.main.activity.calendar.pojo.AskCustomEventPojo;

public class CustomEventHandler {

    private AskCustomEventPojo askCustomEventPojo;
    private FragmentManager fragmentManager;
    private ObjectMapper objectMapper;
    private Context context;

    public CustomEventHandler(FragmentManager fragmentManager,Context context)throws SmackException.NotConnectedException, InterruptedException {
        this.askCustomEventPojo = new AskCustomEventPojo();
        this.fragmentManager = fragmentManager;
        this.objectMapper = new ObjectMapper();
        this.context = context;
    }

    public void sendDefaultMesg1() {
/*
        try{

            HashMap<String, String> param = new HashMap<>();
            AskCustomEventPojo askCustomEventPojo = Connection.getInstance().createLoadingDialog(new CustomEventIqRequestTask(), fragmentManager, param);

//            AskTimetableEventPojo askTimetableEventPojo = Connection.getInstance().createLoadingDialog(new TimetableIqRequestTask(), fragmentManager,param);

            Bundle bundle = new Bundle();

            List<CustomEvent> c = askCustomEventPojo.getCustomEvents();

            for(CustomEvent t : c) {

            }

            changeToCalndarFragmentView(bundle);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
    }

    private void changeToCalndarFragmentView(Bundle bundle) {
        Fragment fragment = new CalendarFragment();
        fragment.setArguments(bundle);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, "CALENDAR_TAG");
        fragmentTransaction.commitAllowingStateLoss();
    }
}
