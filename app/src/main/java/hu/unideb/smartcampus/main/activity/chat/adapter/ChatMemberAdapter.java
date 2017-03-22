package hu.unideb.smartcampus.main.activity.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.main.activity.chat.pojo.ChatItem;

/**
 * Created by Headswitcher on 2017. 03. 21..
 */

public class ChatMemberAdapter extends BaseAdapter {

    List<ChatItem> chatItemList;
    Context context;

    public ChatMemberAdapter(List<ChatItem> chatItemList, Context context) {
        this.chatItemList = chatItemList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return chatItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return chatItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String chatName = chatItemList.get(position).getChatName();
        String lastMsg = chatItemList.get(position).getLastMsg();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_chat, null);
        }
        TextView chatNameTextView = (TextView) convertView
                .findViewById(R.id.chat_name);
        chatNameTextView.setText(chatName);

        TextView lastMsgTextView = (TextView) convertView
                .findViewById(R.id.chat_last_message);
        lastMsgTextView.setText(lastMsg);

        return convertView;
    }
}
