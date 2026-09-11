package com.example.appevecommon.Models.Event;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Event_Groups")
public class EventGroup extends Event {
    @Column( name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column( name = "end_date", nullable = false)
    private LocalDate endtDate;

    @Column( name = "front_page_link", nullable = false)
    private String frontPageLink;

    @Column( name = "banner_link", nullable = false)
    private String bannerLink;
}
