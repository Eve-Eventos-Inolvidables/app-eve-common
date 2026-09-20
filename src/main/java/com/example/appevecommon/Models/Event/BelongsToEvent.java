package com.example.appevecommon.Models.Event;

import com.example.appevecommon.Models.Base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class BelongsToEvent extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "event_id")
    private SimpleEvent event;
}
