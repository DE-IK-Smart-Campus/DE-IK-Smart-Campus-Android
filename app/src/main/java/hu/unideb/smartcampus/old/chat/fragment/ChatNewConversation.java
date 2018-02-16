package hu.unideb.smartcampus.old.chat.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.lang3.StringUtils;
import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.StanzaCollector;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.bosh.XMPPBOSHConnection;
import org.jivesoftware.smack.chat2.Chat;
import org.jivesoftware.smack.chat2.ChatManager;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.MultiUserChatException;
import org.jivesoftware.smackx.muc.MultiUserChatManager;
import org.jivesoftware.smackx.search.ReportedData;
import org.jivesoftware.smackx.search.UserSearchManager;
import org.jivesoftware.smackx.xdata.Form;
import org.jxmpp.jid.DomainBareJid;
import org.jxmpp.jid.EntityBareJid;
import org.jxmpp.jid.impl.JidCreate;
import org.jxmpp.jid.parts.Resourcepart;
import org.jxmpp.stringprep.XmppStringprepException;
import org.jxmpp.util.XmppStringUtils;

import java.util.Iterator;
import java.util.List;

import hu.unideb.smartcampus.R;
import hu.unideb.smartcampus.shared.iq.request.AddMucChatIqRequest;
import hu.unideb.smartcampus.shared.iq.request.AddUserChatIqRequest;
import hu.unideb.smartcampus.task.chat.GetChatsTask;
import hu.unideb.smartcampus.xmpp.Connection;

import static hu.unideb.smartcampus.xmpp.Connection.ADMINJID;
import static hu.unideb.smartcampus.xmpp.Connection.HOSTNAME;

/**
 * Created by Headswitcher on 2017. 03. 31..
 * <p>
 * We check the editfield, if every jid there is valid,
 * if thats the case we create a chat (Muc if theres more then one jid with the separator character <code>,</code>)
 * <p>
 * TODO needs refactor, transaction handle , exception handle
 */

public class ChatNewConversation extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_chat, container, false);

        final EditText editText = (EditText) view.findViewById(R.id.chat_new_partnerJids_edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                EditText groupNameEditText = (EditText) getView().findViewById(R.id.chat_new_group_name_editText);
                final int length = StringUtils.split(s.toString(), ",").length;
                if (s.toString().contains(",") && length > 1) {
                    groupNameEditText.setVisibility(View.VISIBLE);
                } else {
                    groupNameEditText.setVisibility(View.GONE);
                }
            }

        });

        final Button createNewChat = (Button) view.findViewById(R.id.chat_new_create_button);
        createNewChat.setOnClickListener(new View.OnClickListener()

        {
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
                        EditText groupNameEditText = (EditText) getView().findViewById(R.id.chat_new_group_name_editText);
                        String[] partnerBareJids = StringUtils.split(partnerBareJid, ",");
                        for (int i = 0; i < partnerBareJids.length; i++) {
                            if ((partnerBareJids[i].length() > 0)) {
                                isValidUsername = checkUserIsExists(xmppConnection, userSearchManager, partnerBareJids[i]);
                                if (!isValidUsername) {
                                    final String toastText = getString(R.string.can_not_find) + " " + partnerBareJids[i] + " " + getString(R.string.user_with_this_name);
                                    Toast.makeText(v.getContext(), toastText, Toast.LENGTH_SHORT).show();
                                    isValidUsername = false;
                                    break;
                                }
                            }
                        }
                        if (isValidUsername) {
                            if (groupNameEditText.getText().length() != 0) {
                                MultiUserChat createChat = null;
                                try {
                                    final XMPPBOSHConnection xmppboshConnection = Connection.getInstance().
                                            getXmppConnection();
                                    final String groupnameInString = groupNameEditText.getText().toString();
                                    StringUtils.remove(groupnameInString, " ");
                                    final String groupnameInJid = groupnameInString + "@conference." + HOSTNAME;
                                    createChat = MultiUserChatManager.
                                            getInstanceFor(xmppboshConnection).
                                            getMultiUserChat(JidCreate.entityBareFrom(groupnameInJid));
                                    final Form configurationForm = createChat.getConfigurationForm();
                                    final Form answerForm = configurationForm.createAnswerForm();
                                    answerForm.setAnswer("muc#roomconfig_persistentroom", true);
                                    answerForm.setAnswer("mam", true);
                                    answerForm.setAnswer("muc#roomconfig_publicroom", true);
                                    createChat.sendConfigurationForm(answerForm);

                                    createChat.join(Resourcepart.from(xmppboshConnection.getUser().getLocalpartOrNull().toString()));
                                    createChat.sendMessage(newMsgText);
                                    createChat.leave();

                                    AddMucChatIqRequest addMucChatIqRequest = new AddMucChatIqRequest();
                                    addMucChatIqRequest.setStudent(xmppConnection.getUser().getLocalpartOrThrow().toString());
                                    addMucChatIqRequest.setMuc(groupnameInJid);
                                    addMucChatIqRequest.setType(IQ.Type.set);
                                    addMucChatIqRequest.setFrom(xmppConnection.getUser());
                                    addMucChatIqRequest.setTo(JidCreate.from(ADMINJID));
                                    final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(addMucChatIqRequest);
                                    stanzaCollectorAndSend.nextResultOrThrow(5000);
                                    new GetChatsTask(getActivity()).execute();

                                } catch (XmppStringprepException | SmackException.NotConnectedException | XMPPException.XMPPErrorException | SmackException.NoResponseException | InterruptedException | MultiUserChatException.NotAMucServiceException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Toast.makeText(v.getContext(), R.string.please_give_me_group_name, Toast.LENGTH_SHORT).show();
                            }
                        }

                    } else {
                        isValidUsername = checkUserIsExists(xmppConnection, userSearchManager, partnerBareJid);
                        if (isValidUsername) {
                            createSingleChat(xmppConnection, partnerBareJid, newMsgText);
                        } else {
                            final String toastText = getString(R.string.can_not_find) + partnerBareJid + getString(R.string.user_with_this_name);
                            Toast.makeText(v.getContext(), toastText, Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(v.getContext(), R.string.please_fill_out_every_field, Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;

    }

    private void createSingleChat(XMPPBOSHConnection xmppConnection, String partnerBareJid, String newMsg) {
        ChatManager chatManager = ChatManager.getInstanceFor(xmppConnection);
        try {
            final EntityBareJid partnerJid = JidCreate.entityBareFrom(XmppStringUtils.completeJidFrom(partnerBareJid, xmppConnection.getServiceName()));
            final Chat chat = chatManager.chatWith(partnerJid);
            AddUserChatIqRequest addUserChatIqRequest = new AddUserChatIqRequest();
            addUserChatIqRequest.setStudent(xmppConnection.getUser().getLocalpartOrThrow().toString());
            addUserChatIqRequest.setChat(partnerJid.toString());
            addUserChatIqRequest.setType(IQ.Type.set);
            addUserChatIqRequest.setFrom(xmppConnection.getUser());
            addUserChatIqRequest.setTo(JidCreate.from(ADMINJID));
            final StanzaCollector stanzaCollectorAndSend = Connection.getInstance().getXmppConnection().createStanzaCollectorAndSend(addUserChatIqRequest);
            stanzaCollectorAndSend.nextResultOrThrow(5000);
            chat.send(newMsg);
            new GetChatsTask(getActivity()).execute();

        } catch (XmppStringprepException | InterruptedException | SmackException.NotConnectedException | SmackException.NoResponseException | XMPPException.XMPPErrorException e) {
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

}
