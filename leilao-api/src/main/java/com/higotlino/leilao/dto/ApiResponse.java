package com.higotlino.leilao.dto;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

@Data
public class ApiResponse<T> {
    private String message;
    private T data;
    private Object metadata;

    public ApiResponse(String message, T data, Object metadata) {
        this.message = message;
        this.data = data;
        this.metadata = metadata;
    }

    public static <T> ApiResponse<T> ok(String message, T data) {
        return new ApiResponse<>(message, data, null);
    }

    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(message, null, null);
    }

    public static <T> ApiResponse<List<T>> ok(String message, Page<T> page) {
        Map<String, Object> metadata = Map.of(
                "page", page.getNumber(),
                "size", page.getSize(),
                "totalElements", page.getTotalElements(),
                "totalPages", page.getTotalPages()
        );
        return new ApiResponse<>(message, page.getContent(), metadata);
    }
}
