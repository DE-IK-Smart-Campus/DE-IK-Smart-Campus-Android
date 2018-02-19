package hu.unideb.smartcampus.old.chat.fragment;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.adapter.chat.ChatMemberAdapter;
import hu.unideb.smartcampus.pojo.chat.ChatItem;
import hu.unideb.smartcampus.pojo.chat.GetChatsPojo;

import static hu.unideb.smartcampus.task.chat.GetChatsTask.GET_CHATS_KEY;


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


    @BindView(R.id.chat_menu_history_listview)
    ListView chatHistoryListView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);

        ButterKnife.bind(this, view);
        ListView chatRoomsList = view.findViewById(R.id.chat_menu_history_listview);
        LinearLayout backgroundLayout = view.findViewById(R.id.chat_menu_background_layoutId);

        GetChatsPojo getChatsPojo = (GetChatsPojo) getArguments().getSerializable(GET_CHATS_KEY);

        final List<ChatItem> chatItemList;
        if (getChatsPojo != null) {
            //chatItemList = getChatsPojo.getChatItemList();
            chatItemList = null;

            final ListAdapter listAdapter = new ChatMemberAdapter(chatItemList, getActivity().getApplicationContext());

            //Background "changes" if chat is not empty
            if (chatItemList.isEmpty()) {
                chatRoomsList.setVisibility(View.GONE);
                backgroundLayout.setVisibility(View.VISIBLE);
            } else {
                chatRoomsList.setVisibility(View.VISIBLE);
                backgroundLayout.setVisibility(View.GONE);
            }
            chatHistoryListView.setAdapter(listAdapter);
        } else {
            chatRoomsList.setVisibility(View.VISIBLE);
            backgroundLayout.setVisibility(View.GONE);
        }
        return view;
    }

    @OnClick(R.id.chat_new_conversation)
    public void newChat() {
        Fragment fragment = new ChatNewConversation();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.drawer_layout, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    @OnItemSelected(R.id.chat_menu_history_listview)
    void onSelectedChat(int position) {
        ChatItem selectedChat = (ChatItem) chatHistoryListView.getAdapter().getItem(position);
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

            Fragment mucFragment = new ChatActualConversationMUCFragment();
            Bundle bundle = new Bundle();
            bundle.putInt(CHAT_HISTORY_ITEM_COUNT, DEFAULT_CHAT_HISTORY_ITEM_COUNT);
            bundle.putString(SELECTED_CHAT_FROM, selectedChat.getFrom().toString());
            bundle.putString(SELECTED_CHAT_TYPE, selectedChat.getType().name());
            mucFragment.setArguments(bundle);

            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout, mucFragment, CHAT_ACTUAL_KEY);
            fragmentTransaction.addToBackStack(CHAT_ACTUAL_KEY);
            fragmentTransaction.commit();
        }
    }

}
