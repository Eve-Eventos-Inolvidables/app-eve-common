package com.example.appevecommon.Models.Event;

import com.example.appevecommon.Models.Base.Archivable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class ArchivableBelongsToEvent extends Archivable {
    @ManyToOne
    @JoinColumn(name = "event_id")
    private SimpleEvent event;
}
