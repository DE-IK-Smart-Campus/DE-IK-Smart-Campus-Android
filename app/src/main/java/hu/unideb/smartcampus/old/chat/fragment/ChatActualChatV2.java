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
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.pojo.chat.ChatMessage;
import hu.unideb.smartcampus.pojo.chat.ChatUser;
import hu.unideb.smartcampus.pojo.chat.Dialog;
import hu.unideb.smartcampus.pojo.chat.GetChatsPojo;
import hu.unideb.smartcampus.task.chat.SelectedChatForwardedMessages;

import static hu.unideb.smartcampus.task.chat.GetChatsTask.GET_CHATS_KEY;
import static hu.unideb.smartcampus.task.chat.GetSelectedChatTask.GET_SELECTED_CHAT_KEY;

/**
 * Created by Headswitcher on 2018. 02. 17..
 */

public class ChatActualChatV2 extends Fragment {

    @BindView(R.id.messagesList)
    MessagesList messagesList;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actual_chatv2, container, false);
        ButterKnife.bind(this, view);
        SelectedChatForwardedMessages selectedChatForwardedMessages = (SelectedChatForwardedMessages) getArguments().getSerializable(GET_SELECTED_CHAT_KEY);

        MessagesListAdapter<ChatMessage> adapter = new MessagesListAdapter<>(null, null);
        if (selectedChatForwardedMessages.getForwardedMessages() != null) {
            adapter.addToEnd(selectedChatForwardedMessages.getForwardedMessages(), true);
        }
        messagesList.setAdapter(adapter);

        return view;
    }
}
