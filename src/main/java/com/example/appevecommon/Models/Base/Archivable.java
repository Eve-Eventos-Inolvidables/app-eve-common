package com.example.appevecommon.Models.Base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public abstract class Archivable extends BaseEntity {

    @Column( name = "is_archived")
    private boolean archived;
}
