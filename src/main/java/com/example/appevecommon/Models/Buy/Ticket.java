package com.example.appevecommon.Models.Buy;

import com.example.appevecommon.Models.Base.BaseEntity;
import com.example.appevecommon.Models.Event.Batch.SectorByBatch;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table( name = "Tickets")
public class Ticket extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "sectors_by_batch")
    private SectorByBatch sectorByBatch;

    @Column( name = "qr_token")
    private String qrToken;

    @ManyToOne
    @JoinColumn(name="buy_id")
    private Buy buy;
}
