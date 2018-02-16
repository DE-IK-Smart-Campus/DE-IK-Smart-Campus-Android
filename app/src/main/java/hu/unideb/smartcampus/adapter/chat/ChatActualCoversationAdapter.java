package hu.unideb.smartcampus.adapter.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import org.jxmpp.jid.Jid;

import hu.unideb.smartcampus.pojo.chat.ChatHistory;


/**
 * Created by Headswitcher on 2017. 03. 21..
 */

public class ChatActualCoversationAdapter extends BaseAdapter {

    private ChatHistory chatHistory;
    private Bitmap partnerAvatarInBitmap;
    private Bitmap localUserAvatarInBitmap;
    private Context context;

    public ChatActualCoversationAdapter(ChatHistory chatHistory, Bitmap partnerAvatarInBitmap, Bitmap localUserAvatarInBitmap, Context context) {
        this.chatHistory = chatHistory;
        this.partnerAvatarInBitmap = partnerAvatarInBitmap;
        this.localUserAvatarInBitmap = localUserAvatarInBitmap;
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

        String msg = chatHistory.getChatConversationItems().get(position).getMsg();
        Jid fromUserJid = chatHistory.getChatConversationItems().get(position).getFromUserJid();

//        if (convertView == null) {
//            LayoutInflater layoutInflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = layoutInflater.inflate(R.layout.item_actual_chat, null);
//        }


//        TextView actualMsgTextView = (TextView) convertView
//                .findViewById(R.id.actual_msg);
//        actualMsgTextView.setText(msg);
//        TextView nameAndDate = (TextView) convertView.findViewById(R.id.chat_name_and_date_text);
//        String captilaziedName = org.apache.commons.lang3.StringUtils.capitalize(fromUserJid.getLocalpartOrThrow().toString());
//        nameAndDate.setText("");
//        ImageView reciverImg = (ImageView) convertView.findViewById(R.id.img_receiver);
//        reciverImg.setVisibility(View.VISIBLE);
//        LinearLayout linearLayout = (LinearLayout) convertView.findViewById(chat_actual_item_msg_layout);
//        if (fromUserJid.asBareJid().equals(Connection.getInstance().getXmppConnection().getUser().asBareJid())) {
//            nameAndDate.setVisibility(View.GONE);
//            actualMsgTextView.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
//            nameAndDate.setGravity(Gravity.END);
//            reciverImg.setVisibility(View.GONE);
//            linearLayout.setGravity(Gravity.END);
//        } else {
//            nameAndDate.setText(captilaziedName);
//            actualMsgTextView.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
//            nameAndDate.setGravity(Gravity.START);
//            linearLayout.setGravity(Gravity.START);
//            if (partnerAvatarInBitmap != null) {
//                reciverImg.setImageBitmap(partnerAvatarInBitmap);
//            }
//        }
        return convertView;
    }
}
