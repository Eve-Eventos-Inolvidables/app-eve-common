package com.example.appevecommon.Models.Event.Batch;

import com.example.appevecommon.Models.Base.Archivable;
import com.example.appevecommon.Models.Event.Sector.Sector;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table( name = "Sectors_By_Batch")

public class SectorByBatch extends Archivable {
    @ManyToOne
    @JoinColumn( name = "sector_id")
    private Sector sector;

    @ManyToOne
    @JoinColumn( name = "batch_id")
    private Batch batch;

    @Column( name = "price",nullable = false)
    @Min(value = 1,message = "El precio tiene que ser mayor")
    private int price;

    @Column( name = "quantity",nullable = false)
    @Min(value = 1,message = "La cantidad tiene que ser mayor")
    private int quantity;


}
