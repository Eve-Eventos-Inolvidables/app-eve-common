package com.example.appevecommon.Models.User;

import com.example.appevecommon.Models.Base.Archivable;

import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Roles")
public class Role extends Archivable {

    @NotBlank
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;
}