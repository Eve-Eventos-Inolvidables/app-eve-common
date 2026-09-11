package com.example.appevecommon.Models.Event.Addons;

import com.example.appevecommon.Models.Base.Archivable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name =  "Event_Categories")
public class EventCategory extends Archivable {
    @Column(name = "name", nullable = false, length = 100)
    private String name;
}
