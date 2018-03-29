package hu.unideb.smartcampus.task.calendar;

import android.app.Activity;
import android.os.AsyncTask;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import hu.unideb.smartcampus.dialog.loading.LoadingDialog;
import hu.unideb.smartcampus.converter.calendar.CustomEventConverter;
import hu.unideb.smartcampus.pojo.calendar.AskCustomEventPojo;
//import hu.unideb.smartcampus.shared.iq.request.ListCustomEventIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.container.Container.LOADING_DIALOG_TAG;
import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;

public class CustomEventTask extends AsyncTask<String, Long, AskCustomEventPojo> {

    private LoadingDialog loadingDialog;
    private Activity activity;

    public CustomEventTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected AskCustomEventPojo doInBackground(String... strings) {
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = new LoadingDialog();
        loadingDialog.show(activity.getFragmentManager(), LOADING_DIALOG_TAG);
    }

//    @Override
//    protected AskCustomEventPojo doInBackground(String... strings) {
//        final Connection connection = Connection.getInstance();
//        try {
//
//            ListCustomEventIqRequest iq = new ListCustomEventIqRequest();
//            iq.setStudent(connection.getXmppConnection().getUser().getLocalpartOrThrow().toString());
//            iq.setType(IQ.Type.get);
//            iq.setTo(JidCreate.from(ADMINJID));
//
//            final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(iq);
//            final ListCustomEventIqRequest listCustomEventIqRequest = stanzaCollectorAndSend.nextResultOrThrow(5000);
//            return CustomEventConverter.convertToAskCustomEventPojo(listCustomEventIqRequest);
//
//        } catch (SmackException.NotConnectedException
//                | XMPPException.XMPPErrorException
//                | SmackException.NoResponseException
//                | XmppStringprepException
//                | InterruptedException e) {
//            e.printStackTrace();
//        }
//        return new AskCustomEventPojo();
//    }

    @Override
    protected void onPostExecute(AskCustomEventPojo askCustomEventPojo) {
        super.onPostExecute(askCustomEventPojo);
        loadingDialog.dismiss();
    }
}
