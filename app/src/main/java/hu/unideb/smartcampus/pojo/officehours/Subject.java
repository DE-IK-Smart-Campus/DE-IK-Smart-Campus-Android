package hu.unideb.smartcampus.pojo.officehours;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subject implements Serializable {
    private Integer id;
    private String name;
    private List<Instructor> instructors;

}

