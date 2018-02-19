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

import static hu.unideb.smartcampus.task.chat.GetChatsTask.GET_CHATS_KEY;

/**
 * Created by Headswitcher on 2018. 02. 17..
 */

public class ChatActualChatV2 extends Fragment {

    @BindView(R.id.messagesList)
    MessagesList messagesList;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_actual_chatv2, container, false);
        ButterKnife.bind(this, view);

        ChatUser chatUser = new ChatUser("1", "ÉnTeszt", "Avatar", true);

        ChatUser chatUser2 = new ChatUser("2", "NemÉnTeszt", "Avatar", true);

        ArrayList<ChatUser> chatUsers = new ArrayList<>();

        chatUsers.add(chatUser);
        chatUsers.add(chatUser2);


        ChatMessage chatMessage = new ChatMessage("1", chatUser, "SZIA");
        ChatMessage chatMessage0 = new ChatMessage("2", chatUser2, "sup");
        ChatMessage chatMessage1 = new ChatMessage("1", chatUser, "nothin");

        MessagesListAdapter<ChatMessage> adapter = new MessagesListAdapter<>("1", null);
        ArrayList<ChatMessage> chatDialog = new ArrayList<>();

        chatDialog.add(chatMessage);
        chatDialog.add(chatMessage0);
        chatDialog.add(chatMessage1);
        adapter.addToEnd(chatDialog, true);
        messagesList.setAdapter(adapter);

        /*
        DialogsListAdapter dialogsListAdapter = new DialogsListAdapter<>(new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
            }
        });
        GetChatsPojo getChatsPojo = (GetChatsPojo) getArguments().getSerializable(GET_CHATS_KEY);
        dialogsListAdapter.addItems(getChatsPojo.getChatItemList());
        dialogsList.setAdapter(dialogsListAdapter);
*/

        return view;
    }
}
