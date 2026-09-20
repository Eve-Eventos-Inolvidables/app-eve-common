package com.example.appevecommon.Models.Base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
// This SQL Restriction injects AND is_archived = false in the queries
// If you want to ignore this flag, use nativeQuery = true (uses SQL directly so hibernate won't inject the flag in the query)
@SQLRestriction( "is_archived = false")
public abstract class Archivable extends BaseEntity {

    @Column( name = "is_archived")
    private boolean archived;
}
