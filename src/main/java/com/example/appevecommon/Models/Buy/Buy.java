package com.example.appevecommon.Models.Buy;

import com.example.appevecommon.Models.Event.BelongsToEvent;
import com.example.appevecommon.Models.User.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "Buys")
@Table( name = "Buys")
public class Buy extends BelongsToEvent {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "total", nullable = false)
    @Min(value = 1, message = "El total debe ser mayor")
    private int total;
    @Column( name = "date", nullable = false)
    private LocalTime date;

}
