package hu.unideb.smartcampus.activity.exception;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class CrashReportActivity extends AppCompatActivity {

    private String stackTrace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_crash_report);
//        ButterKnife.bind(this);
//        Intent intent = getIntent();
//        stackTrace = intent.getStringExtra(STACKTRACE);
    }

//    @OnClick(R.id.error_page_close_button)
//    public void closeErrorPage() {
//        String manufacturer = Build.MANUFACTURER;
//        String model = Build.MODEL;
//        int version = Build.VERSION.SDK_INT;
//        String versionRelease = Build.VERSION.RELEASE;
//        String android = "Manufacturer: " + manufacturer + "\nModel: " + model + "\nAndroid Version: " + String.valueOf(version) + "\nAndroid Release Version: " + versionRelease + "\n";
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            String jsonInString = objectMapper.writeValueAsString(ErrorBuilder.createOnlyErrorMessages(android + "\n\n" + stackTrace));
//            new ErrorSendTask(this).execute(jsonInString);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//        finish();
//    }

}