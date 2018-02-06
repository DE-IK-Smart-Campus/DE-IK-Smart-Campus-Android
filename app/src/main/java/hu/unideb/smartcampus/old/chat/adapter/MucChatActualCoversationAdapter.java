package hu.unideb.smartcampus.old.chat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.jxmpp.jid.Jid;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.old.chat.pojo.MucChatHistory;

/**
 * Created by Headswitcher on 2017. 03. 21..
 */

public class MucChatActualCoversationAdapter extends BaseAdapter {

    private Jid currentUserJid;
    private MucChatHistory chatHistory;
    private Context context;

    public MucChatActualCoversationAdapter(Jid currentUserJid, MucChatHistory chatHistory, Context context) {
        this.currentUserJid = currentUserJid;
        this.chatHistory = chatHistory;
        this.context = context;
    }

    @Override
    public int getCount() {
        return chatHistory.getChatConversationItems().size();
    }

    @Override
    public Object getItem(int position) {
        return chatHistory.getChatConversationItems().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        MucChatConversationItem mucChatConversationItem = chatHistory.getChatConversationItems().get(position);
//        String msg = mucChatConversationItem.getMsg();
//        String resourceName = mucChatConversationItem.getResourceName();
//
//        if (convertView == null) {
//            LayoutInflater layoutInflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = layoutInflater.inflate(R.layout.item_actual_chat, null);
//        }
//
//
//        TextView actualMsgTextView = (TextView) convertView
//                .findViewById(R.id.actual_msg);
//        actualMsgTextView.setText(msg);
//
//        TextView nameAndDate = (TextView) convertView.findViewById(R.id.chat_name_and_date_text);
//        String resourceNameWithCaptialStart = StringUtils.capitalize(resourceName);
//
//        ImageView reciverImg = (ImageView) convertView.findViewById(R.id.img_receiver);
//        reciverImg.setVisibility(View.VISIBLE);
//        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(chat_actual_item_msg_layout);
//        if (StringUtils.equals(resourceName, currentUserJid.getLocalpartOrThrow().toString())) {
//            nameAndDate.setVisibility(View.GONE);
//            actualMsgTextView.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
//            nameAndDate.setGravity(Gravity.END);
//            reciverImg.setVisibility(View.GONE);
//            linearLayout.setGravity(Gravity.END);
//        } else {
//            nameAndDate.setText(resourceNameWithCaptialStart);
//            actualMsgTextView.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
//            nameAndDate.setGravity(Gravity.START);
//            linearLayout.setGravity(Gravity.START);
//            Bitmap partnerAvatarInBitmap = chatHistory.getResourceAvatarMap().get(resourceName);
//            if (partnerAvatarInBitmap != null) {
//                reciverImg.setImageBitmap(partnerAvatarInBitmap);
//            }
//        }
        return convertView;
    }
}
