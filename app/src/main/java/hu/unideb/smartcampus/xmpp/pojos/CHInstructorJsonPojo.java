package hu.unideb.smartcampus.xmpp.pojos;

import java.util.List;

/**
 * Created by Headswitcher on 2017. 03. 10..
 */

public class CHInstructorJsonPojo {
    private String messageType;
    private List<CHConsultingHoursPojo> consultingHours;

    public CHInstructorJsonPojo() {
    }

    public CHInstructorJsonPojo(String messageType, List<CHConsultingHoursPojo> consultingHours) {
        this.messageType = messageType;
        this.consultingHours = consultingHours;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public List<CHConsultingHoursPojo> getConsultingHours() {
        return consultingHours;
    }

    public void setConsultingHours(List<CHConsultingHoursPojo> consultingHours) {
        this.consultingHours = consultingHours;
    }
}
