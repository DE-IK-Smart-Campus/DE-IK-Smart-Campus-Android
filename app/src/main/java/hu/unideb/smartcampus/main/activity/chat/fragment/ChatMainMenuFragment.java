package hu.unideb.smartcampus.main.activity.chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.mam.MamManager;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;
import org.jxmpp.util.XmppStringUtils;

import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.chat.adapter.ChatMemberAdapter;
import hu.unideb.smartcampus.main.activity.chat.pojo.ChatItem;
import hu.unideb.smartcampus.xmpp.Connection;

/**
 * Created by Headswitcher on 2017. 02. 22..
 */

public class ChatMainMenuFragment extends Fragment implements OnBackPressedListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        ListView chatRoomsList = (ListView) view.findViewById(R.id.chat_menu_history_listview);

        final ListAdapter listAdapter = new ChatMemberAdapter(getAllChat(), getContext());
        chatRoomsList.setAdapter(listAdapter);

        chatRoomsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ChatItem selectedChat = (ChatItem) parent.getAdapter().getItem(position);

                Fragment fragment = new ChatActualConversationFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("CHAT_HISTORY_ITEM_COUNT", 20);
                bundle.putString("SELECTED_CHAT_FROM", selectedChat.getFrom().toString());
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, "CHATACTUAL");
                fragmentTransaction.addToBackStack("CHATACTUAL");
                fragmentTransaction.commitAllowingStateLoss();
            }
        });

        return view;

    }

    @Override
    public void onBackPressed() {

    }

    public List<ChatItem> getAllChat() {
        //Get me List<Jid>
        //Mocked for now
        MamManager mamManager = MamManager.getInstanceFor(Connection.getInstance().getXmppConnection());
        List<Jid> jids = new ArrayList<>();
        try {
            jids.add(JidCreate.bareFrom("kfnorbi@wt2.inf.unideb.hu/Smartcampus"));
            jids.add(JidCreate.bareFrom("smartcampus@wt2.inf.unideb.hu/Smartcampus"));
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
        //Mocked for now
        //Get all chat jid from Backend
        Message message;

        List<ChatItem> chatItemList = new ArrayList<>();
        for (int i = 0; i < jids.size(); i++) {
            try {
                message = (Message) mamManager.mostRecentPage(jids.get(i), 1).forwardedMessages.get(0).getForwardedStanza();
                chatItemList.add(new ChatItem(jids.get(i), XmppStringUtils.parseLocalpart(jids.get(i).getLocalpartOrNull().toString()), message.getBody().toString()));
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
        return chatItemList;
    }
}