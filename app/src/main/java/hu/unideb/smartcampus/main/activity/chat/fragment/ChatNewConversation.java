package hu.unideb.smartcampus.main.activity.chat.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatException;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.Jid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;
import org.jxmpp.util.XmppStringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.fragment.interfaces.OnBackPressedListener;
import hu.unideb.smartcampus.xmpp.Connection;

import static android.content.ContentValues.TAG;

/**
 * Created by Headswitcher on 2017. 03. 31..
 * <p>
 * We check the editfield, if every jid there is valid,
 * if thats the case we create a chat (Muc if theres more then one jid with the separator character <code>,</code>)
 */

public class ChatNewConversation extends Fragment implements OnBackPressedListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_chat, container, false);
        final Button createNewChat = (Button) view.findViewById(R.id.chat_new_create_button);
        createNewChat.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {

                final EditText newMsg = (EditText) getView().findViewById(R.id.chat_new_msg_edittext);
                final EditText newChatPartners = (EditText) getView().findViewById(R.id.chat_new_partnerJids_edittext);
                final String newMsgText = newMsg.getText().toString();
                final String partnerBareJid = newChatPartners.getText().toString();
                final XMPPBOSHConnection xmppConnection = Connection.getInstance().getXmppConnection();
                final UserSearchManager userSearchManager = new UserSearchManager(xmppConnection);
                if (!partnerBareJid.isEmpty() && !newMsgText.isEmpty()) {
                    boolean isValidUsername = true;
                    if (partnerBareJid.contains(",")) {
                        String[] partnerBareJids = StringUtils.split(partnerBareJid, ",");
                        List<Jid> ownerList = new ArrayList<Jid>();
                        for (int i = 0; i < partnerBareJids.length; i++) {
                            if ((partnerBareJids[i].length() > 0)) {
                                isValidUsername = checkUserIsExists(xmppConnection, userSearchManager, partnerBareJids[i]);
                                if (isValidUsername) {
                                    try {
                                        ownerList.add(JidCreate.entityBareFrom(XmppStringUtils.completeJidFrom(partnerBareJid, "wt2.inf.undideb.hu", "Smartcampus")));
                                    } catch (XmppStringprepException e) {
                                        e.printStackTrace();
                                    }
                                }


                            } else {
                                break;
                                //wrongusername
                            }
                        }
                        if (isValidUsername) {
                            MultiUserChat createChat = null;
                            try {

                                final XMPPBOSHConnection xmppboshConnection = Connection.getInstance().
                                        getXmppConnection();
                                createChat = MultiUserChatManager.
                                        getInstanceFor(xmppboshConnection).
                                        getMultiUserChat(JidCreate.entityBareFrom("Szobabev" + "@conference.wt2.inf.unideb.hu"));

                                createChat.create(Resourcepart.from("Szobanev")).
                                        getConfigFormManager().
                                        setRoomOwners(ownerList);
                                final Form configurationForm = createChat.getConfigurationForm();
                                final Form answerForm = configurationForm.createAnswerForm();
                                final List<FormField> fields = answerForm.getFields();
                                answerForm.setAnswer("muc#roomconfig_persistentroom", true);
                                answerForm.setAnswer("mam", true);
                                answerForm.setAnswer("muc#roomconfig_publicroom",true);
                                createChat.sendConfigurationForm(answerForm);
                            } catch (XmppStringprepException | MultiUserChatException.MucAlreadyJoinedException | MultiUserChatException.MissingMucCreationAcknowledgeException | SmackException.NotConnectedException | MultiUserChatException.NotAMucServiceException | XMPPException.XMPPErrorException | SmackException.NoResponseException | InterruptedException | MultiUserChatException.MucConfigurationNotSupportedException e) {
                                e.printStackTrace();
                            }
                        }

                        //if (isValidUsername){createMultiChat();
                        //
                        //}
                    } else {
                        isValidUsername = checkUserIsExists(xmppConnection, userSearchManager, partnerBareJid);
                        if (isValidUsername) {
                            createSingleChat(xmppConnection, partnerBareJid, newMsgText);
                        } else {
                            //wrongusername
                        }
                    }

                } else

                {
                    //TOAST
                }
            }
        });
        return view;

    }

    private void createSingleChat(XMPPBOSHConnection xmppConnection, String partnerBareJid, String newMsg) {
        ChatManager chatManager = ChatManager.getInstanceFor(xmppConnection);
        try {
            final EntityBareJid partnerJid = JidCreate.entityBareFrom(XmppStringUtils.completeJidFrom(partnerBareJid, xmppConnection.getServiceName()));
            Log.e(TAG, "createSingleChat: " + partnerJid.toString());
            Log.e(TAG, "createSingleChat: " + newMsg);
            chatManager.chatWith(partnerJid).send(newMsg);
        } catch (XmppStringprepException | InterruptedException | SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

    }

    private boolean checkUserIsExists(XMPPBOSHConnection xmppConnection, UserSearchManager userSearchManager, String bareJid) {
        boolean returnValue = true;
        try {
            DomainBareJid searchService = JidCreate.domainBareFrom("vjud." + xmppConnection.getServiceName());
            Form searchForm = userSearchManager.getSearchForm(searchService);
            Form answerForm = searchForm.createAnswerForm();
            answerForm.setAnswer("user", bareJid);
            ReportedData data = userSearchManager.getSearchResults(answerForm, searchService);
            if (data.getRows() != null) {
                List<ReportedData.Row> rows = data.getRows();
                Iterator<ReportedData.Row> it = rows.iterator();
                if (!it.hasNext()) {
                    returnValue = false;
                }
            } else {
                returnValue = false;
            }

        } catch (SmackException.NoResponseException | XMPPException.XMPPErrorException | SmackException.NotConnectedException | InterruptedException | XmppStringprepException e) {
            returnValue = false;
            e.printStackTrace();

        }
        return returnValue;
    }

    @Override
    public void onBackPressed() {

    }
}
