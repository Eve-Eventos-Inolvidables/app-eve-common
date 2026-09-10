package Models.Event;

import Models.Base.Archivable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BelongToEvent extends Archivable {
    @ManyToOne
    @JoinColumn(name = "event_id")
    private SimpleEvent event;
}
