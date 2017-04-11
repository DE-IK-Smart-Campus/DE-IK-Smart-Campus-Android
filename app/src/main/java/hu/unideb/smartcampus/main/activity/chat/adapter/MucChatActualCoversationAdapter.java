package hu.unideb.smartcampus.main.activity.chat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.StringUtils;
import org.jxmpp.jid.BareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;
import org.jxmpp.util.XmppStringUtils;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.main.activity.chat.pojo.MucChatConversationItem;
import hu.unideb.smartcampus.main.activity.chat.pojo.MucChatHistory;

import static hu.unideb.smartcampus.xmpp.Connection.HOSTNAME;

/**
 * Created by Headswitcher on 2017. 03. 21..
 */

public class MucChatActualCoversationAdapter extends BaseAdapter {

    private Jid mucRoomJid;
    private Jid currentUserJid;
    private MucChatHistory chatHistory;
    private Context context;

    public MucChatActualCoversationAdapter(Jid mucRoomJid, Jid currentUserJid, MucChatHistory chatHistory, Context context) {
        this.mucRoomJid = mucRoomJid;
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

        final MucChatConversationItem mucChatConversationItem = chatHistory.getChatConversationItems().get(position);
        String msg = mucChatConversationItem.getMsg();
        String resourceName = mucChatConversationItem.getResourceName();

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.item_actual_chat, null);
        }


        TextView actualMsgTextView = (TextView) convertView
                .findViewById(R.id.actual_msg);
        actualMsgTextView.setText(msg);

        TextView nameAndDate = (TextView) convertView.findViewById(R.id.chat_name_and_date_text);
        nameAndDate.setText(resourceName);

        ImageView reciverImg = (ImageView) convertView.findViewById(R.id.img_receiver);
        ImageView senderImg = (ImageView) convertView.findViewById(R.id.img_sender);
        reciverImg.setVisibility(View.VISIBLE);
        senderImg.setVisibility(View.VISIBLE);
        try {
            final String toJid = XmppStringUtils.completeJidFrom(mucRoomJid.getLocalpartOrNull(), HOSTNAME, resourceName);
            final BareJid partnerBareJid = JidCreate.bareFrom(toJid);

            if (StringUtils.equals(resourceName, currentUserJid.getLocalpartOrThrow().toString())) {
                actualMsgTextView.setGravity(Gravity.END | Gravity.CENTER_VERTICAL);
                nameAndDate.setGravity(Gravity.END);
                reciverImg.setVisibility(View.GONE);

                final Bitmap localUserAvatarInBitmap = chatHistory.getResourceAvatarMap().get(currentUserJid.getLocalpartOrNull().toString());
                if (localUserAvatarInBitmap != null) {
                    senderImg.setImageBitmap(localUserAvatarInBitmap);
                }
            } else {
                actualMsgTextView.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
                nameAndDate.setGravity(Gravity.START);
                senderImg.setVisibility(View.GONE);

                final Bitmap partnerAvatarInBitmap = chatHistory.getResourceAvatarMap().get(resourceName);
                if (partnerAvatarInBitmap != null) {
                    reciverImg.setImageBitmap(partnerAvatarInBitmap);
                }
            }
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
        return convertView;
    }
}
