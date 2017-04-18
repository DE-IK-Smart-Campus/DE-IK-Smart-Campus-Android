package hu.unideb.smartcampus.main.activity.officehours.pojo;

import android.os.Parcel;
import android.os.Parcelable;

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


public class Subject implements Parcelable {
    private Integer id;
    private String name;
    private List<Instructor> instructors;

    public Subject() {
    }

    public Subject(String name, List<Instructor> instructors) {
        this.name = name;
        this.instructors = instructors;
    }

    protected Subject(Parcel in) {
        name = in.readString();
        instructors = in.createTypedArrayList(Instructor.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(instructors);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

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

