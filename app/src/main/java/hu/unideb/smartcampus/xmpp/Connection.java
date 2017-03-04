package hu.unideb.smartcampus.xmpp;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.chat.*;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;

import hu.unideb.smartcampus.xmpp.listeners.AdminListen;

/**
 * Created by Erdei Krisztián on 2017.03.03..
 */

public class Connection {

    private static Connection instance = null;

    private XMPPTCPConnection xmpptcpConnection;
    private Chat adminChat;


    protected Connection() {
    }

    public static Connection getInstance() {

        if (instance == null) {
            instance = new Connection();

            //TODO exception handling
        }
        return instance;
    }

    public XMPPTCPConnection getXmpptcpConnection() {
        return xmpptcpConnection;
    }

    public void setXMPPTCPConnection(XMPPTCPConnectionConfiguration config) {

        setLocalXmpptcpConnection(new XMPPTCPConnection(config));

        // Will refactor TODO;
        try {
            xmpptcpConnection.connect();
            xmpptcpConnection.login();
            ChatManager chatManager = ChatManager.getInstanceFor(xmpptcpConnection);
            Message uzi = new Message("smartcampus@192.168.1.11");
            uzi.setBody("Kommunikáció kialakítása ");
            adminChat = chatManager.createChat("smartcampus@192.168.1.11"); //TODO

            try {
              adminChat.sendMessage(uzi);
            } catch (SmackException.NotConnectedException e) {
                e.printStackTrace();
            }
            adminChat.addMessageListener(new AdminListen());
        } catch (XMPPException e) {
            e.printStackTrace();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setLocalXmpptcpConnection(XMPPTCPConnection xmpptcpConnection) {
        this.xmpptcpConnection = xmpptcpConnection;
    }

    public Chat getAdminChat() {
       if (adminChat != null)

        return adminChat;

        else {
           //TODO
           throw new RuntimeException();
       }
    }
}
