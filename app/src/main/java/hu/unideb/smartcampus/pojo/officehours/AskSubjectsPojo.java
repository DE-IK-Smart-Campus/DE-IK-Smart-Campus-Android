package hu.unideb.smartcampus.pojo.officehours;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class AskSubjectsPojo implements Serializable {

    private List<Subject> subjects;

}


