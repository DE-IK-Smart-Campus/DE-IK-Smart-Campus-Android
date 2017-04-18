package hu.unideb.smartcampus.main.activity.chat.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.forward.packet.Forwarded;
import org.jivesoftware.smackx.mam.MamManager;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.LinkedList;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.chat.adapter.ChatActualCoversationAdapter;
import hu.unideb.smartcampus.main.activity.chat.pojo.ChatConversationItem;
import hu.unideb.smartcampus.main.activity.chat.pojo.ChatHistory;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.R.id.chat_actual_conversation_list_view;
import static hu.unideb.smartcampus.R.id.chat_name;
import static hu.unideb.smartcampus.R.id.chat_send_button;
import static hu.unideb.smartcampus.R.id.chat_text_edit_text;
import static hu.unideb.smartcampus.main.activity.chat.fragment.ChatMainMenuFragment.CHAT_HISTORY_ITEM_COUNT;
import static hu.unideb.smartcampus.main.activity.chat.fragment.ChatMainMenuFragment.SELECTED_CHAT_FROM;

/**
 * Created by Headswitcher on 2017. 03. 21..
 */

public class ChatActualConversationFragment extends Fragment implements OnBackPressedListener {

    private ChatHistory chatHistory;
    private LinkedList<ChatConversationItem> chatConversationItems;
    private MamManager mamManager;
    private boolean afterViewCreate = false;
    private int chatHistoryItemCount;
    private EntityBareJid selectedChatPartnerJid;
    private Chat chat;
    private View actualV;
    private ChatManager chatManager;
    private Bitmap userAvatarInBitmap;
    private Bitmap partnerAvatarInBitmap;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final XMPPBOSHConnection xmppboshConnection = Connection.getInstance().getXmppConnection();
        chatManager = ChatManager.getInstanceFor(xmppboshConnection);
        mamManager = MamManager.getInstanceFor(xmppboshConnection);
        String toJid = getArguments().getString(SELECTED_CHAT_FROM);
        try {
            VCard userVCard = VCardManager.getInstanceFor(xmppboshConnection).loadVCard(xmppboshConnection.getUser().asEntityBareJidIfPossible());
            VCard partnerVCard = VCardManager.getInstanceFor(xmppboshConnection).loadVCard(JidCreate.entityBareFrom(toJid));

            if (userVCard.getAvatar() != null) {
                final byte[] userVCardAvatar = userVCard.getAvatar();
                userAvatarInBitmap = BitmapFactory.decodeByteArray(userVCardAvatar, 0, userVCardAvatar.length);
            } else {
                userAvatarInBitmap = null;
            }

            if (partnerVCard.getAvatar() != null) {
                final byte[] partnerVCardAvatar = partnerVCard.getAvatar();
                partnerAvatarInBitmap = BitmapFactory.decodeByteArray(partnerVCardAvatar, 0, partnerVCardAvatar.length);
            } else {
                partnerAvatarInBitmap = null;
            }


        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | InterruptedException | SmackException.NotConnectedException | XmppStringprepException e) {
            e.printStackTrace();
        }
        chatConversationItems = new LinkedList<>();
        chatHistoryItemCount = getArguments().getInt(CHAT_HISTORY_ITEM_COUNT);
        chatHistory = new ChatHistory();
        setChatHistory(xmppboshConnection, toJid);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_actual_conversation, container, false);
        chatHistoryItemCount = getArguments().getInt(CHAT_HISTORY_ITEM_COUNT);

        chat = chatManager.chatWith(selectedChatPartnerJid);


        TextView textView = (TextView) view.findViewById(chat_name);
        textView.setText(StringUtils.capitalize(selectedChatPartnerJid.getLocalpart().toString()));

        Button sendButton = (Button) view.findViewById(chat_send_button);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) actualV.findViewById(chat_text_edit_text);
                chatHistory.getChatConversationItems().add(new ChatConversationItem(Connection.getInstance().getXmppConnection().getUser(), editText.getText().toString()));
                ListView listView = (ListView) actualV.findViewById(chat_actual_conversation_list_view);
                listView.setAdapter(new ChatActualCoversationAdapter(chatHistory, partnerAvatarInBitmap, userAvatarInBitmap, getContext()));
                listView.setSelection(chatHistory.getChatConversationItems().size() - 1);
                try {
                    chat.send(editText.getText().toString());
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
                            //TODO
                            forwardedMessages = mamManager.mostRecentPage(selectedChatPartnerJid, chatHistoryItemCount).forwardedMessages;
                            chatConversationItems = new LinkedList<>();
                            setChatHistory(forwardedMessages);
                            chatHistory.setChatConversationItems(chatConversationItems);
                            view.setAdapter(new ChatActualCoversationAdapter(chatHistory, partnerAvatarInBitmap, userAvatarInBitmap, getContext()));
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
        chatManager.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                if (selectedChatPartnerJid.equals(from)) {
                    try {
                        MamManager.MamQueryResult addJustOne = mamManager.mostRecentPage(selectedChatPartnerJid, 1);

                        Message tmpMsg = ((Message) addJustOne.forwardedMessages.get(0).getForwardedStanza());
                        ChatConversationItem tmpChatConversationItem = new ChatConversationItem();
                        tmpChatConversationItem.setMsg(tmpMsg.getBody());
                        tmpChatConversationItem.setFromUserJid(tmpMsg.getFrom());
                        LinkedList<ChatConversationItem> chatConversationItems2 = chatHistory.getChatConversationItems();
                        chatConversationItems2.addLast(tmpChatConversationItem);
                        chatHistory.setChatConversationItems(chatConversationItems2);
                    } catch (XMPPException.XMPPErrorException
                            | SmackException.NotLoggedInException
                            | SmackException.NotConnectedException
                            | InterruptedException
                            | SmackException.NoResponseException e) {
                        e.printStackTrace();
                    }
                    //TODO
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
                    Fragment chatactual = new ChatActualConversationFragment();
                    chatactual.setArguments(getArguments());
                    fragmentTransaction.replace(R.id.frame, chatactual);
                    afterViewCreate = false;
                    fragmentTransaction.commit();
                    chatManager.removeListener(this);
                }
            }
        });
        listView.setAdapter(new ChatActualCoversationAdapter(chatHistory, partnerAvatarInBitmap, userAvatarInBitmap, getContext()));
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

            final EntityBareJid jid = JidCreate.entityBareFrom(toJid);
            selectedChatPartnerJid = jid;

            MamManager.MamQueryResult lastQueryResult = mamManager.mostRecentPage(selectedChatPartnerJid, chatHistoryItemCount);
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
        Fragment fragment = new ChatMainMenuFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }
}
