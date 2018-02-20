package hu.unideb.smartcampus.task.chat;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.mam.MamManager;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;
import org.jxmpp.util.XmppStringUtils;

import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.converter.chat.ChatConverter;
import hu.unideb.smartcampus.dialog.loading.LoadingDialog;
import hu.unideb.smartcampus.old.chat.fragment.ChatActualChatV2;
import hu.unideb.smartcampus.old.chat.fragment.ChatMainMenuFragment;
import hu.unideb.smartcampus.old.chat.fragment.Chatv2MainMenu;
import hu.unideb.smartcampus.pojo.chat.ChatItem;
import hu.unideb.smartcampus.pojo.chat.ChatMessage;
import hu.unideb.smartcampus.pojo.chat.ChatUser;
import hu.unideb.smartcampus.pojo.chat.Dialog;
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
        List<Dialog> chatItemList = new ArrayList<>();
        Message message = null;

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

            ArrayList<ChatUser> chatUsers = new ArrayList<>();


            final StanzaCollector stanzaCollectorAndSend = connection.getXmppConnection().createStanzaCollectorAndSend(iqRequest);
            final ListUserChatsIqRequest listUserChatsIqRequest = stanzaCollectorAndSend.nextResultOrThrow(SmackConfiguration.getDefaultReplyTimeout());
            final ListUserChatsIqRequestPojo listUserChatsIqRequestPojo = ChatConverter.convertToListUserChatsIqRequestPojo(listUserChatsIqRequest);
// TODO NAMING !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
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
       /*for (int i = 0; i < twoUserChatJids.size(); i++) {
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
        }
        */

        XMPPConnection xmppConnection = Connection.getInstance().getXmppConnection();


        for (int i = 0; i < multiUserChat.size(); i++) {

            String chatName;
            chatName = XmppStringUtils.parseBareJid(multiUserChat.get(i).getLocalpartOrNull().toString());
            ChatUser chatUser = new ChatUser(multiUserChat.get(i).asEntityBareJidOrThrow().toString()
                    , multiUserChat.get(i).asEntityBareJidOrThrow().toString()
                    , "TODO"
                    , false);

            ChatUser actualUser = new ChatUser(connection.getUserJID()
                    , connection.getUserJID()
                    , "TODO"
                    , false);
           /* MamManager mamManagerForMultiChat = MamManager.getInstanceFor(xmppConnection, multiUserChat.get(i));
            MamManager.MamQueryResult queryResult = mamManagerForMultiChat.queryArchive(1);

            if (queryResult.forwardedMessages.size() > 0) {
                message = (Message) queryResult.forwardedMessages.get(0).getForwardedStanza();
                ArrayList<ChatUser> chatUsers = new ArrayList<>();
                chatUsers.add(new ChatUser(Integer.toString(i), connection.getXmppConnection().getUser().toString(), "VCARDTOAVATARTODO", true));
                chatUsers.add(new ChatUser(Integer.toString(i), "TODO2NDUSER", "VCARDTOAVATARTODO", true));
                Dialog dialog = new Dialog(Integer.toString(i), chatName, "VCARDTOAVATARTODO", chatUsers, new ChatMessage("TODOID", new ChatUser(Integer.toString(i), message.getFrom().toString(), "VCARDTOAVATARTODO", true), message.getBody().toString()), 0);
                chatItemList.add(dialog);
            } else {
            */
            ArrayList<ChatUser> chatUsers = new ArrayList<>();

          /*  ChatUser mockUser1 = new ChatUser("123", "MockUser1", "VCARDTOAVATARTODO", true);
            ChatUser mockUser2 = new ChatUser("124", "MockUser2", "VCARDTOAVATARTODO", true);

            chatUsers.add(mockUser1);
            chatUsers.add(mockUser2);
           */
            chatUsers.add(chatUser);
            chatUsers.add(actualUser);

            Dialog dialog = new Dialog(Integer.toString(i), chatName, "VCARDTOAVATARTODO", chatUsers, new ChatMessage("TESZT", chatUser, "Test message"), i);

            chatItemList.add(dialog);
        }
        //  }
        return new GetChatsPojo(chatItemList);

    }


    @Override
    protected void onPostExecute(GetChatsPojo getChatsPojo) {
        super.onPostExecute(getChatsPojo);
        loadingDialog.dismiss();

        if (getChatsPojo != null) {

            Chatv2MainMenu fragment = new Chatv2MainMenu();
            Bundle bundle = new Bundle();

            bundle.putSerializable(GET_CHATS_KEY, getChatsPojo);

            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout, fragment, CHAT_MAIN_MENU_FRAGMENT_KEY);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }
}
