package hu.unideb.smartcampus.old.chat.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.pojo.chat.Dialog;
import hu.unideb.smartcampus.pojo.chat.ChatMessage;
import hu.unideb.smartcampus.pojo.chat.ChatUser;
import hu.unideb.smartcampus.pojo.chat.GetChatsPojo;
import hu.unideb.smartcampus.task.chat.GetSelectedChatTask;

import static hu.unideb.smartcampus.task.chat.GetChatsTask.GET_CHATS_KEY;

/**
 * Created by Headswitcher on 2018. 02. 17..
 */


public class Chatv2MainMenu extends Fragment {

    @BindView(R.id.dialogsList)
    DialogsList dialogsList;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chatv2, container, false);
        ButterKnife.bind(this, view);

        DialogsListAdapter dialogsListAdapter = new DialogsListAdapter<>(null);
        GetChatsPojo getChatsPojo = (GetChatsPojo) getArguments().getSerializable(GET_CHATS_KEY);
        dialogsListAdapter.addItems(getChatsPojo.getChatItemList());

        dialogsListAdapter.setOnDialogClickListener(dialog -> {
            Dialog dialog1 = (Dialog) dialog;
            new GetSelectedChatTask(getActivity()).execute(dialog1);
        });
        dialogsList.setAdapter(dialogsListAdapter);


        return view;
    }

}
