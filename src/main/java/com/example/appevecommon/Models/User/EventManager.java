package com.example.appevecommon.Models.User;

import com.example.appevecommon.Models.Base.BaseEntity;
import com.example.appevecommon.Models.Event.SimpleEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Event_Managers")
public class EventManager extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private SimpleEvent event;

    @Column( name = "assigned_at")
    private LocalDate assignedAt;
}
