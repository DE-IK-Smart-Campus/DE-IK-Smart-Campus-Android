package hu.unideb.smartcampus.main.activity.chat.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.ExtensionElement;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.StringUtils;
import org.jivesoftware.smackx.forward.packet.Forwarded;
import org.jivesoftware.smackx.mam.MamManager;
import org.jivesoftware.smackx.muc.MucEnterConfiguration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatException;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.muclight.MultiUserChatLight;
import org.jivesoftware.smackx.muclight.MultiUserChatLightManager;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.chat.adapter.ChatActualCoversationAdapter;
import hu.unideb.smartcampus.main.activity.chat.pojo.ChatConversationItem;
import hu.unideb.smartcampus.main.activity.chat.pojo.ChatHistory;
import hu.unideb.smartcampus.xmpp.Connection;

import static android.content.ContentValues.TAG;
import static hu.unideb.smartcampus.R.id.chat_actual_conversation_list_view;
import static hu.unideb.smartcampus.R.id.chat_name;
import static hu.unideb.smartcampus.R.id.chat_send_button;
import static hu.unideb.smartcampus.R.id.chat_text_edit_text;

/**
 * Created by Erdei Krisztián on 2017.04.09..
 */

public class ChatActualConversationMUCFragment extends Fragment implements OnBackPressedListener {

