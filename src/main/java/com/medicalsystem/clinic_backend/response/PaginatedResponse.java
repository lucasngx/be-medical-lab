package com.medicalsystem.clinic_backend.response;

import lombok.Data;
import java.util.List;

@Data
public class PaginatedResponse<T> {
    private List<T> data;
    private long total;
    private int page;
    private int limit;
    private int totalPages;

    public PaginatedResponse(List<T> data, long total, int page, int limit) {
        this.data = data;
        this.total = total;
        this.page = page;
        this.limit = limit;
        this.totalPages = (int) Math.ceil((double) total / limit);
    }

    public PaginatedResponse() {
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setTotal(long total) {
        this.total = total;
        this.totalPages = (int) Math.ceil((double) total / limit);
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setLimit(int limit) {
        this.limit = limit;
        this.totalPages = (int) Math.ceil((double) total / limit);
    }
}
