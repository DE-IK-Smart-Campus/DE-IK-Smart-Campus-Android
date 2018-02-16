package hu.unideb.smartcampus.task.chat;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;

import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.mam.MamManager;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;
import org.jxmpp.util.XmppStringUtils;

import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.converter.chat.ChatConverter;
import hu.unideb.smartcampus.dialog.loading.LoadingDialog;
import hu.unideb.smartcampus.old.chat.fragment.ChatMainMenuFragment;
import hu.unideb.smartcampus.pojo.chat.ChatItem;
import hu.unideb.smartcampus.pojo.chat.GetChatsPojo;
import hu.unideb.smartcampus.pojo.chat.ListUserChatsIqRequestPojo;
import hu.unideb.smartcampus.shared.iq.request.ListUserChatsIqRequest;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.dialog.loading.LoadingDialog.LOADING_DIALOG_TAG;
import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;

/**
 * Created by Headswitcher on 2018. 02. 15..
 */

public class GetChatsTask extends AsyncTask<Void, Long, GetChatsPojo> {

    public static String GET_CHATS_KEY = "GET_CHATS_KEY";
    public final static String CHAT_MAIN_MENU_FRAGMENT_KEY = "CHAT_MAIN_MENU_FRAGMENT";


    private LoadingDialog loadingDialog;

    private Activity activity;

    public GetChatsTask(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = new LoadingDialog();
        loadingDialog.show(activity.getFragmentManager(), LOADING_DIALOG_TAG);
    }

    @Override
    protected GetChatsPojo doInBackground(Void... voids) {
        List<ChatItem> chatItemList = new ArrayList<>();
        Message message;

        MamManager mamManager = MamManager.getInstanceFor(Connection.getInstance().getXmppConnection());

        List<Jid> twoUserChatJids = new ArrayList<>();
        List<Jid> multiUserChat = new ArrayList<>();

        final Connection connection = Connection.getInstance();

        try {
            //todo stream :c
            ListUserChatsIqRequest iqRequest = new ListUserChatsIqRequest();
            iqRequest.setStudent(connection.getXmppConnection().getUser().getLocalpartOrThrow().toString());
            iqRequest.setType(IQ.Type.get);
            iqRequest.setTo(JidCreate.from(ADMINJID));

            final StanzaCollector stanzaCollectorAndSend = connection.getXmppConnection().createStanzaCollectorAndSend(iqRequest);
            final ListUserChatsIqRequest listUserChatsIqRequest = stanzaCollectorAndSend.nextResultOrThrow(SmackConfiguration.getDefaultReplyTimeout());
            final ListUserChatsIqRequestPojo listUserChatsIqRequestPojo = ChatConverter.convertToListUserChatsIqRequestPojo(listUserChatsIqRequest);

            for (String userJidInString : listUserChatsIqRequestPojo.getChatList()) {
                twoUserChatJids.add(JidCreate.entityBareFrom(userJidInString));
            }
            for (String userJidInString : listUserChatsIqRequestPojo.getMucChatList()) {
                multiUserChat.add(JidCreate.entityBareFrom(userJidInString));
            }
        } catch (XMPPException | SmackException | InterruptedException | XmppStringprepException e) {
            e.printStackTrace();
        }


        //TODO refactor into seperate method toChatMsg
        for (int i = 0; i < twoUserChatJids.size(); i++) {
            try {
                message = null;
                MamManager.MamQueryResult queryResult = mamManager.mostRecentPage(twoUserChatJids.get(i), 1);
                if (queryResult.forwardedMessages.size() > 0) {
                    message = (Message) queryResult.forwardedMessages.get(0).getForwardedStanza();
                }
                VCardManager vCardManager = VCardManager.getInstanceFor(Connection.getInstance().getXmppConnection());
                VCard vCard;
                if (message != null) {
                    String messageForMeaddition = message.getBody();
                    if (StringUtils.equals(message.getFrom().asBareJid(), Connection.getInstance().getXmppConnection().getUser().asBareJid())) {
                        messageForMeaddition = "Én" + messageForMeaddition;
                        vCard = vCardManager.loadVCard(message.getTo().asEntityBareJidOrThrow());
                    } else {
                        vCard = vCardManager.loadVCard(message.getFrom().asEntityBareJidOrThrow());
                    }
                    chatItemList.add(new ChatItem(
                            vCard,
                            twoUserChatJids.get(i),
                            XmppStringUtils.parseBareJid(twoUserChatJids.get(i).getLocalpartOrThrow().toString()),
                            ChatItem.Type.SINGLE, messageForMeaddition));
                } else {
                    vCard = vCardManager.loadVCard(twoUserChatJids.get(i).asEntityBareJidOrThrow());
                    chatItemList.add(new ChatItem(
                            vCard,
                            twoUserChatJids.get(i),
                            XmppStringUtils.parseBareJid(twoUserChatJids.get(i).getLocalpartOrThrow().toString()),
                            ChatItem.Type.SINGLE, ""));
                }
            } catch (XMPPException.XMPPErrorException | SmackException.NotLoggedInException | InterruptedException | SmackException.NotConnectedException | SmackException.NoResponseException e) {
                e.printStackTrace();
            }

            return new GetChatsPojo(chatItemList);

        }
        return new GetChatsPojo(chatItemList);
    }

    @Override
    protected void onPostExecute(GetChatsPojo getChatsPojo) {
        super.onPostExecute(getChatsPojo);
        loadingDialog.dismiss();

        if (getChatsPojo != null) {

            ChatMainMenuFragment fragment = new ChatMainMenuFragment();
            Bundle bundle = new Bundle();

            bundle.putSerializable(GET_CHATS_KEY, getChatsPojo);

            /* TEST ADAT
            AskSubjectsPojo askSubjectsPojo1 = new AskSubjectsPojo();
            ArrayList<Subject> subjects = new ArrayList<>();
            ArrayList<Instructor> instructors = new ArrayList<>();
            instructors.add(new Instructor(1L, "Pató Pál", null));

            subjects.add(new Subject(1, "Tárgy1", instructors));
            subjects.add(new Subject(2, "Tárgy2", instructors));
            subjects.add(new Subject(3, "Tárgy3", instructors));
            askSubjectsPojo1.setSubjects(subjects);

            //bundle.putSerializable("OFFICE_HOUR_SUBJECT_KEY", askSubjectsPojo1);
            */

            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout, fragment, CHAT_MAIN_MENU_FRAGMENT_KEY);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
}
