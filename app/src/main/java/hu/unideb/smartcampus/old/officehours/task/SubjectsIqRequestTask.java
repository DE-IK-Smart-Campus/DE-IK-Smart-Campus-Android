package hu.unideb.smartcampus.old.officehours.task;

import android.os.AsyncTask;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.HashMap;

import hu.unideb.smartcampus.old.officehours.converter.OfficeHourConverter;
import hu.unideb.smartcampus.old.officehours.pojo.AskSubjectsPojo;
import hu.unideb.smartcampus.shared.iq.request.SubjectsIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;

/**
 * Created by Headswitcher on 2017. 03. 24..
 * //TODO
 */

public class SubjectsIqRequestTask extends AsyncTask<HashMap<String, String>, Integer, AskSubjectsPojo> {

    public static final String PARAM_ACTUAL_USER_JID = "ACTUAL_USER_JID";

    @Override
    protected AskSubjectsPojo doInBackground(HashMap<String, String>... params) {
            return  null;
    }
}
