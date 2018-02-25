package hu.unideb.smartcampus.task.chat;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import com.stfalcon.chatkit.messages.MessagesListAdapter;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.forward.packet.Forwarded;
import org.jivesoftware.smackx.mam.MamManager;
import org.jivesoftware.smackx.vcardtemp.VCardManager;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.stringprep.XmppStringprepException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.dialog.loading.LoadingDialog;
import hu.unideb.smartcampus.old.chat.fragment.ChatActualChatV2;
import hu.unideb.smartcampus.pojo.chat.ChatConversationItem;
import hu.unideb.smartcampus.pojo.chat.ChatHistory;
import hu.unideb.smartcampus.pojo.chat.ChatMessage;
import hu.unideb.smartcampus.pojo.chat.ChatUser;
import hu.unideb.smartcampus.pojo.chat.Dialog;
import hu.unideb.smartcampus.pojo.chat.GetChatsPojo;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.dialog.loading.LoadingDialog.LOADING_DIALOG_TAG;

/**
 * Created by Headswitcher on 2018. 02. 20..
 */

public class GetSelectedChatTask extends AsyncTask<Dialog, Long, SelectedChatForwardedMessages> {

    public final static String GET_SELECTED_CHAT_KEY = "GET_SELECTED_CHAT_KEY";
    public final static String CHAT_MAIN_MENU_FRAGMENT_KEY = "CHAT_MAIN_MENU_FRAGMENT";

    private ChatHistory chatHistory;
    private LinkedList<ChatConversationItem> chatConversationItems;
    private MamManager mamManager;
    private boolean afterViewCreate = false;
    private int chatHistoryItemCount;
    private EntityBareJid selectedChatPartnerJid;
    private Chat chat;
    private ChatManager chatManager;
    private Bitmap userAvatarInBitmap;
    private Bitmap partnerAvatarInBitmap;

    public GetSelectedChatTask(Activity activity) {
        this.activity = activity;
    }

    private LoadingDialog loadingDialog;

    private Activity activity;

    @Override
    protected SelectedChatForwardedMessages doInBackground(Dialog... dialogs) {
        Dialog dialog = dialogs[0];
        final XMPPBOSHConnection xmppboshConnection = Connection.getInstance().getXmppConnection();

        chatManager = ChatManager.getInstanceFor(xmppboshConnection);
        mamManager = MamManager.getInstanceFor(xmppboshConnection);
        //TODO MUC
        String partnerToJid = dialog.getUsers().get(0).getName().compareToIgnoreCase(xmppboshConnection.getUser().getLocalpart().toString()) == 0 ? dialog.getUsers().get(0).getName() : dialog.getUsers().get(1).getName();

        try {
/*            VCard userVCard = VCardManager.getInstanceFor(xmppboshConnection).loadVCard(xmppboshConnection.getUser().asEntityBareJidIfPossible());
            VCard partnerVCard = VCardManager.getInstanceFor(xmppboshConnection).loadVCard(JidCreate.entityBareFrom(partnerToJid));

            if (userVCard.getAvatar() != null) {
                final byte[] userVCardAvatar = userVCard.getAvatar();
                userAvatarInBitmap = BitmapFactory.decodeByteArray(userVCardAvatar, 0, userVCardAvatar.length);
            } else {
                userAvatarInBitmap = null;
            }

            if (partnerVCard.getAvatar() != null) {
                final byte[] partnerVCardAvatar = partnerVCard.getAvatar();
                partnerAvatarInBitmap = BitmapFactory.decodeByteArray(partnerVCardAvatar, 0, partnerVCardAvatar.length);
            } else {
                partnerAvatarInBitmap = null;
            }
*/

            chatConversationItems = new LinkedList<>();
            chatHistoryItemCount = 20; //TODO
            chatHistory = new ChatHistory();

            final EntityBareJid jid = JidCreate.entityBareFrom(partnerToJid);
            selectedChatPartnerJid = jid;

/*            MamManager.MamQueryResult lastQueryResult = mamManager.mostRecentPage(selectedChatPartnerJid, chatHistoryItemCount);
            List<Forwarded> forwardedMessages = lastQueryResult.forwardedMessages;
            List<ChatMessage> chatMessages = new ArrayList<>();

            chatHistory.setChatConversationItems(chatConversationItems);

            for (Forwarded forwarded : forwardedMessages) {
                final Message tmpMsg = ((Message) forwarded.getForwardedStanza());
                String getFrom = forwarded.getForwardedStanza().getFrom().toString();
                chatMessages.add(new ChatMessage(forwarded.getForwardedStanza().getStanzaId()
                        , new ChatUser(getFrom, getFrom, "TODO", false)
                        , tmpMsg.getBody()));

            }
            */

       /*     ChatUser chatUser = new ChatUser("1", "ÉnTeszt", "Avatar", true);
            ChatUser chatUser2 = new ChatUser("2", "NemÉnTeszt", "Avatar", true);
            ArrayList<ChatUser> chatUsers = new ArrayList<>();
            chatUsers.add(chatUser);
            chatUsers.add(chatUser2);
            ChatMessage chatMessage = new ChatMessage("1", chatUser, "SZIA");
            ChatMessage chatMessage0 = new ChatMessage("2", chatUser2, "sup");
            ChatMessage chatMessage1 = new ChatMessage("1", chatUser, "nothin");
            MessagesListAdapter<ChatMessage> adapter = new MessagesListAdapter<>("1", null);
            ArrayList<ChatMessage> chatMessages = new ArrayList<>();
            chatMessages.add(chatMessage);
            chatMessages.add(chatMessage0);
            chatMessages.add(chatMessage1);
        */

            ChatUser chatUser = new ChatUser("1", "ÉnTeszt", "Avatar", true);
            ChatUser chatUser2 = new ChatUser("2", "NemÉnTeszt", "Avatar", true);
            ArrayList<ChatUser> chatUsers = new ArrayList<>();
            chatUsers.add(chatUser);
            chatUsers.add(chatUser2);
            ChatMessage chatMessage = new ChatMessage("1", chatUser, "Teszt1 User1");
            ChatMessage chatMessage0 = new ChatMessage("2", chatUser2, "Teszt2 User2");
            ChatMessage chatMessage1 = new ChatMessage("1", chatUser, "Teszt2 User1");

            ArrayList<ChatMessage> chatMessages = new ArrayList<>();
            chatMessages.add(chatMessage);
            chatMessages.add(chatMessage0);
            chatMessages.add(chatMessage1);

            return new SelectedChatForwardedMessages(chatMessages);

        /*} catch (SmackException.NotLoggedInException | SmackException.NoResponseException | XMPPException.XMPPErrorException | InterruptedException | SmackException.NotConnectedException | XmppStringprepException e) {
            e.printStackTrace();
        }
        */
        } catch (XmppStringprepException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        loadingDialog = new LoadingDialog();
        loadingDialog.show(activity.getFragmentManager(), LOADING_DIALOG_TAG);
    }

    @Override
    protected void onPostExecute(SelectedChatForwardedMessages selectedChatForwardedMessages) {
        super.onPostExecute(selectedChatForwardedMessages);
        loadingDialog.dismiss();

        if (selectedChatForwardedMessages != null) {

            ChatActualChatV2 fragment = new ChatActualChatV2();
            Bundle bundle = new Bundle();

            bundle.putSerializable(GET_SELECTED_CHAT_KEY, selectedChatForwardedMessages);

            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = activity.getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.drawer_layout, fragment, CHAT_MAIN_MENU_FRAGMENT_KEY);
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

}
