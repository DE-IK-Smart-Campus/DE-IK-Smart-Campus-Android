package hu.unideb.smartcampus.pojo.officehours;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FromToDatesInLong implements Serializable {

    private Long from;
    private Long to;
}
