package hu.unideb.smartcampus.task.chat;

import android.os.AsyncTask;

import java.util.HashMap;

import hu.unideb.smartcampus.pojo.chat.ListUserChatsIqRequestPojo;

/**
 * Created by Headswitcher on 2017. 04. 11..
 */

public class ListUserChatsIqRequestTask extends AsyncTask<HashMap<String, String>, Integer, ListUserChatsIqRequestPojo> {

    public static final String STUDENT_PARAM_KEY = "STUDENT_PARAM_KEY";

    @Override
    protected ListUserChatsIqRequestPojo doInBackground(HashMap<String, String>... params) {

        return new ListUserChatsIqRequestPojo();
    }


}
