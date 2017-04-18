package hu.unideb.smartcampus.main.activity.chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

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
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.chat.adapter.ChatMemberAdapter;
import hu.unideb.smartcampus.main.activity.chat.pojo.ChatItem;
import hu.unideb.smartcampus.main.activity.chat.pojo.ListUserChatsIqRequestPojo;
import hu.unideb.smartcampus.main.activity.chat.task.ListUserChatsIqRequestTask;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.main.activity.chat.task.ListUserChatsIqRequestTask.STUDENT_PARAM_KEY;

/** // TODO DIALOG / BACKTRACE
 * Created by Headswitcher on 2017. 02. 22..
 */

public class ChatMainMenuFragment extends Fragment implements OnBackPressedListener {

    public static final String CHAT_HISTORY_ITEM_COUNT = "CHAT_HISTORY_ITEM_COUNT";
    public static final int DEFAULT_CHAT_HISTORY_ITEM_COUNT = 20;
    public static final String SELECTED_CHAT_TYPE = "SELECTED_CHAT_TYPE";
    public static final String SELECTED_CHAT_FROM = "SELECTED_CHAT_FROM";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ListView chatRoomsList = (ListView) view.findViewById(R.id.chat_menu_history_listview);
        LinearLayout backgroundLayout = (LinearLayout) view.findViewById(R.id.chat_menu_background_layoutId);

        Button newChatButton = (Button) view.findViewById(R.id.chat_new_conversation);
        newChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new ChatNewConversation();
                Bundle bundle = new Bundle();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
            }
        });

        final List<ChatItem> chatItemList = getAllChat();
        final ListAdapter listAdapter = new ChatMemberAdapter(chatItemList, getContext());
        if (chatItemList.isEmpty()) {
            chatRoomsList.setVisibility(View.GONE);
            backgroundLayout.setVisibility(View.VISIBLE);
        } else {
            chatRoomsList.setVisibility(View.VISIBLE);
            backgroundLayout.setVisibility(View.GONE);
        }
        chatRoomsList.setAdapter(listAdapter);
        chatRoomsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ChatItem selectedChat = (ChatItem) parent.getAdapter().getItem(position);
                if (ChatItem.Type.SINGLE == selectedChat.getType()) {
                    Fragment singleFragment = new ChatActualConversationFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(CHAT_HISTORY_ITEM_COUNT, DEFAULT_CHAT_HISTORY_ITEM_COUNT);
                    bundle.putString(SELECTED_CHAT_FROM, selectedChat.getFrom().toString());
                    bundle.putString(SELECTED_CHAT_TYPE, selectedChat.getType().name());
                    singleFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, singleFragment, "CHATACTUAL");
                    fragmentTransaction.addToBackStack("CHATACTUAL");
                    fragmentTransaction.commitAllowingStateLoss();
                }
                if (ChatItem.Type.MUC == selectedChat.getType()) {
                    Fragment mucFragment = new ChatActualConversationMUCFragment();
                    Bundle bundle = new Bundle();
                    bundle.putInt(CHAT_HISTORY_ITEM_COUNT, DEFAULT_CHAT_HISTORY_ITEM_COUNT);
                    bundle.putString(SELECTED_CHAT_FROM, selectedChat.getFrom().toString());
                    bundle.putString(SELECTED_CHAT_TYPE, selectedChat.getType().name());
                    mucFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                            android.R.anim.fade_out);
                    fragmentTransaction.replace(R.id.frame, mucFragment, "CHATACTUAL");
                    fragmentTransaction.addToBackStack("CHATACTUAL");
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }
        });
        return view;
    }

    @Override
    public void onBackPressed() {

    }

    public List<ChatItem> getAllChat() {

        List<ChatItem> chatItemList = new ArrayList<>();
        Message message;
        MamManager mamManager = MamManager.getInstanceFor(Connection.getInstance().getXmppConnection());
        List<Jid> twoUserChatJids = new ArrayList<>();
        List<Jid> multiUserChat = new ArrayList<>();

        final Connection connection = Connection.getInstance();
        HashMap<String, String> param = new HashMap<>();
        param.put(STUDENT_PARAM_KEY, connection.getXmppConnection().getUser().getLocalpartOrThrow().toString());
        try {
            final ListUserChatsIqRequestPojo listUserChatsIqRequestPojo = connection.runAsyncTask(new ListUserChatsIqRequestTask(), param);

            for (String userJidInString : listUserChatsIqRequestPojo.getChatList()) {
                twoUserChatJids.add(JidCreate.entityBareFrom(userJidInString));
            }
            for (String userJidInString : listUserChatsIqRequestPojo.getMucChatList()) {
                multiUserChat.add(JidCreate.entityBareFrom(userJidInString));
            }


        } catch (ExecutionException | InterruptedException | XmppStringprepException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < multiUserChat.size(); i++) {
            message = null;
            final XMPPBOSHConnection xmppConnection = Connection.getInstance().getXmppConnection();
            try {
                MamManager mamManagerForMultiChat = MamManager.getInstanceFor(xmppConnection, multiUserChat.get(i));
                MamManager.MamQueryResult queryResult = mamManagerForMultiChat.queryArchive(1);
                if (queryResult.forwardedMessages.size() > 0) {
                    message = (Message) queryResult.forwardedMessages.get(0).getForwardedStanza();
                    chatItemList.add(new ChatItem(
                            new VCard(),
                            multiUserChat.get(i),
                            XmppStringUtils.parseBareJid(multiUserChat.get(i).getLocalpartOrNull().toString()),
                            ChatItem.Type.MUC,
                            message.getBody()));
                } else {
                    chatItemList.add(new ChatItem(
                            new VCard(),
                            multiUserChat.get(i),
                            XmppStringUtils.parseBareJid(multiUserChat.get(i).getLocalpartOrNull().toString()),
                            ChatItem.Type.MUC,
                            ""));
                }
            } catch (XMPPException.XMPPErrorException | SmackException.NotLoggedInException | SmackException.NotConnectedException | SmackException.NoResponseException | InterruptedException e) {
                e.printStackTrace();
            }
        }

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
                        messageForMeaddition = getString(R.string.chatMe) + messageForMeaddition;
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
        return chatItemList;
    }
}
