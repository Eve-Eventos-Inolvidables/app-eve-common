package com.example.appevecommon.Models.User;

import com.example.appevecommon.Models.Base.Identificable;
import com.example.appevecommon.Models.Event.Addons.EventCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "User_interests")
public class UserInterest extends Identificable {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private EventCategory category;
}
