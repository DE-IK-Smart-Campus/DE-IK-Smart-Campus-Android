package hu.unideb.smartcampus.main.activity.chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.chat2.IncomingChatMessageListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.forward.packet.Forwarded;
import org.jivesoftware.smackx.mam.MamManager;
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

import static android.content.ContentValues.TAG;
import static hu.unideb.smartcampus.R.id.chat_actual_conversation_list_view;
import static hu.unideb.smartcampus.R.id.chat_name;
import static hu.unideb.smartcampus.R.id.chat_send_button;
import static hu.unideb.smartcampus.R.id.chat_text_edit_text;

/**
 * Created by Headswitcher on 2017. 03. 21..
 */

public class ChatActualConversationFragment extends Fragment implements OnBackPressedListener {

    private ChatHistory chatHistory;
    private LinkedList<ChatConversationItem> chatConversationItems;
    private MamManager mamManager;
    private boolean isItAfter = false;
    private int chatHistoryitemcount;
    private EntityBareJid selectedChatPartnerJid;
    private Chat chat;
    private ListView chatlistView;
    private View actualV;
    private MamManager.MamQueryResult lastQueryResult;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        chatConversationItems = new LinkedList<>();
        mamManager = MamManager.getInstanceFor(Connection.getInstance().getXmppConnection());

        final List<Forwarded> forwardedMessages;
        try {
            lastQueryResult = mamManager.mostRecentPage(selectedChatPartnerJid, chatHistoryitemcount);
            forwardedMessages = lastQueryResult.forwardedMessages;

            for (int i = 0; i < forwardedMessages.size(); i++) {
                ChatConversationItem tmpChatConversationItem = new ChatConversationItem();
                Message tmpMsg = ((Message) forwardedMessages.get(i).getForwardedStanza());
                tmpChatConversationItem.setMsg(tmpMsg.getBody());
                tmpChatConversationItem.setFromUserJid(tmpMsg.getFrom());
                chatConversationItems.addLast(tmpChatConversationItem);
            }
            chatHistory.setChatConversationItems(chatConversationItems);
        } catch (XMPPException.XMPPErrorException e) {
            e.printStackTrace();
        } catch (SmackException.NotLoggedInException e) {
            e.printStackTrace();
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SmackException.NoResponseException e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_chat_actual_conversation, container, false);
        final ChatManager chatManager = ChatManager.getInstanceFor(Connection.getInstance().getXmppConnection());

        chatHistoryitemcount = getArguments().getInt("CHAT_HISTORY_ITEM_COUNT");
        final String toJid = getArguments().getString("SELECTED_CHAT_FROM");

        try {
            selectedChatPartnerJid = JidCreate.entityBareFrom(toJid);
            chat = chatManager.chatWith(selectedChatPartnerJid);

        /*    final List<Forwarded> forwardedMessages = mamManager.mostRecentPage(selectedChatPartnerJid, chatHistoryitemcount).forwardedMessages;

            for (int i = 0; i < forwardedMessages.size(); i++) {
                ChatConversationItem chatConversationItem = new ChatConversationItem();
                chatConversationItem.setMsg(((Message) forwardedMessages.get(i).getForwardedStanza()).getBody());
                chatConversationItem.setFromUserJid(((Message) forwardedMessages.get(i).getForwardedStanza()).getFrom());
                chatHistory.getChatConversationItems().add(chatConversationItem);
            }*/

        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
        TextView textView = (TextView) view.findViewById(chat_name);
        Button button = (Button) view.findViewById(chat_send_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText) actualV.findViewById(chat_text_edit_text);
                chatHistory.getChatConversationItems().add(new ChatConversationItem(Connection.getInstance().getActualUserJid(), editText.getText().toString()));
                ListView listView = (ListView) actualV.findViewById(chat_actual_conversation_list_view);
                listView.setAdapter(new ChatActualCoversationAdapter(chatHistory, getContext()));
                listView.setSelection(chatHistory.getChatConversationItems().size() - 1);
                try {
                    chat.send(editText.getText().toString());
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        chatManager.addIncomingListener(new IncomingChatMessageListener() {
            @Override
            public void newIncomingMessage(EntityBareJid from, Message message, Chat chat) {
                if (selectedChatPartnerJid.equals(from)) {

                    Fragment fragment = new ChatActualConversationFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("SELECTED_CHAT_FROM", getArguments().getString("SELECTED_CHAT_FROM"));
                    bundle.putInt("CHAT_HISTORY_ITEM_COUNT", getArguments().getInt("CHAT_HISTORY_ITEM_COUNT"));
                    fragment.setArguments(bundle);

                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
                    final Fragment chatactual = getFragmentManager().findFragmentByTag("CHATACTUAL");

                    fragmentTransaction.replace(R.id.frame, fragment, "CHATACTUAL");
                    fragmentTransaction.addToBackStack("CHATACTUAL");
                    fragmentTransaction.commitAllowingStateLoss();
                }
            }
        });


        textView.setText(toJid);

        ListView listView = (ListView) view.findViewById(chat_actual_conversation_list_view);

        listView.setAdapter(new ChatActualCoversationAdapter(chatHistory, getContext()));
        listView.setSelection(chatHistory.getChatConversationItems().size() - 1);
        actualV = view;

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                {
                    if (isItAfter && firstVisibleItem == 0 && (visibleItemCount != totalItemCount)) {
                        if (!(chatHistoryitemcount > chatHistory.getChatConversationItems().size())) {
                            Log.e(TAG, "onScroll:");
                            isItAfter = false;
                            chatHistoryitemcount += 20;
                            Log.e(TAG, "onScroll:" + chatHistoryitemcount);
                            MamManager mamManager = MamManager.getInstanceFor(Connection.getInstance().getXmppConnection());
                            final List<Forwarded> forwardedMessages;
                            try {
                                forwardedMessages = mamManager.mostRecentPage(selectedChatPartnerJid, chatHistoryitemcount).forwardedMessages;
                                Log.e(TAG, "onScroll: BEFORE");
                                for (int i = 0; i < forwardedMessages.size(); i++) {
                                    ChatConversationItem chatConversationItem = new ChatConversationItem();
                                    chatConversationItem.setMsg(((Message) forwardedMessages.get(i).getForwardedStanza()).getBody());
                                    chatConversationItem.setFromUserJid(((Message) forwardedMessages.get(i).getForwardedStanza()).getFrom());
                                    chatHistory.getChatConversationItems().add(chatConversationItem);

                                }

                                Fragment fragment = new ChatActualConversationFragment();
                                Bundle bundle = new Bundle();
                                bundle.putString("SELECTED_CHAT_FROM", getArguments().getString("SELECTED_CHAT_FROM"));
                                bundle.putInt("CHAT_HISTORY_ITEM_COUNT", chatHistoryitemcount);
                                fragment.setArguments(bundle);
                                Log.e(TAG, "onScroll: AFTER");
                                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
                                fragmentTransaction.replace(R.id.frame, fragment, "CHATACTUAL");
                                fragmentTransaction.addToBackStack("CHATACTUAL");
                                fragmentTransaction.commitAllowingStateLoss();
                            } catch (XMPPException.XMPPErrorException e) {
                                e.printStackTrace();
                            } catch (SmackException.NotLoggedInException e) {
                                e.printStackTrace();
                            } catch (SmackException.NotConnectedException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            } catch (SmackException.NoResponseException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            }
        });

        isItAfter = true;
        return view;

    }

    @Override
    public void onBackPressed() {

    }
}
