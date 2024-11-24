package com.grytaJan.ExpenseTracker.utils.pagination;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageResponse<T> {
    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private long totalElements;

    private int totalPages;
    private boolean last;
    private boolean first;
    private boolean empty;
    private int numberOfElements;
    private SortResponse sort;

    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.pageNumber = page.getNumber();
        this.pageSize = page.getSize();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.last = page.isLast();
        this.first = page.isFirst();
        this.empty = page.isEmpty();
        this.numberOfElements = page.getNumberOfElements();
        this.sort = new SortResponse(page.getSort());
    }

    @Getter
    @Setter
    public static class SortResponse {
        private List<SortDetails> sortDetailsList;

        public SortResponse(Sort sort) {
            sortDetailsList = new ArrayList<>();
            for (Sort.Order order: sort) {
                sortDetailsList.add(new SortDetails(order.getProperty(), order.getDirection().name()));
            }
        }

        @Getter
        @Setter
        @AllArgsConstructor
        public static class SortDetails {
            private String orderBy;
            private String direction;
        }

    }

}
