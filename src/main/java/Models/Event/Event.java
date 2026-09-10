package Models.Event;

import Models.Base.Archivable;
import Models.Event.Addons.EventCategory;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Event extends Archivable {

    @ManyToOne
    @JoinColumn(name = "category_id")
    private EventCategory eventCategory;

    @Column( name = "name", nullable = false,length = 125)
    private String name;

    @Column( name = "visible_start",nullable = false)
    private LocalDate visibleStart;

    @Column(name ="description",length = 1000)
    private String description;

}
