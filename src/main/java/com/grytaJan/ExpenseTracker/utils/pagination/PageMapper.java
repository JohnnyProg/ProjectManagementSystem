package com.grytaJan.ExpenseTracker.utils.pagination;

import org.springframework.data.domain.Page;

import java.util.function.Function;

public class PageMapper {
    public static <T, U> Page<U> pageToDtos(Page<T> page, Function<T, U> converter) {
        return page.map(converter);
    }

    public static <T, U> PageResponse<U> pageToPageResponse(Page<T> page, Function<T, U> converter) {
        Page<U> dtoPage = pageToDtos(page, converter);
        return new PageResponse<>(dtoPage);
    }
}
