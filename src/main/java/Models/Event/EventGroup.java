package Models.Event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Event_Groups")
public class EventGroup extends Event {
    @Column( name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column( name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column( name = "front_page_link", nullable = false)
    private String frontPageLink;

    @Column( name = "banner_link", nullable = false)
    private String bannerLink;

    @OneToMany(
            mappedBy = "eventGroup",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SimpleEvent> simpleEvents = new ArrayList<>();
}
