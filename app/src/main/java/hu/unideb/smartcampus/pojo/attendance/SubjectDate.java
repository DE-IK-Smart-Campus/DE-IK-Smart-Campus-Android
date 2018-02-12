package hu.unideb.smartcampus.pojo.attendance;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
class SubjectDate implements Serializable {

    private Long dateId;
    private Long date;
    private Boolean yesOrNo;
}
