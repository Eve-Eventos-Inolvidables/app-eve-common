package Models.Event.Batch;

import Models.Base.Archivable;
import Models.Event.BelongToEvent;
import Models.Event.SimpleEvent;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = "Batches")
@Inheritance(strategy = InheritanceType.JOINED)
public class Batch extends BelongToEvent {
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
