<<<<<<<< HEAD:src/main/java/com/example/appevecommon/Models/Event/EventCategory.java
package com.example.appevecommon.Models.Event;

import com.example.appevecommon.Models.Base.Identificable;
========
package Models.Event.Addons;

import Models.Base.Archivable;
>>>>>>>> be4de9750f62ce0ff128467d2e514bcbfcb672c1:src/main/java/com/example/appevecommon/Models/Event/Addons/EventCategory.java
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
