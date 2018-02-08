package hu.unideb.smartcampus.xmpp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.bosh.BOSHConfiguration;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jxmpp.jid.EntityJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.application.MainApplication;
import hu.unideb.smartcampus.pojo.login.ActualUserInfo;
import hu.unideb.smartcampus.shared.iq.provider.AddCustomEventIqProvider;
import hu.unideb.smartcampus.shared.iq.provider.AddMucChatIqProvider;
import hu.unideb.smartcampus.shared.iq.provider.AddUserChatIqProvider;
import hu.unideb.smartcampus.shared.iq.provider.CalendarSubjectsIqProvider;
import hu.unideb.smartcampus.shared.iq.provider.DeleteCustomEventIqProvider;
import hu.unideb.smartcampus.shared.iq.provider.InstructorConsultingDateIqProvider;
import hu.unideb.smartcampus.shared.iq.provider.ListCustomEventIqProvider;
import hu.unideb.smartcampus.shared.iq.provider.SubjectRequestIqProvider;
import hu.unideb.smartcampus.shared.iq.provider.UserChatListIqProvider;
import hu.unideb.smartcampus.shared.iq.request.AddCustomEventIqRequest;
import hu.unideb.smartcampus.shared.iq.request.AddMucChatIqRequest;
import hu.unideb.smartcampus.shared.iq.request.AddUserChatIqRequest;
import hu.unideb.smartcampus.shared.iq.request.BaseSmartCampusIqRequest;
import hu.unideb.smartcampus.shared.iq.request.CalendarSubjectsIqRequest;
import hu.unideb.smartcampus.shared.iq.request.DeleteCustomEventIqRequest;
import hu.unideb.smartcampus.shared.iq.request.InstructorConsultingDatesIqRequest;
import hu.unideb.smartcampus.shared.iq.request.ListCustomEventIqRequest;
import hu.unideb.smartcampus.shared.iq.request.ListUserChatsIqRequest;
import hu.unideb.smartcampus.shared.iq.request.SubjectsIqRequest;

import static java.lang.Thread.sleep;

public class Connection {
    private static final String TAG = Connection.class.getSimpleName();

    private static MainApplication mainApplication = new MainApplication();

    private static Connection instance = null;

    public static final String HTTP_BASIC_AUTH_PATH = mainApplication.getContext().getResources().getString(R.string.SMARTCAMPUS_BASIC_AUTH_PATH);
    public static final String ADMINJID = mainApplication.getContext().getResources().getString(R.string.SMARTCAMPUS_ADMIN_JID);
    public static final String HOSTNAME = mainApplication.getContext().getResources().getString(R.string.SMARTCAMPUS_HOSTNAME);
    public static EntityJid adminEntityJID;

    private BOSHConfiguration config;
    private XMPPBOSHConnection xmppConnection;
    private String userJID;
    private Context context;

