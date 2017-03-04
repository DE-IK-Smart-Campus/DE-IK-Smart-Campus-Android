package hu.unideb.smartcampus.xmpp.listeners;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.packet.Message;

import java.util.Date;

/**
 * Created by Erdei Krisztián on 2017.03.04..
 */

public class AdminListen implements ChatMessageListener {
    @Override
    public void processMessage(Chat chat, Message message) {

        //JSONTest TODO
     /*   ClassesJson classesJson;
        ObjectMapper objectMapper = new ObjectMapper();
        String body = message.getBody();
        try {
            List<ClassesJson> myObjects = objectMapper.readValue(body, new TypeReference<List<ClassesJson>>() {
            });

            for (int i = 0; i < myObjects.size(); i++) {
                Log.d("TTárgyak:", myObjects.get(i).toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    */

        Message uzi = new Message("smartcampus@192.168.1.11");
        uzi.setBody(String.valueOf(new Date().getSeconds()));
        try {
            chat.sendMessage(uzi);
        } catch (SmackException.NotConnectedException e) {
            e.printStackTrace();
        }

    }
}
