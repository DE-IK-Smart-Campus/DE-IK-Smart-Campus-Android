package hu.unideb.smartcampus.xmpp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.bosh.BOSHConfiguration;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.provider.ProviderManager;
import org.jivesoftware.smackx.disco.ServiceDiscoveryManager;
import org.jxmpp.jid.EntityFullJid;
import org.jxmpp.jid.EntityJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.LoginActivity;
import hu.unideb.smartcampus.activity.MainActivity_SmartCampus;
import hu.unideb.smartcampus.fragment.LoadingDialogFragment;
import hu.unideb.smartcampus.main.activity.officehours.pojo.BasePojo;
import hu.unideb.smartcampus.shared.iq.provider.InstructorConsultingDateIqProvider;
import hu.unideb.smartcampus.shared.iq.provider.SubjectRequestIqProvider;
import hu.unideb.smartcampus.shared.iq.request.BaseSmartCampusIq;
import hu.unideb.smartcampus.shared.iq.request.InstructorConsultingDatesIqRequest;
import hu.unideb.smartcampus.shared.iq.request.SubjectsIqRequest;

import static android.content.ContentValues.TAG;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.DIALOG_TAG;
import static java.lang.Thread.sleep;
import static org.jxmpp.jid.impl.JidCreate.entityBareFrom;

/**
 * Created by Erdei Krisztián on 2017.03.03..
 * TODO
 */

public class Connection {

    private static Connection instance = null;
    private static Context actualContext;
    private BOSHConfiguration config;

    public static final String HTTP_BASIC_AUTH_PATH = "http://wt2.inf.unideb.hu/smartcampus-backend/integration/retrieveUserData";
    public static final String ADMINJID = "smartcampus@wt2.inf.unideb.hu/Smartcampus";
    public static final String HOSTNAME = "wt2.inf.unideb.hu";
    public static EntityJid adminEntityJID;

    private EntityFullJid actualUserJid;

    private XMPPBOSHConnection xmppConnection;
    private Chat adminChat;
    private ChatManager chatManager;
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

    private void checkConnection(Context actualContext) {
        if (!xmppConnection.isConnected()) {
            try {
                xmppConnection.connect();
                sleep(SmackConfiguration.getDefaultReplyTimeout());
                xmppConnection.login();
                actualUserJid = xmppConnection.getUser();

            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(actualContext, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            actualContext.startActivity(intent);
        }
    }


    public void startBoshConnection(BOSHConfiguration config, Context actualContext) {
        this.actualContext = actualContext;
        xmppConnection = new XMPPBOSHConnection(config);
        checkConnection(actualContext);
        if (xmppConnection.isAuthenticated()) {


            ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(InstructorConsultingDatesIqRequest.ELEMENT);
            ProviderManager.addIQProvider(InstructorConsultingDatesIqRequest.ELEMENT, BaseSmartCampusIq.BASE_NAMESPACE, new InstructorConsultingDateIqProvider());
            ServiceDiscoveryManager.getInstanceFor(xmppConnection).addFeature(SubjectsIqRequest.ELEMENT);
            ProviderManager.addIQProvider(SubjectsIqRequest.ELEMENT, BaseSmartCampusIq.BASE_NAMESPACE, new SubjectRequestIqProvider());

            userJID = config.getUsername().toString();
            ChatManager chatManager = ChatManager.getInstanceFor(xmppConnection);
            try {
                adminChat = chatManager.chatWith(entityBareFrom(ADMINJID));
            } catch (XmppStringprepException e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(actualContext, MainActivity_SmartCampus.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            actualContext.startActivity(intent);
        }
        Log.e(TAG, "startBoshConnection: " + xmppConnection.isConnected());
    }


    public Chat getAdminChat() {
        if (adminChat != null)

            return adminChat;

        else {
            throw new RuntimeException();
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
            throw new NullPointerException("Bundle was null");
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

    public ChatManager getChatManager() {
        return chatManager;
    }

    public EntityFullJid getActualUserJid() {
        return actualUserJid;
    }

    public void setActualUserJid(EntityFullJid actualUserJid) {
        this.actualUserJid = actualUserJid;
    }
}