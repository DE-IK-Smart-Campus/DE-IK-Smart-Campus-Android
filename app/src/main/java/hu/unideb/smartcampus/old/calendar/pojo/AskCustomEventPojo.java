package hu.unideb.smartcampus.old.calendar.pojo;


import java.util.List;

import hu.unideb.smartcampus.old.officehours.pojo.BasePojo;
import hu.unideb.smartcampus.old.sqlite.model.CustomEvent;

public class AskCustomEventPojo extends BasePojo {

    private List<CustomEvent> customEvents;

    public AskCustomEventPojo() {
    }

    public AskCustomEventPojo(List<CustomEvent> customEvents) {
        this.customEvents = customEvents;
    }

    public List<CustomEvent> getCustomEvents() {
        return customEvents;
    }

    public void setCustomEvents(List<CustomEvent> customEvents) {
        this.customEvents = customEvents;
    }
}
