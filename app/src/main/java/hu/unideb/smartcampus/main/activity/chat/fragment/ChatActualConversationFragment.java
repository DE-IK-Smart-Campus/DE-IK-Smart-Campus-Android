package hu.unideb.smartcampus.main.activity.chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;

import static hu.unideb.smartcampus.R.id.chat_actual_conversation_list_view;

/**
 * Created by Headswitcher on 2017. 03. 21..
 */

public class ChatActualConversationFragment extends Fragment implements OnBackPressedListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat_actual_conversation, container, false);

        ListView chatRoomsList = (ListView) view.findViewById(chat_actual_conversation_list_view);

        List names = Arrays.asList("Holkai", "Gabai", "Atkai", "Norkai", "Filtián", "Balai");

        //chatRoomsList.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, names));

        return view;
    }

    @Override
    public void onBackPressed() {

    }
}
