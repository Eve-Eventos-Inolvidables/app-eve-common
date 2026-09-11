package com.example.appevecommon.Models.Event;

import Models.Base.Archivable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BelongToEvent extends Archivable {
    @ManyToOne
    @JoinColumn(name = "event_id")
    private SimpleEvent event;
}
