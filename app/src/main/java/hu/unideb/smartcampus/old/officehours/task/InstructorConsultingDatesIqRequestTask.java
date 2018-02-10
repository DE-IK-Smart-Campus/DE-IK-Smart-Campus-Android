package hu.unideb.smartcampus.old.officehours.task;

import android.os.AsyncTask;


import java.util.HashMap;

import hu.unideb.smartcampus.old.officehours.pojo.Instructor;


/**
 * Created by Headswitcher on 2017. 03. 24..
 * //TODO
 */

public class InstructorConsultingDatesIqRequestTask extends AsyncTask<HashMap<String, String>, Integer, Instructor> {
    @Override
    protected Instructor doInBackground(HashMap<String, String>[] hashMaps) {
        return null;
    }


    public static final String INSTRUCTOR_ID = "INSTRUCTOR_ID";

}