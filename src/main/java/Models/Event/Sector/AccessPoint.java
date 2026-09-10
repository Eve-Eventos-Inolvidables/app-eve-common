package Models.Event.Sector;

import Models.Base.Archivable;
import Models.Base.Identificable;
import Models.Event.BelongToEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "Access_Points")

public class AccessPoint extends BelongToEvent {
    @ManyToOne
    @JoinColumn( name = "sector_id")
    private Sector sector;

    @Column( name = "name", nullable = false,length = 125)
    private String name;

    @Column( name = "direction", nullable = false,length = 125)
    private String direction;
}
