package com.example.appevecommon.Service.Utilities;

import lombok.Getter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
public abstract class PagedFilter {
    private static final int DEFAULT_SIZE = 20;
    private static final int MAX_SIZE = 1000;

    private int page = 0;
    private int size = DEFAULT_SIZE;
    private String sort;

    public void setPage(int page) {
        this.page = Math.max(page, 0);
    }

    public void setSize(int size) {
        this.size = Math.clamp(size, 1, MAX_SIZE);
    }

    public Pageable toPageable() {
        Sort sortable = Sort.unsorted();
        if (sort != null && !sort.isBlank()) {
            String[] parts = sort.trim().split("[,\\s]+");
            String field = parts[0];
            Sort.Direction direction = parts.length > 1 && "desc".equalsIgnoreCase(parts[1])
                    ? Sort.Direction.DESC : Sort.Direction.ASC;
            sortable = Sort.by(new Sort.Order(direction, field));
        }
        return PageRequest.of(page, size, sortable);
    }
}