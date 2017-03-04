package hu.unideb.smartcampus.xmpp.json;

import java.util.List;

import hu.unideb.smartcampus.adapter.consultingHours.dataObjects.Class;

/**
 * Created by Erdei Krisztián on 2017.03.04..
 */

public class ClassesJson {
//JSONTEST
    String name;

    public ClassesJson() {
    }

    public ClassesJson(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
