package com.example.appevecommon.Models.Event.Batch;


import com.example.appevecommon.Models.Event.ArchivableBelongsToEvent;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity(name = "Batches")
@Getter
@Setter
@NoArgsConstructor
@Table( name = "Batches")
public class Batch extends ArchivableBelongsToEvent {
    @Id
    private Long id;
    @Column( name = "name",nullable = false,length = 100)
    private String name;

    @Column( name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column( name = "end_date", nullable = false)
    private LocalDate endDate;

    @OneToMany(
            mappedBy = "batch",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<SectorByBatch> sectorByBatchesList;
}
