package hu.unideb.smartcampus.old.chat.handler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.mam.MamManager;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;
import org.jxmpp.util.XmppStringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.old.chat.pojo.ChatItem;
import hu.unideb.smartcampus.old.chat.pojo.ListUserChatsIqRequestPojo;
import hu.unideb.smartcampus.old.chat.task.ListUserChatsIqRequestTask;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.old.chat.task.ListUserChatsIqRequestTask.STUDENT_PARAM_KEY;

/**
 * Created by Headswitcher on 2017. 04. 18..
 */

public class ChatHandler {

    static ChatHandler instance;
    private FragmentManager fragmentManager;
    private List<ChatItem> chatItemList;

    public static ChatHandler getInstance() {
        if (instance == null) {
            instance = new ChatHandler();
        }
        return instance;
    }

    public ChatHandler() {
        chatItemList = new ArrayList<>();
    }

//    public void getAllChat(FragmentManager fragmentManager) {
//        this.fragmentManager = fragmentManager;
//        List<ChatItem> chatItemList = new ArrayList<>();
//        Message message;
//        MamManager mamManager = MamManager.getInstanceFor(Connection.getInstance().getXmppConnection());
//        List<Jid> twoUserChatJids = new ArrayList<>();
//        List<Jid> multiUserChat = new ArrayList<>();
//
//        final Connection connection = Connection.getInstance();
//        HashMap<String, String> param = new HashMap<>();
//        param.put(STUDENT_PARAM_KEY, connection.getXmppConnection().getUser().getLocalpartOrThrow().toString());
//        try {
////            final ListUserChatsIqRequestPojo listUserChatsIqRequestPojo = connection.runAsyncTask(new ListUserChatsIqRequestTask(), param);
////
////            for (String userJidInString : listUserChatsIqRequestPojo.getChatList()) {
////                twoUserChatJids.add(JidCreate.entityBareFrom(userJidInString));
////            }
////            for (String userJidInString : listUserChatsIqRequestPojo.getMucChatList()) {
////                multiUserChat.add(JidCreate.entityBareFrom(userJidInString));
////            }
//
//
////        } catch (ExecutionException | InterruptedException | XmppStringprepException e) {
////            e.printStackTrace();
////        }
//
//        for (int i = 0; i < twoUserChatJids.size(); i++) {
//            try {
//                message = null;
//                MamManager.MamQueryResult queryResult = mamManager.mostRecentPage(twoUserChatJids.get(i), 1);
//                if (queryResult.forwardedMessages.size() > 0) {
//                    message = (Message) queryResult.forwardedMessages.get(0).getForwardedStanza();
//                }
//                VCardManager vCardManager = VCardManager.getInstanceFor(Connection.getInstance().getXmppConnection());
//                VCard vCard;
//                if (message != null) {
//                    String messageForMeaddition = message.getBody();
//                    if (StringUtils.equals(message.getFrom().asBareJid(), Connection.getInstance().getXmppConnection().getUser().asBareJid())) {
//                        messageForMeaddition = "Én" + messageForMeaddition;
//                        vCard = vCardManager.loadVCard(message.getTo().asEntityBareJidOrThrow());
//                    } else {
//                        vCard = vCardManager.loadVCard(message.getFrom().asEntityBareJidOrThrow());
//                    }
//                    chatItemList.add(new ChatItem(
//                            vCard,
//                            twoUserChatJids.get(i),
//                            XmppStringUtils.parseBareJid(twoUserChatJids.get(i).getLocalpartOrThrow().toString()),
//                            ChatItem.Type.SINGLE, messageForMeaddition));
//                } else {
//                    vCard = vCardManager.loadVCard(twoUserChatJids.get(i).asEntityBareJidOrThrow());
//                    chatItemList.add(new ChatItem(
//                            vCard,
//                            twoUserChatJids.get(i),
//                            XmppStringUtils.parseBareJid(twoUserChatJids.get(i).getLocalpartOrThrow().toString()),
//                            ChatItem.Type.SINGLE, ""));
//                }
//            } catch (XMPPException.XMPPErrorException | SmackException.NotLoggedInException | InterruptedException | SmackException.NotConnectedException | SmackException.NoResponseException e) {
//                e.printStackTrace();
//            }
//        }
//
//        for (int i = 0; i < multiUserChat.size(); i++) {
//
//                String chatName;
//            chatName = XmppStringUtils.parseBareJid(multiUserChat.get(i).getLocalpartOrNull().toString());
//            final XMPPBOSHConnection xmppConnection = Connection.getInstance().getXmppConnection();
//            try {
//                MamManager mamManagerForMultiChat = MamManager.getInstanceFor(xmppConnection, multiUserChat.get(i));
//                MamManager.MamQueryResult queryResult = mamManagerForMultiChat.queryArchive(1);
//                if (queryResult.forwardedMessages.size() > 0) {
//                    message = (Message) queryResult.forwardedMessages.get(0).getForwardedStanza();
//                    chatItemList.add(new ChatItem(
//                            new VCard(),
//                            multiUserChat.get(i),
//                            chatName,
//                            ChatItem.Type.MUC,
//                            message.getBody()));
//                } else {
//                    chatItemList.add(new ChatItem(
//                            new VCard(),
//                            multiUserChat.get(i),
//                            chatName,
//                            ChatItem.Type.MUC,
//                            ""));
//                }
//            } catch (XMPPException.XMPPErrorException | SmackException.NotLoggedInException | SmackException.NotConnectedException | SmackException.NoResponseException | InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//
//        this.chatItemList = chatItemList;
//        changeToChatMenuFragment(new Bundle());
//    }

//    public void changeToChatMenuFragment(Bundle bundle) {
//        Fragment fragment = new ChatMainMenuFragment();
//        fragment.setArguments(bundle);
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                android.R.anim.fade_out);
//        fragmentTransaction.replace(R.id.frame, fragment, OFFICE_HOURS_TAG);
//        fragmentTransaction.commitAllowingStateLoss();
//    }

    public List<ChatItem> getChatItemList() {
        return chatItemList;
    }

    public void setChatItemList(List<ChatItem> chatItemList) {
        this.chatItemList = chatItemList;
    }
}
