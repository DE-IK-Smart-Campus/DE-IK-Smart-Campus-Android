package hu.unideb.smartcampus.adapter.chat;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import hu.unideb.smartcampus.pojo.chat.ChatItem;

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

//        final ChatItem chatItem = chatItemList.get(position);
//        String chatName = chatItem.getChatName();
//        String lastMsg = chatItem.getLastMsg();
//
//        VCard vCard = chatItem.getvCard();
//
//        if (convertView == null) {
//            LayoutInflater layoutInflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = layoutInflater.inflate(R.layout.list_chat, null);
//        }
//        TextView chatNameTextView = (TextView) convertView
//                .findViewById(R.id.chat_name);
//        chatNameTextView.setText(chatName);
//
//        TextView lastMsgTextView = (TextView) convertView
//                .findViewById(R.id.chat_last_message);
//        lastMsgTextView.setText(lastMsg);
//
//        ImageView imageView = (ImageView) convertView
//                .findViewById(R.id.chat_image);
//
//        if (chatItem.getType() == ChatItem.Type.MUC) {
//            imageView.setImageResource(R.drawable.chat_icon);
//        }
//
//        if (chatItem.getType() == ChatItem.Type.SINGLE && vCard.getAvatar() != null) {
//            final byte[] vCardAvatar = vCard.getAvatar();
//            Bitmap bitmap = BitmapFactory.decodeByteArray(vCardAvatar, 0, vCardAvatar.length);
//            imageView.setImageBitmap(bitmap);
//        }
        return convertView;
    }
}
