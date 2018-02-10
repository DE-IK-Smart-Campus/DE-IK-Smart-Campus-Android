package hu.unideb.smartcampus.old.officehours.pojo;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Pojo class
 * <p>
 * The user will have some subjects imported from the calendar.
 * a subject will have a {@code String name} and a list of {@link Instructor}
 *
 * @see Parcelable
 * <p>
 * Created by Headswitcher on 2017. 03. 08..
 */


public class Subject implements Serializable {
    private Integer id;
    private String name;
    private List<Instructor> instructors;

    public Subject() {
    }

    public Subject(String name, List<Instructor> instructors) {
        this.name = name;
        this.instructors = instructors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}

