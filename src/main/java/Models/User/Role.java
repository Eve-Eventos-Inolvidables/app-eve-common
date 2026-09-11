package Models.User;

import Models.Base.Identificable;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Roles")
public class Role extends Identificable {

    @NotBlank
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;
}