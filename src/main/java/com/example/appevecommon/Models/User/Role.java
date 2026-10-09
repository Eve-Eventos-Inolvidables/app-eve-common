package com.example.appevecommon.Models.User;

import com.example.appevecommon.Models.Base.BaseEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Roles")
@Table(name = "Roles")
public class Role extends BaseEntity {

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;
}