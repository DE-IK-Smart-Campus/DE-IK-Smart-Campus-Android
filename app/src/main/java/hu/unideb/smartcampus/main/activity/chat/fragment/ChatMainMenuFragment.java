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

import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.LoadingDialogFragment;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.chat.adapter.ChatMemberAdapter;
import hu.unideb.smartcampus.main.activity.chat.handler.ChatHandler;
import hu.unideb.smartcampus.main.activity.chat.pojo.ChatItem;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.main.activity.officehours.handler.OfficeHourHandler.DIALOG_TAG;

/**
 * // TODO DIALOG / BACKTRACE
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

        final List<ChatItem> chatItemList = ChatHandler.getInstance().getChatItemList();
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
                    fragmentTransaction.commit();
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
                    fragmentTransaction.commit();
                }
            }
        });
        LoadingDialogFragment fragmentByTag = (LoadingDialogFragment) getFragmentManager().findFragmentByTag(DIALOG_TAG);
        fragmentByTag.nDialog.dismiss();
        return view;
    }

    @Override
    public void onBackPressed() {
        Connection.getInstance().createLoadingDialogFragment(getFragmentManager(), new Bundle());
        new Thread(new Runnable() {
            public void run() {
                ChatHandler chatHandler = ChatHandler.getInstance();
                chatHandler.getAllChat(getFragmentManager());
            }
        }).start();
    }
}
