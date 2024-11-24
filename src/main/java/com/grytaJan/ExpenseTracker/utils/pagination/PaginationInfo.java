package com.grytaJan.ExpenseTracker.utils.pagination;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaginationInfo
{
    @Min(0)
    private int page = 0;
    @Min(1)
    @Max(100)
    private int size = 10;

    @NotBlank
    private String sortBy = "id";
    private String direction = "asc";
    @JsonIgnore
    public boolean isDescending() {
        return direction.equalsIgnoreCase("desc");
    }
}
