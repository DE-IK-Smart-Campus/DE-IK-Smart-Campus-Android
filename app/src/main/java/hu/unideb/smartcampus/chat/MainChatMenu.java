package hu.unideb.smartcampus.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import hu.unideb.smartcampus.R;

/**
 * Created by Headswitcher on 2017. 02. 22..
 */

public class MainChatMenu extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ListView chatRoomsList = (ListView) findViewById(R.id.chat_menu_ListView);
        List names = Arrays.asList("Holkai","Gabai","Atkai","Norkai","Filtián","Balai");
        // need function to get all the chat rooms available
        // need function - and brainstorm about chat history, what the xmpp server does

        ListAdapter listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,names);
        chatRoomsList.setAdapter(listAdapter);
    }
}
