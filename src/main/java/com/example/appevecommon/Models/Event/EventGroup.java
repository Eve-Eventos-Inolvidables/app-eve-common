package com.example.appevecommon.Models.Event;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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

    @OneToMany(
            mappedBy = "eventGroup",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SimpleEvent> simpleEvents;
}
