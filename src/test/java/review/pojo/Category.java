package review.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter //this annotation will add getters to all fields
@Setter //this annotation will add setters to all fields
@AllArgsConstructor //this annotation will add constructors with all arguments
@NoArgsConstructor //this annotation will add constructors with no arguments
@ToString //this annotation will add toString method  with all arguments
@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    private int id;
    private String name;

}
