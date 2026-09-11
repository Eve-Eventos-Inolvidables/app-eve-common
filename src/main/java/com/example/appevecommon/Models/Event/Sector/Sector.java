package com.example.appevecommon.Models.Event.Sector;

import com.example.appevecommon.Models.Base.Archivable;
import com.example.appevecommon.Models.Base.Identificable;
import com.example.appevecommon.Models.Event.Batch.SectorByBatch;
import com.example.appevecommon.Models.Event.BelongToEvent;
import com.example.appevecommon.Models.Event.SimpleEvent;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = "Sectors")

public class Sector extends BelongToEvent {


    @Column( name = "name",nullable = false,length = 100)
    private String name;

    @Column( name = "capacity",nullable = false)
    @Min(value = 1, message = "La capacidad tiene que ser mayor")
    private int capacity;


    @OneToMany(
            mappedBy = "sector",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SectorByBatch> sectorByBatchesList;
}
