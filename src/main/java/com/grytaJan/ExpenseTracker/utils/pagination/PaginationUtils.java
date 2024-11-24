package com.grytaJan.ExpenseTracker.utils.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtils {
    public static Pageable createPageable(int page, int size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("desc")
                //id descending and ascendng matching first order becaouse if user wants to sort by id it would override first order, this way no checks required
                ? Sort.by(Sort.Order.desc(sortBy), Sort.Order.desc("id"))
                : Sort.by(Sort.Order.asc(sortBy), Sort.Order.asc("id"));
        return PageRequest.of(page, size, sort);
    }

    public static Pageable createPageable(PaginationInfo paginationInfo) {
        Sort sort = paginationInfo.isDescending()
                //id descending and ascendng matching first order becaouse if user wants to sort by id it would override first order, this way no checks required
                ? Sort.by(Sort.Order.desc(paginationInfo.getSortBy()), Sort.Order.desc("id"))
                : Sort.by(Sort.Order.asc(paginationInfo.getSortBy()), Sort.Order.asc("id"));
        return PageRequest.of(paginationInfo.getPage(), paginationInfo.getSize(), sort);
    }
}
