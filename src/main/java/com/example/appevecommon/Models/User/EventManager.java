package com.example.appevecommon.Models.User;

import com.example.appevecommon.Models.Base.Identificable;
import com.example.appevecommon.Models.Event.SimpleEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Event_managers")
public class EventManager extends Identificable {

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private User manager;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private SimpleEvent event;
}
