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
public abstract class BaseEntity {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "is_archived", nullable = false)
    private boolean archived;
}