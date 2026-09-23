package com.example.appevecommon.Models.User;

import com.example.appevecommon.Models.Base.BaseEntity;
import com.example.appevecommon.Models.Event.Addons.EventCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "User_Interests")
@Table(name = "User_Interests")
public class UserInterest extends BaseEntity {

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private EventCategory category;

    @NotNull
    @Column( name = "marked_at")
    private LocalDate markedAt;

}
