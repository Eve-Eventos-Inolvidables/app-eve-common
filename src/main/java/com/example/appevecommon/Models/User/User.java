package com.example.appevecommon.Models.User;

import com.example.appevecommon.Models.Base.Archivable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Users")
public class User extends Archivable {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @NotBlank
    @Column(name = "name", nullable = false, length = 125)
    private String name;

    @NotBlank
    @Email
    @Column(name = "email", nullable = false, unique = true, length = 150)
    private String email;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;
}