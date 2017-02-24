package hu.unideb.smartcampus.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import hu.unideb.smartcampus.R;

public class Chat extends FragmentActivity {

    private Button button;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_chat);
        button = (Button) findViewById(R.id.button);

//        button.setOnClickListener(this);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.button:
//                Toast.makeText(getApplicationContext(), R.string.loginSucces, Toast.LENGTH_SHORT).show();
//                break;
//        }
//    }


}
