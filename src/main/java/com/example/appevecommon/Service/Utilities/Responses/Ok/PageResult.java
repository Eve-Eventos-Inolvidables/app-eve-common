package com.example.appevecommon.Service.Utilities.Responses.Ok;

import com.example.appevecommon.Service.Utilities.Responses.Pagination;
import org.springframework.data.domain.Page;

import java.util.List;

public record PageResult<T>(List<T> items, Pagination pagination) {
    public static <T> PageResult<T> from(Page<T> page) {
        return new PageResult<>(
                page.getContent(),
                new Pagination(
                        page.getNumber(),
                        page.getSize(),
                        page.getTotalElements(),
                        page.getTotalPages()
                )
        );
    }
}