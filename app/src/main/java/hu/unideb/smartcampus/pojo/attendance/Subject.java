package hu.unideb.smartcampus.pojo.attendance;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject implements Serializable {

    private String name;
    private List<SubjectDate> subjectDates;
}
