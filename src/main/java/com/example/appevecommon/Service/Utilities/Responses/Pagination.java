package com.example.appevecommon.Service.Utilities.Responses;

public record Pagination(
        int page,
        int size,
        long totalItems,
        int totalPages
) {
}