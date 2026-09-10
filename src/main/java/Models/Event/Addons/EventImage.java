package Models.Event.Addons;

import Models.Base.Identificable;
import Models.Event.SimpleEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "Event_Images")
public class EventImage extends Identificable {

    @ManyToOne
    @JoinColumn( name = "event_id")
    private SimpleEvent event;

    @ManyToOne
    @JoinColumn( name = "image_type_id")
    private  ImageType imageType;

    @Column( name = "name", nullable = false, length = 100)
    private String name;

    @Column( name = "image_link", nullable = false)
    private String imageLink;
}
