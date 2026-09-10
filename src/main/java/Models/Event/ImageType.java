package Models.Event;

import Models.Base.Identificable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name =  "Image Types")
public class ImageType extends Identificable {
    @Column(name = "name", nullable = false, length = 100)
    private String name;
}
