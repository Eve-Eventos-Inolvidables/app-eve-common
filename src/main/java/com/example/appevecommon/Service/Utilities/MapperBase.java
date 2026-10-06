package com.example.appevecommon.Service.Utilities;

import com.example.appevecommon.Models.Base.BaseEntity;
import org.mapstruct.Named;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public interface MapperBase {


    @Named("idOf")
    default Long idOf(BaseEntity related) {
        return (related != null) ? related.getId() : null;
    }

    default String formatTime(LocalTime time) {
        return (time == null) ? null : time.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    default LocalTime parseTime(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        String normalized = value.trim();
        try {
            return LocalTime.parse(normalized, DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (DateTimeParseException e) {
            return LocalTime.parse(normalized, DateTimeFormatter.ofPattern("HH:mm"));
        }
    }
}
