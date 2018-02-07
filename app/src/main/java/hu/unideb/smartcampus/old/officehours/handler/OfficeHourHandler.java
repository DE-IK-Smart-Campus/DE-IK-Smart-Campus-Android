package hu.unideb.smartcampus.old.officehours.handler;

/**
 * This is the controller class for the OfficeFragment
 */

public class OfficeHourHandler {

//    public static final String SELECTED_OFFICE_HOUR_ID = "selectedOfficeHourId";
//    public static final String EXTRA_FROM_UNTIL_DATES = "EXTRA_FROM_UNTIL_DATES";
//    public static final String DIALOG_TAG = "DIALOG_TAG";
//    public static final String OFFICE_HOURS_TAG = "OFFICE_HOURS_TAG";
//    public static final String SELECTED_OFFICE_HOUR_ALREADY_RESERVED_SUM = "SELECTED_OFFICE_HOUR_ALREADY_RESERVED_SUM";
//    public static final String SELECTED_INSTRUCTOR_NAME = "SELECTED_INSTRUCTOR_NAME";
//
//    public enum Status {
//        ASKSUBJECTS,
//        ASKINSTRUCTOROFFICEHOURS,
//    }
//
//    private Status status;
//
//    boolean isDBFinised = false;
//    boolean isNetworkFinished = false;
//
//    private AskSubjectsPojo askSubjectsPojo;
//    private FragmentManager fragmentManager;
//    private Instructor selectedInstructor;
//    private Context context;
//
//    static OfficeHourHandler instance;
//
//    public static OfficeHourHandler getInstance() {
//        if (instance == null) {
//            instance = new OfficeHourHandler();
//        }
//        return instance;
//    }
//
//    public OfficeHourHandler() {
//        status = Status.ASKSUBJECTS;
//    }
//
//    public void askSubjectsFromDb(FragmentManager fragmentManager) {
//        try {
//            isDBFinised = false;
//            final Connection connection = Connection.getInstance();
//            this.fragmentManager = fragmentManager;
//            status = Status.ASKSUBJECTS;
//            HashMap<String, String> param = new HashMap<>();
//            param.put(PARAM_ACTUAL_USER_JID, connection.getXmppConnection().getUser().getLocalpartOrThrow().toString());
//            askSubjectsPojo = connection.runAsyncTask(new SubjectsIqRequestTask(), param);
//            isDBFinised = true;
//            changeToOfficeFragmentView(new Bundle());
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void askSubjects(FragmentManager fragmentManager, final Context context) {
//        try {
//            isNetworkFinished = false;
//            final Connection connection = Connection.getInstance();
//            this.fragmentManager = fragmentManager;
//            status = Status.ASKSUBJECTS;
//            HashMap<String, String> param = new HashMap<>();
//            param.put(PARAM_ACTUAL_USER_JID, connection.getXmppConnection().getUser().getLocalpartOrThrow().toString());
//            askSubjectsPojo = connection.runAsyncTask(new SubjectsIqRequestTask(), param);
//
//         /*   new Thread(new Runnable() {
//                public void run() {
//                    DatabaseManager databaseManager = new DatabaseManager(context);
//                    databaseManager.open();
//                    databaseManager.setAllSubjectAndInstructor(askSubjectsPojo.getSubjects());
//                    databaseManager.close();
//                    Log.e("DatebaseThread", "run: Finished!!");
//                }
//            }).start();
//        */
//
//            isNetworkFinished = true;
//            changeToOfficeFragmentView(new Bundle());
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void askInstructorOfficehours(FragmentManager fragmentManager, Instructor selectedInstructor) {
//        isNetworkFinished = false;
//        HashMap<String, String> params = new HashMap<>();
//        final String selectedInstructorId = selectedInstructor.getInstructorId().toString();
//        params.put(INSTRUCTOR_ID, selectedInstructorId);
//        try {
//            this.selectedInstructor = Connection.getInstance().runAsyncTask(new InstructorConsultingDatesIqRequestTask(), params);
//            this.selectedInstructor.setName(selectedInstructor.getName());
//        } catch (ExecutionException | InterruptedException e) {
//            e.printStackTrace();
//        }
//        this.fragmentManager = fragmentManager;
//        status = Status.ASKINSTRUCTOROFFICEHOURS;
//        changeToOfficeFragmentView(new Bundle());
//        isNetworkFinished = true;
//    }
//
//    public void changeToOfficeFragmentView(Bundle bundle) {
//        Fragment fragment = new OfficeHourFragment();
//        fragment.setArguments(bundle);
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                android.R.anim.fade_out);
//        fragmentTransaction.replace(R.id.frame, fragment, OFFICE_HOURS_TAG);
//        fragmentTransaction.commitAllowingStateLoss();
//    }
//
//    public void changeToOfficeReserveActivity(OfficeHour selectedOfficeHour) {
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(EXTRA_FROM_UNTIL_DATES, selectedOfficeHour.getFromToDates());
//        bundle.putLong(SELECTED_OFFICE_HOUR_ID, selectedOfficeHour.getConsultingHourId());
//        bundle.putLong(SELECTED_OFFICE_HOUR_ALREADY_RESERVED_SUM, selectedOfficeHour.getReservedSum());
//        bundle.putString(SELECTED_INSTRUCTOR_NAME, selectedInstructor.getName());
//        Fragment fragment = new OfficeHourReserveFragment();
//        fragment.setArguments(bundle);
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                android.R.anim.fade_out);
//        fragmentTransaction.replace(R.id.frame, fragment, OFFICE_HOURS_TAG);
//        fragmentTransaction.commitAllowingStateLoss();
//    }
//
//    public void onBackPressed() {
//        status = Status.ASKSUBJECTS;
//    }
//
//    public Instructor getSelectedInstructor() {
//        return selectedInstructor;
//    }
//
//    public Status getStatus() {
//        return status;
//    }
//
//    public AskSubjectsPojo getAskSubjectsPojo() {
//        return askSubjectsPojo;
//    }
//
//    public FragmentManager getFragmentManager() {
//        return fragmentManager;
//    }
//
//    public Context getContext() {
//        return context;
//    }
//
//    public void setContext(Context context) {
//        this.context = context;
//    }
}
