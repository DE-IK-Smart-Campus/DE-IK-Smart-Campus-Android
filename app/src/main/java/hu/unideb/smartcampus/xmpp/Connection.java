package hu.unideb.smartcampus.xmpp;

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
import hu.unideb.smartcampus.fragment.LoadingDialogFragment;

import static android.content.ContentValues.TAG;
import static hu.unideb.smartcampus.main.activity.officehours.constant.OfficeHourConstant.DIALOGTAG;

/**
 * Created by Erdei Krisztián on 2017.03.03..
 */

public class Connection {

    public static final String ADMINJID = "smartcampus@192.168.0.43";
    public static final String HOSTNAME= "192.168.0.43";

    private final String adminJID = ADMINJID;
    private static Connection instance = null;
    private XMPPBOSHConnection xmppConnection;
    private Chat adminChat;
    private String userJID;


    protected Connection() {
    }

    public static Connection getInstance() {

        if (instance == null) {
            instance = new Connection();
        }
        return instance;
    }

    public XMPPBOSHConnection getXmppConnection() {
        return xmppConnection;
    }

    public void setXMPPTCPConnection(BOSHConfiguration config) {

        setLocalXmpptcpConnection(new XMPPBOSHConnection(config));

        // Will refactor TODO;
        try {
            xmppConnection.connect();
            xmppConnection.login();
            userJID = config.getUsername().toString();
            ChatManager chatManager = ChatManager.getInstanceFor(xmppConnection);
            Message uzi = new Message(adminJID);
            uzi.setBody("Kommunikáció kialakítása");
            adminChat = chatManager.createChat(adminJID); //TODO


//            try {
//                adminChat.sendMessage("");
//            } catch (SmackException.NotConnectedException e) {
//                e.printStackTrace();
//            }
//            adminChat.addMessageListener(new AdminListen());
//        } catch (XMPPException e) {
//            e.printStackTrace();
//        } catch (SmackException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        }
    }

    private void setLocalXmpptcpConnection(XMPPBOSHConnection xmpptcpConnection) {
        this.xmppConnection = xmpptcpConnection;
    }

    public Chat getAdminChat() {
        if (adminChat != null)

            return adminChat;

        else {
            throw new RuntimeException();
        }
    }

    public void createLoadingDialog(String toAdminMsg, FragmentManager fragmentManager, Bundle bundle) throws SmackException.NotConnectedException {
        LoadingDialogFragment loadingDialogFragment = (LoadingDialogFragment) fragmentManager.findFragmentByTag("DIALOGTAG");
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
        fragmentTransaction.replace(R.id.frame, loadingDialogFragment, DIALOGTAG);
        fragmentTransaction.addToBackStack(DIALOGTAG);
        fragmentTransaction.commit();
        Log.d(TAG, "Sent MSG: " + toAdminMsg);
        adminChat.sendMessage(new Message(adminJID, toAdminMsg));
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
