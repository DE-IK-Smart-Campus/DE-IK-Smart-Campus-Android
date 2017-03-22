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

import java.util.ArrayList;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.main.activity.chat.adapter.ChatMemberAdapter;
import hu.unideb.smartcampus.main.activity.chat.pojo.ChatItem;

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

        List<ChatItem> chatItemList = new ArrayList<>();

        // MOCK
        chatItemList.add(new ChatItem("Filtikai", "Működik már a login?"));
        chatItemList.add(new ChatItem("Gabai", "te angulárhacker"));
        chatItemList.add(new ChatItem("Filtikai", "Működik már a login?"));
        chatItemList.add(new ChatItem("Gabai", "te angulárhacker"));
        chatItemList.add(new ChatItem("Filtikai", "Működik már a login?"));
        chatItemList.add(new ChatItem("Gabai", "te angulárhacker"));
        chatItemList.add(new ChatItem("Filtikai", "Működik már a login?"));
        chatItemList.add(new ChatItem("Gabai", "te angulárhacker"));
        chatItemList.add(new ChatItem("Filtikai", "Működik már a login?"));
        chatItemList.add(new ChatItem("Gabai", "te angulárhacker"));
        chatItemList.add(new ChatItem("Filtikai", "Működik már a login?"));
        chatItemList.add(new ChatItem("Gabai", "te angulárhacker"));
        chatItemList.add(new ChatItem("Filtikai", "Működik már a login?"));
        chatItemList.add(new ChatItem("Gabai", "te angulárhacker"));

        ListAdapter listAdapter = new ChatMemberAdapter(chatItemList, getContext());
        chatRoomsList.setAdapter(listAdapter);

        chatRoomsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = new ChatActualConversationFragment();
                //fragment.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, "chat_actual");
                fragmentTransaction.commitAllowingStateLoss();
            }
        });

        return view;

    }

    @Override
    public void onBackPressed() {

    }
}
