package com.example.appevecommon.Models.Event;

import com.example.appevecommon.Models.Event.Sector.Sector;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Events")

public class SimpleEvent extends Event{
    @Column( name = "date", nullable = false)
    private LocalDate date;
    @Column( name="kick_off_time",nullable = false)
    private LocalTime kickOffTime;

    @NotNull
    @Column( name = "tickets_per_user_limit")
    @Min(value = 1, message = "La cantidad mínima por persona es 1 ticket")
//    @Max(value = 10, message = "La cantidad máxima por persona son 10 tickets" )
    private Integer ticketsPerPersonLimit;

    @Column(name="primary_color", nullable = false)
    private String primaryColor;

    @Column(name="secondary_color", nullable = false)
    private String secondaryColor;

    @Column(name ="maps_link")
    private String mapsLink;

    @ManyToOne
    @JoinColumn( name = "group_event_id")
    private EventGroup eventGroup;

    @OneToMany(
            mappedBy = "event",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Sector> sectorList;

}
