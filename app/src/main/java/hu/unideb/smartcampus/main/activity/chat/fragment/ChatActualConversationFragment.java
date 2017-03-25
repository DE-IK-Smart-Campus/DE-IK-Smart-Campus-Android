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
    private boolean befejezte = false;
    private int chatHistoryItemCount;
    private EntityBareJid selectedChatPartnerJid;
    private Chat chat;
    private View actualV;
    private MamManager.MamQueryResult lastQueryResult;
    private ChatManager chatManager;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chatManager = ChatManager.getInstanceFor(Connection.getInstance().getXmppConnection());
        chatConversationItems = new LinkedList<>();
        mamManager = MamManager.getInstanceFor(Connection.getInstance().getXmppConnection());
        String toJid = getArguments().getString("SELECTED_CHAT_FROM");
        chatHistoryItemCount = getArguments().getInt("CHAT_HISTORY_ITEM_COUNT");

        try {
            selectedChatPartnerJid = JidCreate.entityBareFrom(toJid);
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }

        try {
            lastQueryResult = mamManager.mostRecentPage(selectedChatPartnerJid, chatHistoryItemCount);
            List<Forwarded> forwardedMessages = lastQueryResult.forwardedMessages;

            for (int i = 0; i < forwardedMessages.size(); i++) {
                ChatConversationItem tmpChatConversationItem = new ChatConversationItem();
                Message tmpMsg = ((Message) forwardedMessages.get(i).getForwardedStanza());
                tmpChatConversationItem.setMsg(tmpMsg.getBody());
                tmpChatConversationItem.setFromUserJid(tmpMsg.getFrom());
                chatConversationItems.addLast(tmpChatConversationItem);
            }
            chatHistory = new ChatHistory();
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_actual_conversation, container, false);
        Log.e(TAG, "onCreateView: ONCREATE!");

        chatHistoryItemCount = getArguments().getInt("CHAT_HISTORY_ITEM_COUNT");

        chat = chatManager.chatWith(selectedChatPartnerJid);

        /*    final List<Forwarded> forwardedMessages = mamManager.mostRecentPage(selectedChatPartnerJid, chatHistoryItemCount).forwardedMessages;

            for (int i = 0; i < forwardedMessages.size(); i++) {
                ChatConversationItem chatConversationItem = new ChatConversationItem();
                chatConversationItem.setMsg(((Message) forwardedMessages.get(i).getForwardedStanza()).getBody());
                chatConversationItem.setFromUserJid(((Message) forwardedMessages.get(i).getForwardedStanza()).getFrom());
                chatHistory.getChatConversationItems().add(chatConversationItem);
            }*/

        TextView textView = (TextView) view.findViewById(chat_name);
        textView.setText(selectedChatPartnerJid);

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
                    editText.setText("");
                    InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(actualV.getWindowToken(), 0);
                } catch (SmackException.NotConnectedException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        ListView listView = (ListView) view.findViewById(chat_actual_conversation_list_view);
        listView.setAdapter(new ChatActualCoversationAdapter(chatHistory, getContext()));
        //TODO

        Log.e(TAG, "onCreateView: " + chatHistory.getChatConversationItems().get(chatHistory.getChatConversationItems().size() - 1).getMsg());

        listView.setSelection(chatHistory.getChatConversationItems().
                size() - 1);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount,
                                 int totalItemCount) {
                Log.e(TAG, "onScroll: ");
                Log.e(TAG, "onScroll:" + befejezte);
                final boolean vanelatohatarkivuli = visibleItemCount != totalItemCount;
                final boolean latszikeazelso = firstVisibleItem == 0;
                // befejezte e a view töltését , látszik e az első item , van e látóhatáron kívüli (alul)
                if (befejezte && latszikeazelso && vanelatohatarkivuli) {
                    //Ne töltsön be akkor ha Chathistoryba nem tudod Chat history item countnyit tölteni
                    if (!(chatHistoryItemCount > chatHistory.getChatConversationItems().size())) {
                        Log.e(TAG, "onScroll:");
                        chatHistoryItemCount += 20;
                        Log.e(TAG, "onScroll:" + chatHistoryItemCount);
                        List<Forwarded> forwardedMessages;
                        try {
                            forwardedMessages = mamManager.mostRecentPage(selectedChatPartnerJid, chatHistoryItemCount).forwardedMessages;
                            Log.e(TAG, "onScroll: BEFORE");

                            chatConversationItems = new LinkedList<ChatConversationItem>();
                            for (int i = 0; i < forwardedMessages.size(); i++) {
                                ChatConversationItem chatConversationItem = new ChatConversationItem();
                                final Message tmpMsg = (Message) forwardedMessages.get(i).getForwardedStanza();
                                chatConversationItem.setMsg(tmpMsg.getBody());
                                chatConversationItem.setFromUserJid(tmpMsg.getFrom());
                                chatConversationItems.addLast(chatConversationItem);
                            }
                            chatHistory.setChatConversationItems(chatConversationItems);
                            //ListView listView = (ListView) actualV.findViewById(chat_actual_conversation_list_view);
                            //listView.setAdapter(new ChatActualCoversationAdapter(chatHistory, getContext()));
                            //listView.setSelection(chatHistory.getChatConversationItems().size() - 19);

                            view.setAdapter(new ChatActualCoversationAdapter(chatHistory, getContext()));
                            view.setSelection(19);

                            Log.d(TAG, "onScroll() called with: view = [" + view + "], firstVisibleItem = [" + firstVisibleItem + "], visibleItemCount = [" + visibleItemCount + "], totalItemCount = [" + totalItemCount + "]");
                            view.smoothScrollBy(0, 0);
                            // view.setSelection();

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
                    //TODO
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_NONE);
                    Fragment chatactual = getFragmentManager().findFragmentByTag("CHATACTUAL");
                    Log.e(TAG, "newIncomingMessage:" + chatactual);
                    fragmentTransaction.detach(chatactual);
                    fragmentTransaction.attach(chatactual);
                    //      fragmentTransaction.addToBackStack("CHATACTUAL");
                    befejezte = false;

                    fragmentTransaction.commit();

                    chatManager.removeListener(this);
                    Log.e(TAG, "newIncomingMessage: levettem");
                }
            }
        });

        actualV = view;
        befejezte = true;
        return view;

    }

    @Override
    public void onBackPressed() {

    }
}
