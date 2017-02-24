package hu.unideb.smartcampus.consultinghours;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import hu.unideb.smartcampus.R;

/**
 * Created by Headswitcher on 2017. 02. 24..
 */

public class ConsultingHours extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulting_hours);
        ListView ClassList = (ListView) findViewById(R.id.consulting_hours_ExpandableListView);
        List ClassNames = Arrays.asList("Hálózati architektúrák és protokollok", "Programozási környezetek", "Adatbázisrendszerek megvalósítása 1", "A mesterséges intelligencia alapjai");
        // need function to get all the chat rooms available
        // need function - and brainstorm about chat history, what the xmpp server does

      //  ExpandableListAdapter expandableListAdapter = new ExpandableListAdapter() {
      //  }
      //  ClassList.setAdapter(listAdapter);
    }
}
