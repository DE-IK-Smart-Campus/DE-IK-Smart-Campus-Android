package hu.unideb.smartcampus.xmpp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.bosh.BOSHConfiguration;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.packet.Message;

import java.io.IOException;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.activity.LoginActivity;
import hu.unideb.smartcampus.activity.MainActivity_SmartCampus;
import hu.unideb.smartcampus.fragment.LoadingDialogFragment;

import static android.content.ContentValues.TAG;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.DIALOG_TAG;

/**
 * Created by Erdei Krisztián on 2017.03.03..
 * TODO
 */

public class Connection {

    private static Connection instance = null;
    private static Context actualContext;
    private BOSHConfiguration config;

    public static final String ADMINJID = "smartcampus@wt2.inf.unideb.hu";
    public static final String HOSTNAME = "wt2.inf.unideb.hu";


    private final String adminJID = ADMINJID;

    private XMPPBOSHConnection xmppConnection;
    private Chat adminChat;
    private String userJID;

    public static Context getActualContext() {
        return actualContext;
    }

    public static void setActualContext(Context actualContext) {
        Connection.actualContext = actualContext;
    }

    protected Connection() {
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
                xmppConnection.login();
            } catch (SmackException | IOException | XMPPException e) {
                Intent intent = new Intent(actualContext, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                actualContext.startActivity(intent);
                e.printStackTrace();
            }

        }
    }

    public void startBoshConnection(BOSHConfiguration config, Context actualContext) {
        this.actualContext = actualContext;
        xmppConnection = new XMPPBOSHConnection(config);
        checkConnection(actualContext);
        userJID = config.getUsername().toString();
        ChatManager chatManager = ChatManager.getInstanceFor(xmppConnection);
        adminChat = chatManager.createChat(adminJID); //TODO
        Intent intent = new Intent(actualContext, MainActivity_SmartCampus.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        actualContext.startActivity(intent);
    }

    public Chat getAdminChat() {
        if (adminChat != null)

            return adminChat;

        else {
            throw new RuntimeException();
        }
    }

    public void createLoadingDialog(String toAdminMsg, FragmentManager fragmentManager, Bundle bundle) {
        LoadingDialogFragment loadingDialogFragment = (LoadingDialogFragment) fragmentManager.findFragmentByTag(DIALOG_TAG);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (loadingDialogFragment != null) {
            fragmentTransaction.remove(loadingDialogFragment);
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
        fragmentTransaction.commitNow();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, loadingDialogFragment, DIALOG_TAG);
        fragmentTransaction.addToBackStack(DIALOG_TAG);
        fragmentTransaction.commit();
        Log.d(TAG, "Sent MSG: " + toAdminMsg);

        try {
            Connection.getInstance().checkConnection(actualContext);
            adminChat.sendMessage(new Message(adminJID, toAdminMsg));
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }
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

    public String getAdminJID() {
        return adminJID;
    }
}
