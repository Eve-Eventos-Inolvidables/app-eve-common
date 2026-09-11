package com.example.appevecommon.Models.Base;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
@Inheritance( strategy = InheritanceType.JOINED)
public abstract class Identificable {
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

}
