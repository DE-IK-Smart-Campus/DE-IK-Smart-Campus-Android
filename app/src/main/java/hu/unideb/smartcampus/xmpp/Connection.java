package hu.unideb.smartcampus.xmpp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.bosh.BOSHConfiguration;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jxmpp.jid.EntityFullJid;
import org.jxmpp.jid.EntityJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.LoginActivity;
import hu.unideb.smartcampus.activity.MainActivity_SmartCampus;
import hu.unideb.smartcampus.fragment.LoadingDialogFragment;
import hu.unideb.smartcampus.main.activity.officehours.pojo.BasePojo;
import hu.unideb.smartcampus.shared.iq.provider.AddMucChatIqProvider;
import hu.unideb.smartcampus.shared.iq.provider.AddUserChatIqProvider;
import hu.unideb.smartcampus.shared.iq.provider.InstructorConsultingDateIqProvider;
import hu.unideb.smartcampus.shared.iq.provider.SubjectRequestIqProvider;
import hu.unideb.smartcampus.shared.iq.provider.UserChatListIqProvider;
import hu.unideb.smartcampus.shared.iq.request.AddMucChatIqRequest;
import hu.unideb.smartcampus.shared.iq.request.AddUserChatIqRequest;
import hu.unideb.smartcampus.shared.iq.request.BaseSmartCampusIqRequest;
import hu.unideb.smartcampus.shared.iq.request.InstructorConsultingDatesIqRequest;
import hu.unideb.smartcampus.shared.iq.request.ListUserChatsIqRequest;
import hu.unideb.smartcampus.shared.iq.request.SubjectsIqRequest;

import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.DIALOG_TAG;
import static java.lang.Thread.sleep;

/**
 * Created by Erdei Krisztián on 2017.03.03..
 * TODO
 */

public class Connection {
    private static Connection instance = null;
    private static Context actualContext;
    public static final String HTTP_BASIC_AUTH_PATH = "http://wt2.inf.unideb.hu/smartcampus-backend/integration/retrieveUserData";
    public static final String ADMINJID = "smartcampus@wt2.inf.unideb.hu/Smartcampus";
    public static final String HOSTNAME = "wt2.inf.unideb.hu";
    public static EntityJid adminEntityJID;

    private BOSHConfiguration config;
    private EntityFullJid actualUserJid;
    private XMPPBOSHConnection xmppConnection;
    private String userJID;

    public static Context getActualContext() {
        return actualContext;
    }

    public static void setActualContext(Context actualContext) {
        Connection.actualContext = actualContext;
    }

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

    public boolean maintainConnection(Context actualContext) {
        boolean returnBool = true;
        this.actualContext = actualContext;
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
        return returnBool;
    }

    public void newActivity(Class<? extends AppCompatActivity> toActivity) {
        Intent intent = new Intent(actualContext, toActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        actualContext.startActivity(intent);
    }


    public void startBoshConnection(BOSHConfiguration config, Context actualContext) {
        this.actualContext = actualContext;
        this.config = config;
        if (maintainConnection(actualContext) && xmppConnection.isAuthenticated()) {
            ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(InstructorConsultingDatesIqRequest.ELEMENT);
            ProviderManager.addIQProvider(InstructorConsultingDatesIqRequest.ELEMENT, BaseSmartCampusIqRequest.BASE_NAMESPACE, new InstructorConsultingDateIqProvider());

            ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(AddMucChatIqRequest.ELEMENT);
            ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(AddUserChatIqRequest.ELEMENT);
            ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(ListUserChatsIqRequest.ELEMENT);

            ProviderManager.addIQProvider(AddMucChatIqRequest.ELEMENT, BaseSmartCampusIqRequest.BASE_NAMESPACE, new AddMucChatIqProvider());
            ProviderManager.addIQProvider(AddUserChatIqRequest.ELEMENT, BaseSmartCampusIqRequest.BASE_NAMESPACE, new AddUserChatIqProvider());
            ProviderManager.addIQProvider(ListUserChatsIqRequest.ELEMENT, BaseSmartCampusIqRequest.BASE_NAMESPACE, new UserChatListIqProvider());


            ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(SubjectsIqRequest.ELEMENT);
            ProviderManager.addIQProvider(SubjectsIqRequest.ELEMENT, BaseSmartCampusIqRequest.BASE_NAMESPACE, new SubjectRequestIqProvider());
            userJID = config.getUsername().toString();

            newActivity(MainActivity_SmartCampus.class);
        } else {
            newActivity(LoginActivity.class);
        }
    }


//// TODO: 2017. 03. 28. We need an unbreakable dialog

    public FragmentManager createLoadingDialog(FragmentManager fragmentManager, Bundle bundle) {
        LoadingDialogFragment loadingDialogFragment = (LoadingDialogFragment) fragmentManager.findFragmentByTag(DIALOG_TAG);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (loadingDialogFragment != null) {
            fragmentTransaction.remove(loadingDialogFragment);
            fragmentTransaction.commitNow();
        } else {
            loadingDialogFragment = new LoadingDialogFragment();

        }

        if (bundle == null) {
            throw new NullPointerException("Bundle can not be null");
        }

        if (loadingDialogFragment.getArguments() != null) {
            loadingDialogFragment.getArguments().putAll(bundle);
        } else {
            loadingDialogFragment.setArguments(bundle);
        }

        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, loadingDialogFragment, DIALOG_TAG);
        fragmentTransaction.addToBackStack(DIALOG_TAG);
        fragmentTransaction.commit();
        return fragmentManager;
    }

    public <T extends AsyncTask<HashMap<String, String>, Integer, P>, P extends BasePojo>
    P createLoadingDialog(T asyncIqTask, FragmentManager fragmentManager, HashMap<String, String> params) throws ExecutionException, InterruptedException {

        HashMap<String, String> asyncTaskParams = new HashMap<>();
        asyncTaskParams.put("ADMINJID", "Connection.getInstance().getAdminEntityJID().toString()");

        for (Map.Entry<String, String> entry : params.entrySet()) {
            asyncTaskParams.put(entry.getKey(), entry.getValue());
        }

        P pojoClass = asyncIqTask.execute(asyncTaskParams).get();

        return pojoClass;
    }

    public XMPPBOSHConnection getXmppConnection() {
        return xmppConnection;
    }

    public String getUserJID() {
        return userJID;
    }

    public void setUserJID(String userJID) {
        this.userJID = userJID;
    }

    public static String getADMINJID() {
        return ADMINJID;
    }

    public EntityJid getAdminEntityJID() {
        return adminEntityJID;
    }

    public EntityFullJid getActualUserJid() {
        return actualUserJid;
    }

    public void setActualUserJid(EntityFullJid actualUserJid) {
        this.actualUserJid = actualUserJid;
    }
}