    private ChatHistory chatHistory;
    private LinkedList<ChatConversationItem> chatConversationItems;
    private MamManager mamManager;
    private boolean afterViewCreate = false;
    private int chatHistoryItemCount;
    private EntityBareJid selectedChatPartnerJid;
    private MultiUserChat chat;
    private MultiUserChatManager chatManager;
    private View actualV;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final XMPPBOSHConnection xmppboshConnection = Connection.getInstance().getXmppConnection();
        chatManager = MultiUserChatManager.getInstanceFor(xmppboshConnection);
        mamManager = MamManager.getInstanceFor(xmppboshConnection);
        String toJid = getArguments().getString("SELECTED_CHAT_FROM");
        chatConversationItems = new LinkedList<>();
        chatHistoryItemCount = getArguments().getInt("CHAT_HISTORY_ITEM_COUNT");
        chatHistory = new ChatHistory();
        setChatHistory(xmppboshConnection, toJid);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_actual_conversation, container, false);
        final XMPPBOSHConnection xmppboshConnection = Connection.getInstance().getXmppConnection();
        chatHistoryItemCount = getArguments().getInt("CHAT_HISTORY_ITEM_COUNT");

        chat = chatManager.getMultiUserChat(selectedChatPartnerJid);
        try {
            MucEnterConfiguration.Builder enterConfigurationBuilder = chat.getEnterConfigurationBuilder(Resourcepart.from(xmppboshConnection.getUser().getLocalpart().toString()));
            MucEnterConfiguration.Builder builder = enterConfigurationBuilder.requestMaxStanzasHistory(500);
            MucEnterConfiguration build = builder.build();
            //-
            chat.createOrJoin(build);
            //chat.join(Resourcepart.from(xmppboshConnection.getUser().getLocalpart().toString()));
            chat.addMessageListener(new MessageListener() {
                @Override
                public void processMessage(Message message) {
                    Log.e(TAG, "processMessage: " + message.getExtensions());
                    if (!message.getExtensions().isEmpty() && message.getExtension("http://jabber.org/protocol/chatstates") != null) {
                        ExtensionElement extension = message.getExtension("http://jabber.org/protocol/chatstates");
                        Log.e(TAG, "processMessage: " + extension.getNamespace());
                        if (org.apache.commons.lang3.StringUtils.equals(extension.getElementName().toString(), "composing")) {
                            Log.e(TAG, message.getFrom().getResourceOrEmpty().toString() + "éppenír:" + extension.getElementName());
                        }
                        if (org.apache.commons.lang3.StringUtils.equals(extension.getElementName().toString(), "paused")) {
                            Log.e(TAG, message.getFrom().getResourceOrEmpty().toString() + "éppenmegált:" + extension.getElementName());
                        }
                        if (org.apache.commons.lang3.StringUtils.equals(extension.getElementName().toString(), "active")) {
                            Log.e(TAG, message.getFrom().getResourceOrEmpty().toString() + "éppen itt van:" + extension.getElementName());
                        }

                        if (org.apache.commons.lang3.StringUtils.equals(extension.getElementName().toString(), "inactive")) {
                            Log.e(TAG, message.getFrom().getResourceOrEmpty().toString() + "éppen inaktív:" + extension.getElementName());
                        }
                    }
                    if (message.getBody() != null && !message.getBody().isEmpty() && message.getExtension("urn:xmpp:delay") == null) {
                        ChatConversationItem tmpChatConversationItem = new ChatConversationItem();
                        tmpChatConversationItem.setMsg(message.getBody());
                        tmpChatConversationItem.setFromUserJid(message.getFrom());
                        LinkedList<ChatConversationItem> chatConversationItems2 = chatHistory.getChatConversationItems();
                        chatConversationItems2.addLast(tmpChatConversationItem);
                        chatHistory.setChatConversationItems(chatConversationItems2);

                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
                        Fragment chatactual = new ChatActualConversationMUCFragment();
                        chatactual.setArguments(getArguments());
                        fragmentTransaction.replace(R.id.frame, chatactual);
                        afterViewCreate = false;
                        fragmentTransaction.commit();
                    }
                }
            });
            //chat.join(Resourcepart.from(xmppboshConnection.getUser().getLocalpart().toString()));
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (MultiUserChatException.NotAMucServiceException e) {
            e.printStackTrace();
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        } catch (MultiUserChatException.MucAlreadyJoinedException e) {
            e.printStackTrace();
        }


        TextView textView = (TextView) view.findViewById(chat_name);
        textView.setText(selectedChatPartnerJid.getLocalpart().toString());

        Button sendButton = (Button) view.findViewById(chat_send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) actualV.findViewById(chat_text_edit_text);
                chatHistory.getChatConversationItems().add(new ChatConversationItem(Connection.getInstance().getActualUserJid(), editText.getText().toString()));
                ListView listView = (ListView) actualV.findViewById(chat_actual_conversation_list_view);
                listView.setAdapter(new ChatActualCoversationAdapter(chatHistory, getContext()));
                listView.setSelection(chatHistory.getChatConversationItems().size() - 1);
                try {
                    chat.sendMessage(editText.getText().toString());
                    editText.setText("");
                    //Hide Keyboard
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(actualV.getWindowToken(), 0);
                } catch (SmackException.NotConnectedException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        ListView listView = (ListView) view.findViewById(chat_actual_conversation_list_view);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {

                final boolean isThereAnyItemOutsideTheListView = visibleItemCount != totalItemCount;
                final boolean isTheFirstListItemVisible = firstVisibleItem == 0;

                if (afterViewCreate && isTheFirstListItemVisible && isThereAnyItemOutsideTheListView) {
                    //Don't load more history if theres not enough
                    if (!(chatHistoryItemCount > chatHistory.getChatConversationItems().size())) {
                        chatHistoryItemCount += 20;
                        List<Forwarded> forwardedMessages;
                        try {

                            MamManager mamManagerForMultiChat = MamManager.getInstanceFor(Connection.getInstance().getXmppConnection(), selectedChatPartnerJid);
                            MamManager.MamQueryResult lastQueryResult = mamManagerForMultiChat.mostRecentPage(null, chatHistoryItemCount);
                            forwardedMessages = lastQueryResult.forwardedMessages;
                            setChatHistory(forwardedMessages);
                            chatConversationItems = new LinkedList<>();
                            setChatHistory(forwardedMessages);
                            chatHistory.setChatConversationItems(chatConversationItems);
                            view.setAdapter(new ChatActualCoversationAdapter(chatHistory, getContext()));

                            view.setSelection(19);
                            view.smoothScrollBy(0, 0);

                        } catch (XMPPException.XMPPErrorException
                                | SmackException.NoResponseException
                                | InterruptedException
                                | SmackException.NotConnectedException
                                | SmackException.NotLoggedInException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });

//        MultiUserChatLightManager multiUserChatLightManager = MultiUserChatLightManager.getInstanceFor(xmppboshConnection);
        //      MultiUserChatLight multiUserChatLight = multiUserChatLightManager.getMultiUserChatLight(selectedChatPartnerJid);


        listView.setAdapter(new ChatActualCoversationAdapter(chatHistory, getContext()));
        listView.setSelection(chatHistory.getChatConversationItems().size() - 1);

        actualV = view;
        afterViewCreate = true;

        return view;
    }


    private void setChatHistory(List<Forwarded> forwardedMessages) {
        for (int i = 0; i < forwardedMessages.size(); i++) {
            ChatConversationItem tmpChatConversationItem = new ChatConversationItem();
            final Message tmpMsg = ((Message) forwardedMessages.get(i).getForwardedStanza());
            tmpChatConversationItem.setMsg(tmpMsg.getBody());
            tmpChatConversationItem.setFromUserJid(tmpMsg.getFrom());
            chatConversationItems.addLast(tmpChatConversationItem);
        }
        chatHistory.setChatConversationItems(chatConversationItems);
    }

    private void setChatHistory(XMPPBOSHConnection xmppboshConnection, String toJid) {
        try {
            selectedChatPartnerJid = JidCreate.entityBareFrom(toJid);
            MamManager mamManagerForMultiChat = MamManager.getInstanceFor(xmppboshConnection, selectedChatPartnerJid);
            MamManager.MamQueryResult lastQueryResult = mamManagerForMultiChat.mostRecentPage(null, chatHistoryItemCount);
            List<Forwarded> forwardedMessages = lastQueryResult.forwardedMessages;
            setChatHistory(forwardedMessages);

        } catch (XMPPException.XMPPErrorException
                | XmppStringprepException
                | SmackException.NotLoggedInException
                | InterruptedException
                | SmackException.NotConnectedException
                | SmackException.NoResponseException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onBackPressed() {
        try {
            chat.leave();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