    protected Connection() {
        try {
            adminEntityJID = (EntityJid) JidCreate.from(ADMINJID);
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
    }

    public static Connection getInstance() {
        if (instance == null) {
            instance = new Connection();
        }
        return instance;
    }


    public static void startConnection(ActualUserInfo actualUserInfo, Context context) {
        BOSHConfiguration config = null;
        try {
            config = BOSHConfiguration.builder()
                    .setUsernameAndPassword(actualUserInfo.getUsername(), actualUserInfo.getXmppPassword())
                    .setXmppDomain(HOSTNAME)
                    .setHost(mainApplication.getContext().getResources().getString(R.string.SMARTCAMPUS_HOST))
                    .setPort(80)
                    .setFile(mainApplication.getContext().getResources().getString(R.string.SMARTCAMPUS_FILE))
                    .setResource(mainApplication.getContext().getResources().getString(R.string.SMARTCAMPUS_RESOURCE))
                    .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                    .setDebuggerEnabled(false)
                    .build();
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }

        Connection connection = Connection.getInstance();
        connection.setContext(context);
        connection.setConfig(config);
        connection.maintainConnection();
        if (connection.xmppConnection.isAuthenticated()) {
            connection.discoverFeatures();
            if (connection.xmppConnection.getConfiguration() != null) {
                connection.setUserJID(connection.xmppConnection.getConfiguration().toString());
            }
        }
    }

    private void maintainConnection() {
        xmppConnection = new XMPPBOSHConnection(config);
        try {
            if (!xmppConnection.isConnected()) {

                xmppConnection.connect();
                sleep(SmackConfiguration.getDefaultReplyTimeout());
                xmppConnection.login();
            } else if (!xmppConnection.isAuthenticated()) {
                xmppConnection.login();
            }
        } catch (InterruptedException | IOException | SmackException | XMPPException e) {
            e.printStackTrace();
        }
    }

    public void newActivity(Class<? extends AppCompatActivity> toActivity) {
        Intent intent = new Intent(context, toActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

//    public FragmentManager createLoadingDialogFragment(FragmentManager fragmentManager, Bundle bundle) {
//        LoadingDialogFragment loadingDialogFragment = (LoadingDialogFragment) fragmentManager.findFragmentByTag(DIALOG_TAG);
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//
//        if (loadingDialogFragment != null) {
//            fragmentTransaction.remove(loadingDialogFragment);
//            fragmentTransaction.commitNow();
//        } else {
//            loadingDialogFragment = new LoadingDialogFragment();
//        }
//
//        if (bundle == null) {
//            throw new NullPointerException("Bundle can not be null");
//        }
//
//        if (loadingDialogFragment.getArguments() != null) {
//            loadingDialogFragment.getArguments().putAll(bundle);
//        } else {
//            loadingDialogFragment.setArguments(bundle);
//        }
//
//        fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
//        fragmentTransaction.replace(R.id.frame, loadingDialogFragment, DIALOG_TAG);
//        fragmentTransaction.addToBackStack(DIALOG_TAG);
//        fragmentTransaction.commit();
//        Log.i(CONNECTION_TAG, "createLoadingDialogFragment: Replaced!");
//        return fragmentManager;
//    }

//    public <T extends AsyncTask<HashMap<String, String>, Integer, P>, P extends BasePojo>
//    P runAsyncTask(T asyncIqTask, HashMap<String, String> params) throws ExecutionException, InterruptedException {
//
//        HashMap<String, String> asyncTaskParams = new HashMap<>();
//
//        for (Map.Entry<String, String> entry : params.entrySet()) {
//            asyncTaskParams.put(entry.getKey(), entry.getValue());
//        }
//
//        P pojoClass = asyncIqTask.execute(asyncTaskParams).get();
//
//        return pojoClass;
//    }

    public XMPPBOSHConnection getXmppConnection() {
        return xmppConnection;
    }

    private void setUserJID(String userJID) {
        this.userJID = userJID;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    private void setConfig(BOSHConfiguration config) {
        this.config = config;
    }

    private void discoverFeatures() {

        ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(InstructorConsultingDatesIqRequest.ELEMENT);
        ProviderManager.addIQProvider(InstructorConsultingDatesIqRequest.ELEMENT, BaseSmartCampusIqRequest.BASE_NAMESPACE, new InstructorConsultingDateIqProvider());

        ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(SubjectsIqRequest.ELEMENT);
        ProviderManager.addIQProvider(SubjectsIqRequest.ELEMENT, BaseSmartCampusIqRequest.BASE_NAMESPACE, new SubjectRequestIqProvider());

        ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(CalendarSubjectsIqRequest.ELEMENT);
        ProviderManager.addIQProvider(CalendarSubjectsIqRequest.ELEMENT, BaseSmartCampusIqRequest.BASE_NAMESPACE, new CalendarSubjectsIqProvider());

        ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(ListCustomEventIqRequest.ELEMENT);
        ProviderManager.addIQProvider(ListCustomEventIqRequest.ELEMENT, BaseSmartCampusIqRequest.BASE_NAMESPACE, new ListCustomEventIqProvider());

        ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(AddCustomEventIqRequest.ELEMENT);
        ProviderManager.addIQProvider(AddCustomEventIqRequest.ELEMENT, BaseSmartCampusIqRequest.BASE_NAMESPACE, new AddCustomEventIqProvider());

        ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(DeleteCustomEventIqRequest.ELEMENT);
        ProviderManager.addIQProvider(DeleteCustomEventIqRequest.ELEMENT, BaseSmartCampusIqRequest.BASE_NAMESPACE, new DeleteCustomEventIqProvider());


        ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(AddMucChatIqRequest.ELEMENT);
        ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(AddUserChatIqRequest.ELEMENT);
        ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(ListUserChatsIqRequest.ELEMENT);

        ProviderManager.addIQProvider(AddMucChatIqRequest.ELEMENT, BaseSmartCampusIqRequest.BASE_NAMESPACE, new AddMucChatIqProvider());
        ProviderManager.addIQProvider(AddUserChatIqRequest.ELEMENT, BaseSmartCampusIqRequest.BASE_NAMESPACE, new AddUserChatIqProvider());
        ProviderManager.addIQProvider(ListUserChatsIqRequest.ELEMENT, BaseSmartCampusIqRequest.BASE_NAMESPACE, new UserChatListIqProvider());

        ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(SubjectsIqRequest.ELEMENT);
        ProviderManager.addIQProvider(SubjectsIqRequest.ELEMENT, BaseSmartCampusIqRequest.BASE_NAMESPACE, new SubjectRequestIqProvider());

    }
}
