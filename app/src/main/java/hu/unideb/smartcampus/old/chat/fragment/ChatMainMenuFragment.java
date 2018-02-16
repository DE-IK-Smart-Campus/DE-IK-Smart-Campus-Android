package hu.unideb.smartcampus.old.chat.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.adapter.chat.ChatMemberAdapter;
import hu.unideb.smartcampus.pojo.chat.ChatItem;
import hu.unideb.smartcampus.pojo.chat.GetChatsPojo;
import hu.unideb.smartcampus.task.chat.GetChatsTask;
import hu.unideb.smartcampus.xmpp.Connection;


/**
 * // TODO DIALOG / BACKTRACE
 * Created by Headswitcher on 2017. 02. 22..
 */

public class ChatMainMenuFragment extends Fragment {

    public static final String CHAT_HISTORY_ITEM_COUNT = "CHAT_HISTORY_ITEM_COUNT";
    public static final int DEFAULT_CHAT_HISTORY_ITEM_COUNT = 20;
    public static final String SELECTED_CHAT_TYPE = "SELECTED_CHAT_TYPE";
    public static final String SELECTED_CHAT_FROM = "SELECTED_CHAT_FROM";
    public static final String CHAT_ACTUAL_KEY = "CHATACTUAL";


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
/*                Fragment fragment = new ChatNewConversation();
                Bundle bundle = new Bundle();
                fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment);
                fragmentTransaction.commitAllowingStateLoss();
                */
            }
        });

        GetChatsTask chatsTask = new GetChatsTask(getActivity());
        chatsTask.execute();

        //TODO
        GetChatsPojo getChatsPojo = null;
        try {
            getChatsPojo = chatsTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //TODO

        final List<ChatItem> chatItemList = getChatsPojo.getChatItemList();
        final ListAdapter listAdapter = new ChatMemberAdapter(chatItemList, getActivity().getApplicationContext());
        if (chatItemList.isEmpty()) {
            chatRoomsList.setVisibility(View.GONE);
            backgroundLayout.setVisibility(View.VISIBLE);
        } else {
            chatRoomsList.setVisibility(View.VISIBLE);
            backgroundLayout.setVisibility(View.GONE);
        }
        chatRoomsList.setAdapter(listAdapter);
        chatRoomsList.setOnItemClickListener((parent, view1, position, id) -> {

            ChatItem selectedChat = (ChatItem) parent.getAdapter().getItem(position);
            if (ChatItem.Type.SINGLE == selectedChat.getType()) {
                Fragment singleFragment = new ChatActualConversationFragment();
                Bundle bundle = new Bundle();
                bundle.putInt(CHAT_HISTORY_ITEM_COUNT, DEFAULT_CHAT_HISTORY_ITEM_COUNT);
                bundle.putString(SELECTED_CHAT_FROM, selectedChat.getFrom().toString());
                bundle.putString(SELECTED_CHAT_TYPE, selectedChat.getType().name());
                singleFragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();

                fragmentTransaction.replace(R.id.drawer_layout, singleFragment, CHAT_ACTUAL_KEY);
                fragmentTransaction.addToBackStack(CHAT_ACTUAL_KEY);
                fragmentTransaction.commit();
            }
            if (ChatItem.Type.MUC == selectedChat.getType()) {
               /* Fragment mucFragment = new ChatActualConversationMUCFragment();
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
                */
            }
        });
        return view;
    }
}
